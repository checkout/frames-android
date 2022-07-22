package com.checkout.tokenization.model

import com.checkout.base.error.CheckoutError

public sealed class TokenResult<out S : Any> {

    public data class Success<R : Any>(val result: R) : TokenResult<R>()

    public data class Failure(public val error: CheckoutError) : TokenResult<Nothing>()
}
