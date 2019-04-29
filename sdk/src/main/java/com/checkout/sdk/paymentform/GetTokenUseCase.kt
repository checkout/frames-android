package com.checkout.sdk.paymentform

import com.android.volley.VolleyError
import com.checkout.sdk.CheckoutClient
import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.core.RequestGenerator
import com.checkout.sdk.core.TokenResult
import com.checkout.sdk.response.CardTokenisationFail
import com.checkout.sdk.response.CardTokenisationResponse

open class GetTokenUseCase(
    private val generator: RequestGenerator,
    private val client: CheckoutClient,
    private val callback: Callback
) : UseCase<Unit>, CheckoutClient.OnTokenGenerated {

    private constructor(builder: Builder) : this(
        builder.generator,
        builder.client,
        builder.callback
    )

    override fun execute() {
        val request = generator.generate()
        client.generateToken(request, this)
        callback.onProgressChanged(true)
    }

    override fun onTokenGenerated(response: CardTokenisationResponse) {
        client.tokenCallback.onTokenResult(TokenResult.TokenResultSuccess(response))
        callback.onProgressChanged(false)
    }

    override fun onError(error: CardTokenisationFail) {
        client.tokenCallback.onTokenResult(TokenResult.TokenResultTokenisationFail(error))
        callback.onProgressChanged(false)
    }

    override fun onNetworkError(error: VolleyError) {
        client.tokenCallback.onTokenResult(TokenResult.TokenResultVolleyError(error))
        callback.onProgressChanged(false)
    }

    open class Builder(
        val generator: RequestGenerator,
        val client: CheckoutClient
    ) {
        lateinit var callback: Callback
            private set

        open fun callback(callback: Callback) = apply { this.callback = callback }

        open fun build() = GetTokenUseCase(this)
    }

    interface Callback {
        fun onProgressChanged(inProgress: Boolean)
    }
}
