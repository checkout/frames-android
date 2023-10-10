package com.checkout.threedsecure.model

import android.view.ViewGroup

public data class ThreeDSRequest(
    val container: ViewGroup,
    val challengeUrl: String,
    val successUrl: String,
    val failureUrl: String,
    val resultHandler: ThreeDSResultHandler,
)

public typealias ThreeDSResultHandler = (result: ThreeDSResult) -> Unit
