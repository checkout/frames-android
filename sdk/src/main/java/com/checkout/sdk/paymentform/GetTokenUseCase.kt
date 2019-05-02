package com.checkout.sdk.paymentform

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.core.RequestGenerator
import com.checkout.sdk.core.RequestMaker

open class GetTokenUseCase(
    private val generator: RequestGenerator,
    private val requestMaker: RequestMaker
) : UseCase<Unit> {

    override fun execute() {
        requestMaker.makeTokenRequest(generator.generate()!!)
    }
}
