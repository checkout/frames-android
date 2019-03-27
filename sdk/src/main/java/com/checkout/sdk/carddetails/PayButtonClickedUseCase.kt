package com.checkout.sdk.carddetails

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.core.CardDetailsValidity
import com.checkout.sdk.core.CardDetailsValidator

open class PayButtonClickedUseCase(
    private val validator: CardDetailsValidator
) : UseCase<CardDetailsValidity> {

    override fun execute(): CardDetailsValidity {
        return validator.getValidity()
    }
}
