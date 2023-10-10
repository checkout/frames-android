package com.checkout.tokenization.mapper.response

import com.checkout.base.error.CheckoutError
import com.checkout.tokenization.error.TokenizationError.Companion.TOKENIZATION_API_MALFORMED_JSON
import com.checkout.tokenization.mapper.TokenizationNetworkDataMapper
import com.checkout.tokenization.model.CVVTokenDetails
import com.checkout.tokenization.response.GetCVVTokenDetailsResponse

/**
 * An implementation of mapping from [GetCVVTokenDetailsResponse] data object to [CVVTokenDetails].
 */
internal class CVVTokenizationNetworkDataMapper : TokenizationNetworkDataMapper<CVVTokenDetails>() {

    override fun <T : Any> createMappedResult(resultBody: T): CVVTokenDetails = when (resultBody) {
        is GetCVVTokenDetailsResponse -> fromCardTokenizationResponse(resultBody)
        else -> throw CheckoutError(
            TOKENIZATION_API_MALFORMED_JSON,
            "${resultBody.javaClass.name} cannot be mapped to a CVVTokenDetails",
        )
    }

    private fun fromCardTokenizationResponse(result: GetCVVTokenDetailsResponse) = with(result) {
        CVVTokenDetails(
            type = type,
            token = token,
            expiresOn = expiresOn,
        )
    }
}
