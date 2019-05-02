package com.checkout.sdk.core

import com.checkout.sdk.CheckoutClient
import com.checkout.sdk.api.TokenApi
import com.checkout.sdk.executors.Coroutines
import com.checkout.sdk.request.CardTokenizationRequest
import com.checkout.sdk.response.CardTokenizationFail
import com.checkout.sdk.response.CardTokenizationResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class RequestMaker(
    private val tokenApi: TokenApi,
    private val coroutines: Coroutines,
    private val tokenCallback: CheckoutClient.TokenCallback,
    private val progressCallback: ProgressCallback
) {

    fun makeTokenRequest(request: CardTokenizationRequest) {
        val deferred = tokenApi.getTokenAsync(request)
        coroutines.IOScope.launch {
            val response =
                try {
                    deferred.await()
                } catch (e: Exception) {
                    handleRequestError(e)
                    return@launch
                }

            if (response.isSuccessful) {
                handleResponseSuccessful(response)
            } else {
                handleResponseFailure(response)
            }
        }
        progressCallback.onProgressChanged(true)
    }

    private suspend fun handleRequestError(e: Exception) {
        withContext(coroutines.Main) {
            tokenCallback.onTokenResult(TokenResult.TokenResultNetworkError(e))
            progressCallback.onProgressChanged(false)
        }
    }

    private suspend fun handleResponseSuccessful(result: Response<CardTokenizationResponse>) {
        withContext(coroutines.Main) {
            tokenCallback.onTokenResult(TokenResult.TokenResultSuccess(result.body()!!))
            progressCallback.onProgressChanged(false)
        }
    }

    private suspend fun handleResponseFailure(result: Response<CardTokenizationResponse>) {
        val errorString = result.errorBody()!!.string()
        val fail =
            Gson().fromJson(errorString, CardTokenizationFail::class.java)
        withContext(coroutines.Main) {
            tokenCallback.onTokenResult(TokenResult.TokenResultTokenisationFail(fail))
            progressCallback.onProgressChanged(false)
        }
    }

    interface ProgressCallback {
        fun onProgressChanged(inProgress: Boolean)
    }
}
