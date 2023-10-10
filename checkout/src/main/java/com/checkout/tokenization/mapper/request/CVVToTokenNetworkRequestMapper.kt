package com.checkout.tokenization.mapper.request

import com.checkout.base.mapper.Mapper
import com.checkout.tokenization.entity.TokenDataEntity
import com.checkout.tokenization.model.CVVTokenizationRequest
import com.checkout.tokenization.request.CVVTokenNetworkRequest
import com.checkout.tokenization.utils.TokenizationConstants

/**
 * Mapping of [CVVTokenizationRequest] to [CVVTokenNetworkRequest]
 */
internal class CVVToTokenNetworkRequestMapper : Mapper<CVVTokenizationRequest, CVVTokenNetworkRequest> {

    override fun map(from: CVVTokenizationRequest): CVVTokenNetworkRequest = CVVTokenNetworkRequest(
        TokenizationConstants.CVV,
        TokenDataEntity(from.cvv)
    )
}
