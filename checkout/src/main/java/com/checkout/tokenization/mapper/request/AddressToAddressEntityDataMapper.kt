package com.checkout.tokenization.mapper.request

import com.checkout.base.mapper.Mapper
import com.checkout.tokenization.entity.AddressEntity
import com.checkout.tokenization.model.Address

/**
 * Mapping of [Address] to [AddressEntity]
 */
internal class AddressToAddressEntityDataMapper : Mapper<Address, AddressEntity> {

    override fun map(from: Address): AddressEntity = AddressEntity(
        from.addressLine1,
        from.addressLine2,
        from.city,
        from.state,
        from.zip,
        from.country?.iso3166Alpha2
    )
}
