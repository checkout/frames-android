package com.checkout.tokenization.mapper.response

import com.checkout.base.mapper.Mapper
import com.checkout.base.model.Country
import com.checkout.tokenization.entity.AddressEntity
import com.checkout.tokenization.model.Address

/**
 * Mapping of [AddressEntity] to [Address]
 */
internal class AddressEntityToAddressDataMapper : Mapper<AddressEntity, Address> {

    override fun map(from: AddressEntity) = Address(
        from.addressLine1,
        from.addressLine2,
        from.city,
        from.state,
        from.zip,
        Country.from(from.country),
    )
}
