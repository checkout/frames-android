package com.checkout.api

import androidx.annotation.UiThread
import com.checkout.threedsecure.model.ThreeDSRequest
import com.checkout.tokenization.model.CardTokenRequest
import com.checkout.tokenization.model.GooglePayTokenRequest

/**
 * Provides token creation and 3DS handling functionality.
 */
public interface CheckoutApiService {

    /**
     * Creates token for card payment.
     *
     * @param request - [CardTokenRequest] contains card details and result handlers.
     */
    @UiThread
    public fun createToken(request: CardTokenRequest)

    /**
     * Creates token for google pay.
     *
     * @param request - [GooglePayTokenRequest] contains google pay details and result handlers.
     */
    @UiThread
    public fun createToken(request: GooglePayTokenRequest)

    /**
     * Handle 3DS process with WebView.
     *
     * @param request - [ThreeDSRequest] contains all data and listeners which are needed for 3DS flow.
     */
    public fun handleThreeDS(request: ThreeDSRequest)
}
