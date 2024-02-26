package com.checkout.tokenization.request

import com.checkout.tokenization.entity.AddressEntity
import com.checkout.tokenization.entity.PhoneEntity
import com.checkout.tokenization.response.TokenDetailsResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson

internal class TokenDetailsResponseJsonAdapter(private val moshiClient: Moshi) {

    @ToJson
    fun toJson(writer: JsonWriter, value: TokenDetailsResponse?) {
        if (value == null) {
            writer.nullValue()
            return
        }
        writer.beginObject()
        writer.name("type").value(value.type)
        writer.name("token").value(value.token)
        writer.name("expires_on").value(value.expiresOn)
        writer.name("expiry_month").value(value.expiryMonth)
        writer.name("expiry_year").value(value.expiryYear)
        value.scheme?.let { writer.name("scheme").value(it) }
        value.schemeLocal?.let { writer.name("scheme_local").value(it) }
        writer.name("last4").value(value.last4)
        writer.name("bin").value(value.bin)
        value.cardType?.let { writer.name("card_type").value(it) }
        value.cardCategory?.let { writer.name("card_category").value(it) }
        value.issuer?.let { writer.name("issuer").value(it) }
        value.issuerCountry?.let { writer.name("issuer_country").value(it) }
        value.productId?.let { writer.name("product_id").value(it) }
        value.productType?.let { writer.name("product_type").value(it) }
        value.billingAddress?.let {
            writer.name("billing_address")
            moshiClient.adapter(AddressEntity::class.java).toJson(writer, it)
        }
        value.phone?.let {
            writer.name("phone")
            moshiClient.adapter(PhoneEntity::class.java).toJson(writer, it)
        }
        value.name?.let { writer.name("name").value(it) }
        writer.endObject()
    }


    @FromJson
    fun fromJson(reader: JsonReader): TokenDetailsResponse {
        reader.beginObject()
        var type = ""
        var token = ""
        var expiresOn = ""
        var expiryMonth = 0
        var expiryYear = 0
        var scheme: String? = null
        var schemeLocal: String? = null
        var last4 = ""
        var bin = ""
        var cardType: String? = null
        var cardCategory: String? = null
        var issuer: String? = null
        var issuerCountry: String? = null
        var productId: String? = null
        var productType: String? = null
        var billingAddress: AddressEntity? = null
        var phone: PhoneEntity? = null
        var name: String? = null

        while (reader.hasNext()) {
            when (reader.nextName()) {
                "type" -> type = reader.nextString()
                "token" -> token = reader.nextString()
                "expires_on" -> expiresOn = reader.nextString()
                "expiry_month" -> expiryMonth = reader.nextInt()
                "expiry_year" -> expiryYear = reader.nextInt()
                "scheme" -> scheme = reader.nextString()
                "scheme_local" -> schemeLocal = reader.nextString()
                "last4" -> last4 = reader.nextString()
                "bin" -> bin = reader.nextString()
                "card_type" -> cardType = reader.nextString()
                "card_category" -> cardCategory = reader.nextString()
                "issuer" -> issuer = reader.nextString()
                "issuer_country" -> issuerCountry = reader.nextString()
                "product_id" -> productId = reader.nextString()
                "product_type" -> productType = reader.nextString()
                "billing_address" -> billingAddress = moshiClient.adapter(AddressEntity::class.java).fromJson(reader)
                "phone" -> phone = moshiClient.adapter(PhoneEntity::class.java).fromJson(reader)
                "name" -> name = reader.nextString()
                else -> reader.skipValue()
            }
        }
        reader.endObject()
        return TokenDetailsResponse(
            type,
            token,
            expiresOn,
            expiryMonth,
            expiryYear,
            scheme,
            schemeLocal,
            last4,
            bin,
            cardType,
            cardCategory,
            issuer,
            issuerCountry,
            productId,
            productType,
            billingAddress,
            phone,
            name
        )
    }
}
