package com.checkout.tokenization.mapper.response

import com.checkout.base.error.CheckoutError
import com.checkout.tokenization.error.TokenizationError.Companion.TOKENIZATION_API_MALFORMED_JSON
import com.checkout.tokenization.mapper.TokenizationNetworkDataMapper
import com.checkout.tokenization.model.TokenDetails
import com.checkout.tokenization.response.GetTokenDetailsResponse

/**
 * An implementation of mapping from [GetTokenDetailsResponse] data object to [TokenDetails].
 */
internal class CardTokenizationNetworkDataMapper : TokenizationNetworkDataMapper<TokenDetails>() {

    override fun <T : Any> createMappedResult(resultBody: T): TokenDetails =
        when (resultBody) {
            is GetTokenDetailsResponse -> fromCardTokenizationResponse(resultBody)
            else -> throw CheckoutError(
                TOKENIZATION_API_MALFORMED_JSON,
                "${resultBody.javaClass.name} cannot be mapped to a TokenDetails",
            )
        }

    private fun fromCardTokenizationResponse(result: GetTokenDetailsResponse) =
        TokenDetails(
            type = result.type,
            token = result.token,
            expiresOn = result.expiresOn,
            expiryMonth = result.expiryMonth,
            expiryYear = result.expiryYear,
            scheme = result.scheme,
            schemeLocal = result.schemeLocal,
            last4 = result.last4,
            bin = result.bin,
            cardType = result.cardType,
            cardCategory = result.cardCategory,
            issuer = result.issuer,
            issuerCountry = result.issuerCountry,
            productId = result.productId,
            productType = result.productType,
            billingAddress = result.billingAddress?.let { AddressEntityToAddressDataMapper().map(it) },
            phone = result.phone?.let {
                PhoneEntityToPhoneDataMapper().map(from = it to result.billingAddress?.country)
            },
            name = result.name,
        )
}
