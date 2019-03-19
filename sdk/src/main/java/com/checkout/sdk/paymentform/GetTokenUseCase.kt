package com.checkout.sdk.paymentform

import com.android.volley.VolleyError
import com.checkout.sdk.CheckoutClient
import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.core.RequestGenerator
import com.checkout.sdk.response.CardTokenisationFail
import com.checkout.sdk.response.CardTokenisationResponse

class GetTokenUseCase(
    private val generator: RequestGenerator,
    private val client: CheckoutClient
) : UseCase<Unit>, CheckoutClient.OnTokenGenerated {

    override fun execute() {
        val request = generator.generate()
        client.generateToken(request)
        client.tokenListener.onTokenRequested()
    }

    override fun onTokenGenerated(response: CardTokenisationResponse?) {
        client.tokenListener.onTokenGenerated(response)
    }

    override fun onError(error: CardTokenisationFail) {
        client.tokenListener.onError(error)
    }

    override fun onNetworkError(error: VolleyError?) {
        client.tokenListener.onNetworkError(error)
    }

    override fun onTokenRequested() {
        // TODO
    }
}
