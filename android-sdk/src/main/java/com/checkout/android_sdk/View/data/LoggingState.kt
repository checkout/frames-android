package com.checkout.android_sdk.View.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
internal data class LoggingState(
    val correlationId: String,
    var paymentFormPresented: Boolean,
    var billingFormPresented: Boolean,
    var threedsWebviewPresented: Boolean,
    var threedsWebviewLoaded: Boolean,
    var threedsWebviewComplete: Boolean,
): Parcelable {

    constructor(): this(
        correlationId = UUID.randomUUID().toString(),
        paymentFormPresented = false,
        billingFormPresented = false,
        threedsWebviewPresented = false,
        threedsWebviewLoaded = false,
        threedsWebviewComplete = false,
    )
}