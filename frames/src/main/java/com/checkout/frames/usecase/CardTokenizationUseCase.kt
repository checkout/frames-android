package com.checkout.frames.usecase

import com.checkout.api.CheckoutApiService
import com.checkout.base.usecase.UseCase
import com.checkout.frames.model.request.InternalCardTokenRequest
import com.checkout.tokenization.model.CardTokenRequest
import com.checkout.tokenization.model.TokenDetails

internal class CardTokenizationUseCase(
    private val checkoutApiService: CheckoutApiService,
    /** On tokenization start callback from merchant. */
    private val onStart: () -> Unit,
    /** Success callback from merchant. */
    private val onSuccess: (tokenDetails: TokenDetails) -> Unit,
    /** Failure callback from merchant. */
    private val onFailure: (errorMessage: String) -> Unit
) : UseCase<InternalCardTokenRequest, Unit> {

    override fun execute(data: InternalCardTokenRequest) {
        onStart()

        checkoutApiService.createToken(
            CardTokenRequest(
                card = data.card,
                onSuccess = { handleSuccess(it, data.onSuccess) },
                onFailure = { handleFailure(it, data.onFailure) }
            )
        )
    }

    private fun handleSuccess(tokenDetails: TokenDetails, onSuccessInternal: () -> Unit) {
        onSuccessInternal()
        onSuccess(tokenDetails)
    }

    private fun handleFailure(errorMessage: String, onFailureInternal: () -> Unit) {
        onFailureInternal()
        onFailure(errorMessage)
    }
}
