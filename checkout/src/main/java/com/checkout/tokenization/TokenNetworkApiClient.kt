package com.checkout.tokenization

import com.checkout.network.extension.executeHttpRequest
import com.checkout.network.response.ErrorResponse
import com.checkout.network.response.NetworkApiResponse
import com.checkout.tokenization.request.GooglePayTokenNetworkRequest
import com.checkout.tokenization.request.TokenRequest
import com.checkout.tokenization.response.TokenDetailsResponse
import com.checkout.tokenization.utils.TokenizationConstants
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * An implementation of [TokenNetworkApiClient] that makes network requests to the Token API.
 */
internal class TokenNetworkApiClient(
    private val url: String,
    private val okHttpClient: OkHttpClient,
    private val moshiClient: Moshi
) : NetworkApiClient {

    override suspend fun sendCardTokenRequest(
        cardTokenRequest: TokenRequest
    ): NetworkApiResponse<TokenDetailsResponse> {

        val jsonTokenRequestAdapter = moshiClient.adapter(TokenRequest::class.java)

        val requestBody =
            jsonTokenRequestAdapter.toJson(cardTokenRequest).toRequestBody(TokenizationConstants.jsonMediaType)

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        return okHttpClient.executeHttpRequest(
            request,
            moshiClient.adapter(TokenDetailsResponse::class.java),
            moshiClient.adapter(ErrorResponse::class.java)
        )
    }

    override suspend fun sendGooglePayTokenRequest(
        googlePayTokenNetworkRequest: GooglePayTokenNetworkRequest
    ): NetworkApiResponse<TokenDetailsResponse> {
        val jsonGooglePayTokenRequestAdapter = moshiClient.adapter(GooglePayTokenNetworkRequest::class.java)

        val requestBody =
            jsonGooglePayTokenRequestAdapter.toJson(
                googlePayTokenNetworkRequest
            ).toRequestBody(TokenizationConstants.jsonMediaType)

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        return okHttpClient.executeHttpRequest(
            request,
            moshiClient.adapter(TokenDetailsResponse::class.java),
            moshiClient.adapter(ErrorResponse::class.java)
        )
    }
}
