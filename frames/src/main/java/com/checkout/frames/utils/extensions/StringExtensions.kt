package com.checkout.frames.utils.extensions

import com.checkout.tokenization.model.ExpiryDate

@SuppressWarnings("MagicNumber")
internal fun String.toExpiryDate(): ExpiryDate {
    val shortMonthDateLength = 3
    val fullMonthDateLength = 4

    return when (this.length) {
        shortMonthDateLength -> ExpiryDate(
            expiryMonth = this[0].toString().toInt(),
            expiryYear = this.substring(1..2).toInt(),
        )

        fullMonthDateLength -> ExpiryDate(
            expiryMonth = this.substring(0..1).toInt(),
            expiryYear = this.substring(2..3).toInt(),
        )

        else -> ExpiryDate(0, 0)
    }
}
