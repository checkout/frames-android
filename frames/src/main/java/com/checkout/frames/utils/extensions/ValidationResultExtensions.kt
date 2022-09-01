package com.checkout.frames.utils.extensions

import com.checkout.validation.model.ValidationResult

internal fun ValidationResult<Any>.isValid(): Boolean = this is ValidationResult.Success
