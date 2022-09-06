package com.checkout.frames.utils.extensions

import com.checkout.validation.error.ValidationError
import com.checkout.validation.model.ValidationResult

internal fun ValidationResult<Any>.isDateInThePastError(): Boolean =
    (this as? ValidationResult.Failure)?.error?.errorCode == ValidationError.EXPIRY_DATE_IN_PAST

internal fun ValidationResult<Any>.isValid(): Boolean = this is ValidationResult.Success
