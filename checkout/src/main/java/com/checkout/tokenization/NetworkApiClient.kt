package com.checkout.tokenization

import com.checkout.network.response.NetworkApiResponse
import com.checkout.tokenization.request.CVVTokenNetworkRequest
import com.checkout.tokenization.request.GooglePayTokenNetworkRequest
import com.checkout.tokenization.request.TokenRequest
import com.checkout.tokenization.response.CVVTokenDetailsResponse
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

    /**
     * Sends a CVV tokenization request to the network.
     *
     * @param cvvTokenNetworkRequest The network request containing the CVV tokenization details.
     *
     * @return A [NetworkApiResponse] representing the response from the network.
     *         It encapsulates the result as a [CVVTokenDetailsResponse] on success or an error message on failure.
     */
    suspend fun sendCVVTokenRequest(cvvTokenNetworkRequest: CVVTokenNetworkRequest):
            NetworkApiResponse<CVVTokenDetailsResponse>

    /** Sending GooglePayToken request
     *
     * @param googlePayTokenNetworkRequest: the request to execute
     * @return response with its body parsed as a String
     */
    suspend fun sendGooglePayTokenRequest(
        googlePayTokenNetworkRequest: GooglePayTokenNetworkRequest
    ): NetworkApiResponse<TokenDetailsResponse>
}
