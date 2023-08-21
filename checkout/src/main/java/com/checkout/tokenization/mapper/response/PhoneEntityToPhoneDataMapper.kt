package com.checkout.tokenization.mapper.response

import com.checkout.base.mapper.Mapper
import com.checkout.base.model.Country
import com.checkout.tokenization.model.Phone
import com.checkout.tokenization.entity.PhoneEntity

/**
 * Mapping of [PhoneEntity] to [Phone]
 */
internal class PhoneEntityToPhoneDataMapper(private val iso3166Alpha2: String? = null) : Mapper<PhoneEntity, Phone> {

    override fun map(from: PhoneEntity): Phone = Phone(
        from.number,
        Country.getCountry(from.countryCode, iso3166Alpha2)
    )
}
