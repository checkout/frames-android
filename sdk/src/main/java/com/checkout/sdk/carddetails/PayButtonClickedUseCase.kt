package com.checkout.sdk.carddetails

import com.android.volley.VolleyError
import com.checkout.sdk.CheckoutAPIClient
import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.core.RequestGenerator
import com.checkout.sdk.core.RequestValidator
import com.checkout.sdk.core.RequestValidity
import com.checkout.sdk.response.CardTokenisationFail
import com.checkout.sdk.response.CardTokenisationResponse

open class PayButtonClickedUseCase(
    private val validator: RequestValidator,
    private val generator: RequestGenerator,
    private val client: CheckoutAPIClient,
    @Deprecated("Should be pushed into the CheckoutApiClient soon")
    private val checkoutApiCallback: CheckoutAPIClient.OnTokenGenerated, // TODO: This should only be here temporarily
    private val callback: Callback
) : UseCase<Unit>, CheckoutAPIClient.OnTokenGenerated {

    override fun onTokenGenerated(response: CardTokenisationResponse) {
        checkoutApiCallback.onTokenGenerated(response)
        // TODO: Really we should pass this response to the CheckoutAPiClient (or some object it uses)
//        apiClient.onPayResult(PayResult.PayResultSuccess(response))
    }

    override fun onError(error: CardTokenisationFail) {
        checkoutApiCallback.onError(error)
//        apiClient.onPayResult(PayResult.PayResultTokenisationFail(error))
    }

    override fun onNetworkError(error: VolleyError) {
        checkoutApiCallback.onNetworkError(error)
//        apiClient.onPayResult(PayResult.PayResultVolleyError(error))
    }

    private constructor(builder: PayButtonClickedUseCase.Builder) : this(
        builder.validator,
        builder.generator,
        builder.client,
        builder.checkoutApiCallback,
        builder.callback
    )

    override fun execute() {
        val requestState = validator.getRequestValidity()
        if (requestState.isRequestValid()) {
            client.setTokenListener(this)
            val request = generator.generate()
            client.generateToken(request)
        } else {
            callback.onRequestValidity(requestState)
        }
    }

    open class Builder(
        val validator: RequestValidator,
        val generator: RequestGenerator,
        val client: CheckoutAPIClient,
        val checkoutApiCallback: CheckoutAPIClient.OnTokenGenerated
    ) {
        lateinit var callback: Callback
            private set

        open fun callback(callback: Callback) =
            apply { this.callback = callback }

        open fun build() = PayButtonClickedUseCase(this)
    }

    interface Callback {
        fun onRequestValidity(requestValidity: RequestValidity)
    }
}
