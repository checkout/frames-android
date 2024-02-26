package com.checkout.tokenization.request

import com.checkout.tokenization.entity.TokenDataEntity
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

internal object TokenDataEntityJsonAdapter {

    @ToJson
    fun toJson(writer: JsonWriter, value: TokenDataEntity?) {
        writer.beginObject()
        writer.name("cvv").value(value?.cvv ?: "")
        writer.endObject()
    }

    @FromJson
    fun fromJson(reader: JsonReader): TokenDataEntity {
        var cvv = ""
        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "cvv" -> cvv = reader.nextString()
                else -> reader.skipValue()
            }
        }
        reader.endObject()
        return TokenDataEntity(cvv)
    }
}
