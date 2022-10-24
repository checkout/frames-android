package com.checkout.frames.tokenization

import com.checkout.api.CheckoutApiService
import com.checkout.base.usecase.UseCase
import com.checkout.tokenization.model.CardTokenRequest
import com.checkout.tokenization.model.TokenDetails

internal class CardTokenizationUseCase(
    private val checkoutApiService: CheckoutApiService,
    /** Success callback from merchant. */
    private val onSuccess: (tokenDetails: TokenDetails) -> Unit,
    /** Failure callback from merchant. */
    private val onFailure: (errorMessage: String) -> Unit
) : UseCase<InternalCardTokenRequest, Unit> {

    override fun execute(data: InternalCardTokenRequest) = checkoutApiService.createToken(
        CardTokenRequest(
            card = data.card,
            onSuccess = {
                data.onSuccess()
                onSuccess(it)
            },
            onFailure = {
                data.onFailure()
                onFailure(it)
            }
        )
    )
}
