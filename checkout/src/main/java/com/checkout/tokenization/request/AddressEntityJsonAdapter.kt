package com.checkout.tokenization.request

import com.checkout.tokenization.entity.AddressEntity
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

internal object AddressEntityJsonAdapter {
    @ToJson
    fun toJson(writer: JsonWriter, value: AddressEntity?) {
        writer.beginObject()
        writer.name("address_line1").value(value?.addressLine1 ?: "")
        writer.name("address_line2").value(value?.addressLine2 ?: "")
        writer.name("city").value(value?.city ?: "")
        writer.name("state").value(value?.state ?: "")
        writer.name("zip").value(value?.zip ?: "")
        writer.name("country").value(value?.country ?: "")
        writer.endObject()
    }

    @FromJson
    fun fromJson(reader: JsonReader): AddressEntity {
        var addressLine1 = ""
        var addressLine2 = ""
        var city = ""
        var state = ""
        var zip = ""
        var country: String? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "address_line1" -> addressLine1 = reader.nextString()
                "address_line2" -> addressLine2 = reader.nextString()
                "city" -> city = reader.nextString()
                "state" -> state = reader.nextString()
                "zip" -> zip = reader.nextString()
                "country" -> country = reader.nextString()
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        return AddressEntity(addressLine1, addressLine2, city, state, zip, country)
    }
}
