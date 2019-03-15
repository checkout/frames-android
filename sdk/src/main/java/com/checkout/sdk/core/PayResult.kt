package com.checkout.sdk.core

import com.android.volley.VolleyError
import com.checkout.sdk.response.CardTokenisationFail
import com.checkout.sdk.response.CardTokenisationResponse

sealed class PayResult {

    class PayResultSuccess(val response: CardTokenisationResponse) : PayResult()

    class PayResultTokenisationFail(val error: CardTokenisationFail) : PayResult()

    class PayResultVolleyError(val error: VolleyError) : PayResult()

}
