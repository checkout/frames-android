package com.checkout.threedsecure.utils

import android.os.Build
import android.webkit.WebResourceError
import android.webkit.WebResourceResponse
import com.checkout.threedsecure.error.ThreeDSError

internal fun WebResourceResponse.toThreeDSError() = ThreeDSError(this.statusCode.toString(), this.reasonPhrase)

internal fun WebResourceError.toThreeDSError() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    ThreeDSError(this.errorCode.toString(), this.description.toString())
} else null
