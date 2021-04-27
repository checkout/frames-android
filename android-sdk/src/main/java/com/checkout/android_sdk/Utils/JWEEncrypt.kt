package com.checkout.android_sdk.Utils

import android.util.Base64
import org.json.JSONObject
import java.math.BigInteger
import java.security.AlgorithmParameters
import java.security.KeyFactory
import java.security.SecureRandom
import java.security.interfaces.RSAPublicKey
import java.security.spec.MGF1ParameterSpec
import java.security.spec.RSAPublicKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource
import javax.crypto.spec.SecretKeySpec


fun ByteArray.encodeUrlSafeBase64(): String =
        Base64.encodeToString(this, Base64.NO_PADDING or Base64.URL_SAFE or Base64.NO_WRAP)

fun String.decodeUrlSafeBase64(): ByteArray =
        Base64.decode(toByteArray(), Base64.NO_PADDING or Base64.URL_SAFE or Base64.NO_WRAP)

fun jsonObject(vararg pairs: Pair<String, Any>) = JSONObject().apply {
    pairs.forEach { put(it.first, it.second) }
}

object JWEEncrypt {

    private const val IV_BIT_LENGTH = 96
    private const val AUTH_TAG_BIT_LENGTH = 128
    private const val AES_GCM_KEY_BIT_LENGTH = 256

    @Throws(Throwable::class)
    @JvmStatic
    fun encrypt(
            rsaPublicKeyModulus: String,
            rsaPublicKeyExponent: String,
            rsaKeyId: String,
            payload: ByteArray
    ): String {

        val modulus = BigInteger(1, rsaPublicKeyModulus.decodeUrlSafeBase64())
        val exponent = BigInteger(1, rsaPublicKeyExponent.decodeUrlSafeBase64())

        val spec = RSAPublicKeySpec(modulus, exponent)

        val rsaKeyFactory = KeyFactory.getInstance("RSA")
        val rsaPublicKey = rsaKeyFactory.generatePublic(spec) as RSAPublicKey

        val headerBytes = jsonObject(
                "kid" to rsaKeyId,
                "typ" to "JOSE",
                "enc" to "A256GCM",
                "alg" to "RSA-OAEP-256"
        ).toString().toByteArray()
        val encoderHeader = headerBytes.encodeUrlSafeBase64()

        val cekKey = SecretKeySpec(
                generateSecureBytes(AES_GCM_KEY_BIT_LENGTH),
                "AES"
        )

        val gcmSpec = GCMParameterSpec(
                AUTH_TAG_BIT_LENGTH,
                generateSecureBytes(IV_BIT_LENGTH)
        )

        val cipherOutput = Cipher.getInstance("AES/GCM/NoPadding").run {
            init(Cipher.ENCRYPT_MODE, cekKey, gcmSpec)
            updateAAD(encoderHeader.toByteArray())
            doFinal(payload)
        }

        val tagPos: Int = cipherOutput.size - (AUTH_TAG_BIT_LENGTH / 8)
        val cipherTextBytes = subArray(cipherOutput, 0, tagPos)
        val authTagBytes = subArray(cipherOutput, tagPos, (AUTH_TAG_BIT_LENGTH / 8))

        // Encrypt the Content Encryption Key (CEK) with public RSA certificate
        val encryptedCekKey = encryptContentEncryptionKey(
                rsaPublicKey = rsaPublicKey,
                cek = cekKey.encoded
        )

        val encodedEncryptedKey = encryptedCekKey.encodeUrlSafeBase64()
        val encodedIv = gcmSpec.iv.encodeUrlSafeBase64()
        val encodedCipherText = cipherTextBytes.encodeUrlSafeBase64()
        val encodedAuth = authTagBytes.encodeUrlSafeBase64()

        return "$encoderHeader.$encodedEncryptedKey.$encodedIv.$encodedCipherText.$encodedAuth"
    }

    private fun encryptContentEncryptionKey(
            rsaPublicKey: RSAPublicKey,
            cek: ByteArray
    ): ByteArray {
        val algParam = AlgorithmParameters.getInstance("OAEP")
        algParam.init(
                OAEPParameterSpec(
                        "SHA-256",
                        "MGF1",
                        MGF1ParameterSpec.SHA256,
                        PSource.PSpecified.DEFAULT
                )
        )
        // Encrypt the Content Encryption Key (CEK) with public RSA certificate
        return Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding").run {
            init(Cipher.ENCRYPT_MODE, rsaPublicKey, algParam)
            doFinal(cek)
        }
    }

    private fun generateSecureBytes(sizeInBits: Int) =
            ByteArray(sizeInBits / 8).apply {
                SecureRandom().nextBytes(this)
            }

    private fun subArray(byteArray: ByteArray, beginIndex: Int, length: Int) =
            ByteArray(length).apply {
                System.arraycopy(byteArray, beginIndex, this, 0, size)
            }
}