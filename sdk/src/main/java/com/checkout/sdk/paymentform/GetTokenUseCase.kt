package com.checkout.sdk.paymentform

import com.checkout.sdk.CheckoutClient
import com.checkout.sdk.api.TokenApi
import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.core.RequestGenerator
import com.checkout.sdk.core.TokenResult
import com.checkout.sdk.response.CardTokenisationFail
import com.checkout.sdk.response.CardTokenizationResponse
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

open class GetTokenUseCase(
    private val generator: RequestGenerator,
    private val client: CheckoutClient,
    private val callback: Callback,
    private val tokenApi: TokenApi
) : UseCase<Unit> { //, CheckoutClient.OnTokenGenerated {

    private constructor(builder: Builder) : this(
        builder.generator,
        builder.client,
        builder.callback,
        builder.api
    )

    override fun execute() {
        val request = generator.generate()

        CoroutineScope(Dispatchers.IO).launch {
            val deferred = tokenApi.getTokenAsync(request!!)
            var result: Response<CardTokenizationResponse>? = null
                try {
                    result = deferred.await()
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        client.tokenCallback.onTokenResult(TokenResult.TokenResultNetworkError(e))
                        callback.onProgressChanged(false)
                    }
                }
            if (result == null) {
                return@launch
            }

            if (result.isSuccessful) {
                withContext(Dispatchers.Main) {
                    client.tokenCallback.onTokenResult(TokenResult.TokenResultSuccess(result.body()!!))
                    callback.onProgressChanged(false)
                }
            } else {
                val fail =
                    Gson().fromJson(result.errorBody().toString(), CardTokenisationFail::class.java)
                withContext(Dispatchers.Main) {
                    client.tokenCallback.onTokenResult(TokenResult.TokenResultTokenisationFail(fail))
                    callback.onProgressChanged(false)
                }
            }
        }
        callback.onProgressChanged(true)
    }

    open class Builder(
        val generator: RequestGenerator,
        val client: CheckoutClient,
        val api: TokenApi
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
