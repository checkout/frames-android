package com.checkout.tokenization.request

import com.checkout.tokenization.entity.GooglePayEntity
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson

internal class GooglePayTokenJsonAdapter(private val moshiClient: Moshi) {

    @ToJson
    fun toJson(writer: JsonWriter, value: GooglePayTokenNetworkRequest?) {
        writer.beginObject()
        writer.name("type").value(value?.type ?: "")
        writer.name("token_data")
        moshiClient.adapter(GooglePayEntity::class.java).toJson(writer, value?.tokenData)
        writer.endObject()
    }

    @FromJson
    fun fromJson(reader: JsonReader): GooglePayTokenNetworkRequest {
        var type = ""
        var tokenData: GooglePayEntity? = null
        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "type" -> type = reader.nextString()
                "token_data" -> tokenData = moshiClient.adapter(GooglePayEntity::class.java).fromJson(reader)
                else -> reader.skipValue()
            }
        }
        reader.endObject()
        return GooglePayTokenNetworkRequest(type, tokenData ?: GooglePayEntity("","",""))
    }
}
