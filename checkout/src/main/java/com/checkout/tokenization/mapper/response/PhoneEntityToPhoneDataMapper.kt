package com.checkout.tokenization.mapper.response

import com.checkout.base.mapper.Mapper
import com.checkout.base.model.Country
import com.checkout.tokenization.entity.PhoneEntity
import com.checkout.tokenization.model.Phone

/**
 * Mapping of [PhoneEntity] to [Phone]
 */
internal class PhoneEntityToPhoneDataMapper : Mapper<Pair<PhoneEntity, String?>, Phone> {

    override fun map(from: Pair<PhoneEntity, String?>): Phone = Phone(
        from.first.number,
        Country.getCountry(from.first.countryCode, from.second)
    )
}
