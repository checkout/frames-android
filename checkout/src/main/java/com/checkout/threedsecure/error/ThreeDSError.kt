package com.checkout.threedsecure.error

import com.checkout.base.error.CheckoutError

public class ThreeDSError(errorCode: String, message: String?) : CheckoutError(errorCode, message) {

    internal companion object {
        const val COULD_NOT_EXTRACT_TOKEN = "ThreeDSError:3000"
        const val RECEIVED_FAILURE_URL = "ThreeDSError:3001"
    }
}
