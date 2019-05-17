package com.checkout.sdk.paymentform

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.core.RequestGenerator
import com.checkout.sdk.CheckoutClient

open class GetTokenUseCase(
    private val generator: RequestGenerator,
    private val checkoutClient: CheckoutClient
) : UseCase<Unit> {

    override fun execute() {
        checkoutClient.requestToken(generator.generate()!!)
    }
}
