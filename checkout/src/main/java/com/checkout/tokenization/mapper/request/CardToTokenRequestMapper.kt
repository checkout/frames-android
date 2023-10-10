package com.checkout.tokenization.mapper.request

import com.checkout.base.mapper.Mapper
import com.checkout.tokenization.model.Card
import com.checkout.tokenization.request.TokenRequest
import com.checkout.tokenization.utils.TokenizationConstants

/**
 * Mapping of [Card] to [TokenRequest]
 */
internal class CardToTokenRequestMapper : Mapper<Card, TokenRequest> {

    override fun map(from: Card): TokenRequest = TokenRequest(
        TokenizationConstants.CARD,
        from.number,
        from.expiryDate.expiryMonth.toString(),
        from.expiryDate.expiryYear.toString(),
        from.name,
        from.cvv,
        from.billingAddress?.let { AddressToAddressEntityDataMapper().map(it) },
        from.phone?.let { PhoneToPhoneEntityDataMapper().map(it) },
    )
}
