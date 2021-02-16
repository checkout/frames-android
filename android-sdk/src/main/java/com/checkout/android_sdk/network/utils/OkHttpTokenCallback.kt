package com.checkout.android_sdk.network.utils

import com.checkout.android_sdk.Response.TokenisationFail
import com.checkout.android_sdk.Response.TokenisationResponse
import com.checkout.android_sdk.network.NetworkError
import com.checkout.android_sdk.network.TokenisationRequestListener
import com.checkout.android_sdk.network.TokenisationResult.*
import com.google.gson.JsonParseException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.nio.charset.Charset

internal abstract class OkHttpTokenCallback<T : TokenisationResponse>(
    val listener: TokenisationRequestListener<T>
) : Callback {

    override fun onResponse(call: Call, response: Response) {
        val tokenisationResult = try {
            val networkResponse = response.toNetworkResponse()
            val responseData = networkResponse.data

            if (responseData != null && responseData.isNotEmpty()) {
                if (response.isSuccessful) {
                    val result = toSuccessResult(responseData.toString(response.charset()))
                    Success(result, response.code)
                } else {
                    val result = toFailureResult(responseData.toString(response.charset()))
                    Fail(result, response.code)
                }
            } else {
                val errorMessage = when (response.code) {
                    in 200..299, 422 -> "Invalid server response" // Success response
                    401 -> "Unauthorised request"
                    else -> "Token request failed"
                }

                Error(
                    NetworkError(networkResponse, "$errorMessage (HttpStatus: ${response.code})").also {
                        it.networkTimeMs = networkResponse.networkTimeMs
                    }
                )
            }
        } catch (e: Exception) {
            val networkError = NetworkError(response.toNetworkResponse(), cause = e).also {
                it.networkTimeMs = response.networkTimeMs
            }
            Error(networkError)
        }
        listener.onTokenRequestComplete(tokenisationResult)
    }

    override fun onFailure(call: Call, e: IOException) {
        listener.onTokenRequestComplete(
            Error(NetworkError(cause = e))
        )
    }

    @Throws(JsonParseException::class)
    abstract fun toSuccessResult(jsonString: String): T

    @Throws(JsonParseException::class)
    abstract fun toFailureResult(jsonString: String): TokenisationFail

    private fun Response.charset(): Charset {
        return body?.contentType()?.charset(Charsets.UTF_8) ?: Charsets.UTF_8
    }

    private fun Response.toNetworkResponse(): NetworkResponse {
        return NetworkResponse(
            code,
            body?.bytes(),
            cacheResponse != null,
            networkTimeMs,
            headers.map { Header(it.first, it.second) }
        )
    }

    private val Response.networkTimeMs: Long
        get() {
            return if (receivedResponseAtMillis > 0 && sentRequestAtMillis > 0)
                receivedResponseAtMillis - sentRequestAtMillis
            else
                0
        }
}
