package com.checkout.tokenization.request

import com.checkout.tokenization.response.CVVTokenDetailsResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

internal object CVVTokenDetailsResponseJsonAdapter {
    @ToJson
    fun toJson(writer: JsonWriter, value: CVVTokenDetailsResponse?) {
        writer.beginObject()
        writer.name("type").value(value?.type ?: "")
        writer.name("token").value(value?.token ?: "")
        writer.name("expires_on").value(value?.expiresOn ?: "")
        writer.endObject()
    }

    @FromJson
    fun fromJson(reader: JsonReader): CVVTokenDetailsResponse {
        var type = ""
        var token = ""
        var expiresOn = ""
        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "type" -> type = reader.nextString()
                "token" -> token = reader.nextString()
                "expires_on" -> expiresOn = reader.nextString()
                else -> reader.skipValue()
            }
        }
        reader.endObject()
        return CVVTokenDetailsResponse(type, token, expiresOn)
    }
}
