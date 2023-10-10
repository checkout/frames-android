package com.checkout.threedsecure.model

import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public data class ProcessThreeDSRequest(
    val redirectUrl: String?,
    val successUrl: String,
    val failureUrl: String,
)
