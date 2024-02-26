package com.checkout.tokenization.request

import com.checkout.tokenization.entity.GooglePayEntity
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

internal object GooglePayEntityJsonAdapter {
    @ToJson
    fun toJson(writer: JsonWriter, value: GooglePayEntity?) {
        writer.beginObject()
        writer.name("signature").value(value?.signature)
        writer.name("protocolVersion").value(value?.protocolVersion)
        writer.name("signedMessage").value(value?.signedMessage)
        writer.endObject()
    }

    @FromJson
    fun fromJson(reader: JsonReader): GooglePayEntity {
        var signature: String? = null
        var protocolVersion: String? = null
        var signedMessage: String? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "signature" -> signature = reader.nextString()
                "protocolVersion" -> protocolVersion = reader.nextString()
                "signedMessage" -> signedMessage = reader.nextString()
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        return GooglePayEntity(signature, protocolVersion, signedMessage)
    }
}
