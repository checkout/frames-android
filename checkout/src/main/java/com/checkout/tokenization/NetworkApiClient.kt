package com.checkout.tokenization

import com.checkout.network.response.NetworkApiResponse
import com.checkout.tokenization.request.GooglePayTokenNetworkRequest
import com.checkout.tokenization.request.TokenRequest
import com.checkout.tokenization.response.TokenDetailsResponse

/**
 * Common Network api interface
 */
internal interface NetworkApiClient {

    /** Sending cardToken request
     *
     * @param cardTokenRequest: the request to execute
     * @return response with its body parsed as a String
     */
    suspend fun sendCardTokenRequest(cardTokenRequest: TokenRequest): NetworkApiResponse<TokenDetailsResponse>

    /** Sending GooglePayToken request
     *
     * @param googlePayTokenNetworkRequest: the request to execute
     * @return response with its body parsed as a String
     */
    suspend fun sendGooglePayTokenRequest(
        googlePayTokenNetworkRequest: GooglePayTokenNetworkRequest
    ): NetworkApiResponse<TokenDetailsResponse>
}
