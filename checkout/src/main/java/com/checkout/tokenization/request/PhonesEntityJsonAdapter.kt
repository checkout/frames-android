package com.checkout.tokenization.request

import com.checkout.tokenization.entity.PhoneEntity
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

internal object PhonesEntityJsonAdapter {
    @ToJson
    fun toJson(writer: JsonWriter, value: PhoneEntity?) {
        writer.beginObject()
        writer.name("number").value(value?.number ?: "")
        writer.name("country_code").value(value?.countryCode ?: "")
        writer.endObject()
    }

    @FromJson
    fun fromJson(reader: JsonReader): PhoneEntity {
        var number = ""
        var countryCode: String? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "number" -> number = reader.nextString()
                "country_code" -> countryCode = reader.nextString()
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        return PhoneEntity(number, countryCode)
    }
}
