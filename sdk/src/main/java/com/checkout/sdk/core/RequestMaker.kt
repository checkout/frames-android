package com.checkout.sdk.core

import com.checkout.sdk.CheckoutClient
import com.checkout.sdk.api.TokenApi
import com.checkout.sdk.executors.Executors
import com.checkout.sdk.request.CardTokenisationRequest
import com.checkout.sdk.response.CardTokenisationFail
import com.checkout.sdk.response.CardTokenizationResponse
import com.google.gson.Gson
import retrofit2.Response


class RequestMaker(
    private val tokenApi: TokenApi,
    private val executors: Executors,
    private val tokenCallback: CheckoutClient.TokenCallback,
    private val callback: Callback
) {

    fun makeTokenRequest(request: CardTokenisationRequest) {
        val deferred = tokenApi.getTokenAsync(request)
        executors.launchOnIo {
            val response =
                try {
                    deferred.await()
                } catch (e: Exception) {
                    handleRequestError(e)
                    return@launchOnIo
                }

            if (response.isSuccessful) {
                handleResponseSuccessful(response)
            } else {
                handleResponseFailure(response)
            }
        }
        callback.onProgressChanged(true)
    }

    private suspend fun handleRequestError(e: Exception) {
        executors.backOnMain {
            tokenCallback.onTokenResult(TokenResult.TokenResultNetworkError(e))
            callback.onProgressChanged(false)
        }
    }

    private suspend fun handleResponseSuccessful(result: Response<CardTokenizationResponse>) {
        executors.backOnMain {
            tokenCallback.onTokenResult(TokenResult.TokenResultSuccess(result.body()!!))
            callback.onProgressChanged(false)
        }
    }

    private suspend fun handleResponseFailure(result: Response<CardTokenizationResponse>) {
        val fail =
            Gson().fromJson(result.errorBody().toString(), CardTokenisationFail::class.java)
        executors.backOnMain {
            tokenCallback.onTokenResult(TokenResult.TokenResultTokenisationFail(fail))
            callback.onProgressChanged(false)
        }
    }

    interface Callback {
        fun onProgressChanged(inProgress: Boolean)
    }
}
