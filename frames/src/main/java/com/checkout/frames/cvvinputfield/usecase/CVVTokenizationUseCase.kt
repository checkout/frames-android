package com.checkout.frames.cvvinputfield.usecase

import com.checkout.api.CheckoutApiService
import com.checkout.base.usecase.UseCase
import com.checkout.frames.cvvinputfield.models.InternalCVVTokenRequest
import com.checkout.tokenization.model.CVVTokenizationRequest

internal class CVVTokenizationUseCase(
    private val checkoutApiService: CheckoutApiService,
) : UseCase<InternalCVVTokenRequest, Unit> {

    override fun execute(data: InternalCVVTokenRequest) = checkoutApiService.createToken(
           CVVTokenizationRequest(cvv = data.cvv, cardScheme = data.cardScheme, resultHandler = data.resultHandler)
        )
}
