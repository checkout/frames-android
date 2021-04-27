package com.checkout.android_sdk.network.utils

import com.checkout.android_sdk.CheckoutAPIClient
import com.checkout.android_sdk.CheckoutAPIClient.OnGooglePayTokenGenerated
import com.checkout.android_sdk.CheckoutAPIClient.OnTokenGenerated

internal interface TokenRequestor {

    fun requestCardToken(
        correlationID: String?,
        requestBody: String,
        joseRequest: Boolean,
        listener: OnTokenGenerated
    )

    fun requestGooglePayToken(
        correlationID: String?,
        requestBody: String,
        listener: OnGooglePayTokenGenerated
    )

    fun fetchJWKS(
        listener: CheckoutAPIClient.OnJWKSFetched
    )
}