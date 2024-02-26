package com.checkout.tokenization.request

import com.checkout.network.response.ErrorResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types

internal class ErrorResponseJsonAdapter(private val moshiClient: Moshi) {

    @ToJson
    fun toJson(writer: JsonWriter, value: ErrorResponse?) {
        if (value == null) {
            writer.nullValue()
            return
        }
        writer.beginObject()
        writer.name("request_id").value(value.requestId)
        writer.name("error_type").value(value.errorType)
        writer.name("error_codes").jssonValue(value.errorCodes)
        writer.endObject()
    }

    @FromJson
    fun fromJson(reader: JsonReader): ErrorResponse {
        reader.beginObject()
        var requestId: String? = null
        var errorType: String? = null
        var errorCodes: List<String>? = null

        while (reader.hasNext()) {
            when (reader.nextName()) {
                "request_id" -> requestId = reader.nextString()
                "error_type" -> errorType = reader.nextString()
                "error_codes" -> errorCodes =
                    moshiClient.adapter<List<String>>(Types.newParameterizedType(List::class.java, String::class.java))
                        .fromJson(reader)

                else -> reader.skipValue()
            }
        }
        reader.endObject()
        return ErrorResponse(requestId, errorType, errorCodes)
    }

    private fun JsonWriter.jssonValue(value: List<String>?) {
        beginArray()
        value?.forEach { value(it) }
        endArray()
    }

}
