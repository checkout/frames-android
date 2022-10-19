package com.checkout.frames.tokenization

import com.checkout.api.CheckoutApiService
import com.checkout.base.usecase.UseCase
import com.checkout.tokenization.model.Card
import com.checkout.tokenization.model.CardTokenRequest
import com.checkout.tokenization.model.TokenDetails

internal class CardTokenizationUseCase(
    private val checkoutApiService: CheckoutApiService,
    private val onSuccess: (tokenDetails: TokenDetails) -> Unit,
    private val onFailure: (errorMessage: String) -> Unit
) : UseCase<Card, Unit> {

    override fun execute(data: Card) = checkoutApiService.createToken(
        CardTokenRequest(data, onSuccess, onFailure)
    )
}
