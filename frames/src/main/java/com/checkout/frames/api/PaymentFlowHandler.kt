package com.checkout.frames.api

import com.checkout.tokenization.model.TokenDetails

/**
 * Payment flow handler
 */
public interface PaymentFlowHandler {

    /**
     * Invoked when tokenization is started.
     * Can be used to show a progress bar.
     */
    public fun onSubmit()

    /**
     * Invoked when tokenization is succeed.
     *
     * @param tokenDetails - [TokenDetails]
     */
    public fun onSuccess(tokenDetails: TokenDetails)

    /**
     * Invoked when tokenization is failed.
     *
     * @param errorMessage - [String]
     */
    public fun onFailure(errorMessage: String)

    /**
     * Invoked when user decides to close payment details screen
     */
    public fun onBackPressed()
}
