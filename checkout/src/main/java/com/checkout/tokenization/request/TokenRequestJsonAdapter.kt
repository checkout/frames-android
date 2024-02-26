package com.checkout.tokenization.request

import com.checkout.tokenization.entity.AddressEntity
import com.checkout.tokenization.entity.PhoneEntity
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson

internal class TokenRequestJsonAdapter(private val moshiClient: Moshi) {

    @ToJson
    fun toJson(writer: JsonWriter, value: TokenRequest?) {
        if (value == null) {
            writer.nullValue()
            return
        }
        writer.beginObject()
        writer.name("type").value(value.type)
        writer.name("number").value(value.number)
        writer.name("expiry_month").value(value.expiryMonth)
        writer.name("expiry_year").value(value.expiryYear)
        value.name?.let { writer.name("name").value(it) }
        value.cvv?.let { writer.name("cvv").value(it) }
        value.billingAddress?.let {
            writer.name("billing_address")
            moshiClient.adapter(AddressEntity::class.java).toJson(writer, it)
        }
        value.phone?.let {
            writer.name("phone")
            moshiClient.adapter(PhoneEntity::class.java).toJson(writer, it)
        }
        writer.endObject()
    }


    @FromJson
    fun fromJson(reader: JsonReader): TokenRequest {
        reader.beginObject()
        var type = ""
        var number = ""
        var expiryMonth = ""
        var expiryYear = ""
        var name: String? = null
        var cvv: String? = null
        var billingAddress: AddressEntity? = null
        var phone: PhoneEntity? = null

        while (reader.hasNext()) {
            when (reader.nextName()) {
                "type" -> type = reader.nextString()
                "number" -> number = reader.nextString()
                "expiry_month" -> expiryMonth = reader.nextString()
                "expiry_year" -> expiryYear = reader.nextString()
                "name" -> name = reader.nextString()
                "cvv" -> cvv = reader.nextString()
                "billing_address" -> billingAddress = moshiClient.adapter(AddressEntity::class.java).fromJson(reader)
                "phone" -> phone = moshiClient.adapter(PhoneEntity::class.java).fromJson(reader)
                else -> reader.skipValue()
            }
        }
        reader.endObject()
        return TokenRequest(type, number, expiryMonth, expiryYear, name, cvv, billingAddress, phone)
    }
}
