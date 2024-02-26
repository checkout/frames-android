package com.checkout.tokenization.request

import com.checkout.tokenization.entity.TokenDataEntity
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson

internal class CVVTokenNetworkRequestJsonAdapter(private val moshiClient: Moshi) {

    @ToJson
    fun toJson(writer: JsonWriter, value: CVVTokenNetworkRequest?) {
        writer.beginObject()
        writer.name("type").value(value?.type ?: "")
        writer.name("token_data")
        moshiClient.adapter(TokenDataEntity::class.java).toJson(writer, value?.tokenDataEntity)
        writer.endObject()
    }

    @FromJson
    fun fromJson(reader: JsonReader): CVVTokenNetworkRequest {
        var type = ""
        var tokenDataEntity: TokenDataEntity? = null
        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "type" -> type = reader.nextString()
                "token_data" -> tokenDataEntity = moshiClient.adapter(TokenDataEntity::class.java).fromJson(reader)
                else -> reader.skipValue()
            }
        }
        reader.endObject()
        return CVVTokenNetworkRequest(type, tokenDataEntity ?: TokenDataEntity(""))
    }
}
