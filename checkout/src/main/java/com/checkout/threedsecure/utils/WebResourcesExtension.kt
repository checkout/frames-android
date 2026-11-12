package com.checkout.threedsecure.utils

import android.webkit.WebResourceError
import android.webkit.WebResourceResponse
import com.checkout.threedsecure.error.ThreeDSError

internal fun WebResourceResponse.toThreeDSError() = ThreeDSError(this.statusCode.toString(), this.reasonPhrase)

internal fun WebResourceError.toThreeDSError() =
    ThreeDSError(this.errorCode.toString(), this.description.toString())
