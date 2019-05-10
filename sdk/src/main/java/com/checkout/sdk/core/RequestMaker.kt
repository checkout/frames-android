package com.checkout.sdk.core

import android.content.Context
import com.checkout.sdk.CheckoutClient
import com.checkout.sdk.api.ApiFactory
import com.checkout.sdk.api.TokenApi
import com.checkout.sdk.executors.Coroutines
import com.checkout.sdk.request.CardTokenizationRequest
import com.checkout.sdk.request.GooglePayTokenizationRequest
import com.checkout.sdk.request.TokenRequest
import com.checkout.sdk.response.CardTokenizationFail
import com.checkout.sdk.response.GooglePayTokenisationFail
import com.checkout.sdk.response.TokenFail
import com.checkout.sdk.response.TokenResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody


class RequestMaker(
    private val key: String,
    private val tokenApi: TokenApi,
    private val coroutines: Coroutines,
    private val tokenCallback: CheckoutClient.TokenCallback,
    private val progressCallback: ProgressCallback? = null
) {

    fun makeTokenRequest(request: TokenRequest) {
        val deferred = when (request) {
            is CardTokenizationRequest -> tokenApi.getTokenAsync(key, request)
            is GooglePayTokenizationRequest -> tokenApi.getGooglePayTokenAsync(key, request)
            else -> throw IllegalArgumentException("Unknown class: ${request.javaClass}")
        }
        coroutines.IOScope.launch {
            val response =
                try {
                    deferred.await()
                } catch (e: Exception) {
                    handleRequestError(e)
                    return@launch
                }

            if (response.isSuccessful) {
                handleResponseSuccessful(response.body()!!)
            } else {
                handleResponseFailure(response.errorBody()!!, request.javaClass)
            }
        }
        progressCallback?.onProgressChanged(true)
    }

    private suspend fun handleRequestError(e: Exception) {
        withContext(coroutines.Main) {
            tokenCallback.onTokenResult(TokenResult.TokenResultNetworkError(e))
            progressCallback?.onProgressChanged(false)
        }
    }

    private suspend fun handleResponseSuccessful(body: TokenResponse) {
        withContext(coroutines.Main) {
            tokenCallback.onTokenResult(TokenResult.TokenResultSuccess(body))
            progressCallback?.onProgressChanged(false)
        }
    }

    private suspend fun handleResponseFailure(
        errorBody: ResponseBody,
        clazz: Class<TokenRequest>
    ) {
        val tokenError = getTokenError(errorBody.string(), clazz)
        withContext(coroutines.Main) {
            tokenCallback.onTokenResult(TokenResult.TokenResultTokenizationFail(tokenError))
            progressCallback?.onProgressChanged(false)
        }
    }

    private fun getTokenError(errorString: String, clazz: Class<TokenRequest>): TokenFail {
        val fail = when (clazz) {
            CardTokenizationRequest::class.java -> CardTokenizationFail::class.java
            GooglePayTokenizationRequest::class.java -> GooglePayTokenisationFail::class.java
            else -> throw IllegalArgumentException("Unknown class: $clazz")
        }
        return Gson().fromJson(errorString, fail)
    }

    interface ProgressCallback {
        fun onProgressChanged(inProgress: Boolean)
    }

    companion object {
        /**
         * Convenience method for simple creation of a RequestMaker
         */
        fun create(context: Context, checkoutClient: CheckoutClient, progressCallback: ProgressCallback? = null): RequestMaker {
            val apiFactory = ApiFactory(context, checkoutClient.environment)
            val tokenApi = apiFactory.api
            return RequestMaker(
                checkoutClient.key,
                tokenApi,
                Coroutines(),
                checkoutClient.tokenCallback,
                progressCallback
            )
        }
    }
}
