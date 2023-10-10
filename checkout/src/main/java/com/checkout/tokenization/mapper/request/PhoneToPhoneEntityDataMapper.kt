package com.checkout.tokenization.mapper.request

import com.checkout.base.mapper.Mapper
import com.checkout.tokenization.entity.PhoneEntity
import com.checkout.tokenization.model.Phone

/**
 * Mapping of [Phone] to [PhoneEntity]
 */
internal class PhoneToPhoneEntityDataMapper : Mapper<Phone, PhoneEntity> {

    override fun map(from: Phone): PhoneEntity = PhoneEntity(
        from.number,
        from.country?.dialingCode,
    )
}
