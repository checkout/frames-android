package com.checkout.frames.error

import androidx.annotation.StringRes
import com.checkout.base.error.CheckoutError

public open class FramesError @JvmOverloads constructor(
    public override val errorCode: String,
    @StringRes
    public val localizedMessage: Int,
    message: String? = null,
    throwable: Throwable? = null,
) : CheckoutError(errorCode, message, throwable)
