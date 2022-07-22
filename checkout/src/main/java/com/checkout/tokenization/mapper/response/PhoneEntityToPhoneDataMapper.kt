package com.checkout.tokenization.mapper.response

import com.checkout.base.mapper.Mapper
import com.checkout.base.model.Country
import com.checkout.tokenization.model.Phone
import com.checkout.tokenization.entity.PhoneEntity

/**
 * Mapping of [PhoneEntity] to [Phone]
 */
internal class PhoneEntityToPhoneDataMapper : Mapper<PhoneEntity, Phone> {

    override fun map(from: PhoneEntity): Phone = Phone(
        from.number,
        Country.getCountryFromDialingCode(from.countryCode),
    )
}
