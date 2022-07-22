package com.checkout.threedsecure.model

internal data class ProcessThreeDSRequest(
    val redirectUrl: String?,
    val successUrl: String,
    val failureUrl: String
)
