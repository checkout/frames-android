package com.checkout.sdk

import android.content.Context
import com.checkout.sdk.api.ApiFactory
import com.checkout.sdk.api.TokenApi
import com.checkout.sdk.core.TokenResult
import com.checkout.sdk.executors.Coroutines
import com.checkout.sdk.request.CardTokenizationRequest
import com.checkout.sdk.request.GooglePayTokenizationRequest
import com.checkout.sdk.request.TokenRequest
import com.checkout.sdk.response.CardTokenizationFail
import com.checkout.sdk.response.GooglePayTokenisationFail
import com.checkout.sdk.response.TokenFail
import com.checkout.sdk.response.TokenResponse
import com.checkout.sdk.utils.Environment
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody


class CheckoutClient(
    private val key: String,
    private val tokenApi: TokenApi,
    private val coroutines: Coroutines,
    private val tokenCallback: TokenCallback,
    var progressCallback: ProgressCallback? = null
) {

    /**
     * Request a token:
     * - either straight from card details: CardTokenizationRequest, or
     * - from a GooglePay generated token: GooglePayTokenizationRequest
     */
    fun requestToken(request: TokenRequest) {
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
            tokenCallback.onTokenResult(
                TokenResult.TokenResultTokenizationFail(
                    tokenError
                )
            )
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
         * Convenience method for simple creation of a CheckoutClient
         *
         * @param context The android context (activity or application context)
         * @param key Your public key (https://sandbox.checkout.com/settings/channels -> Public Key)
         * @param environment Sandbox (for testing) or live
         * @param tokenCallback The callback for receiveing your Token, or an Error
         */
        fun create(context: Context, key: String, environment: Environment, tokenCallback: TokenCallback): CheckoutClient {
            val apiFactory = ApiFactory(context, environment)
            val tokenApi = apiFactory.api
            return CheckoutClient(
                key,
                tokenApi,
                Coroutines(),
                tokenCallback
            )
        }
    }

    /**
     * Receive your result from your token request (requestToken) via this callback
     *
     * TokenResult will be one of the following:
     * - TokenResultSuccess if you receive the Token successfully
     * - TokenResultTokenizationFail if you there is an error creating your token (typically due
     *   to incorrect values being sent or a server error)
     * - TokenResultNetworkError if Network problems (e.g. being Offline) case a failure
     */
    interface TokenCallback {
        fun onTokenResult(tokenResult: TokenResult)
    }
}
