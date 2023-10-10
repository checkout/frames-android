package com.checkout.tokenization.mapper.request

import com.checkout.base.mapper.Mapper
import com.checkout.tokenization.model.Address
import com.checkout.validation.model.AddressValidationRequest

/**
 * Mapping of [Address] to [AddressValidationRequest]
 */
internal class AddressToAddressValidationRequestDataMapper : Mapper<Address, AddressValidationRequest> {

    override fun map(from: Address): AddressValidationRequest = AddressValidationRequest(
        from.addressLine1,
        from.addressLine2,
        from.city,
        from.state,
        from.zip,
        from.country,
    )
}
