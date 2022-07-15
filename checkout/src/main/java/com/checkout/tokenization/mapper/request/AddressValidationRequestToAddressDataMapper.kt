package com.checkout.tokenization.mapper.request

import com.checkout.base.mapper.Mapper
import com.checkout.tokenization.model.Address
import com.checkout.validation.model.AddressValidationRequest

/**
 * Mapping of [AddressValidationRequest] to [Address]
 */
internal class AddressValidationRequestToAddressDataMapper : Mapper<AddressValidationRequest, Address> {

    override fun map(from: AddressValidationRequest): Address = Address(
        from.addressLine1,
        from.addressLine2,
        from.city,
        from.state,
        from.zip,
        from.country
    )
}
