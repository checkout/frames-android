package com.checkout.threedsecure.model

import com.checkout.threedsecure.error.ThreeDSError

public sealed class ThreeDSResult {

    /**
     * Used when [ProcessThreeDSRequest.redirectUrl] contains [ThreeDSRequest.successUrl]
     */
    public data class Success(val token: String) : ThreeDSResult()

    /**
     * Used when [ProcessThreeDSRequest.redirectUrl] contains [ThreeDSRequest.successUrl]
     */
    public object Failure : ThreeDSResult()

    /**
     * Used when [ProcessThreeDSRequest.redirectUrl] can't be parsed or when error received from 3DS WebView
     */
    public data class Error(val error: ThreeDSError) : ThreeDSResult()
}
