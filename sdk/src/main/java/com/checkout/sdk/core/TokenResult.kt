package com.checkout.sdk.core

import com.android.volley.VolleyError
import com.checkout.sdk.response.CardTokenisationFail
import com.checkout.sdk.response.CardTokenisationResponse

sealed class TokenResult {

    class TokenResultSuccess(val response: CardTokenisationResponse) : TokenResult()

    class TokenResultTokenisationFail(val error: CardTokenisationFail) : TokenResult()

    class TokenResultVolleyError(val error: VolleyError) : TokenResult()

}
