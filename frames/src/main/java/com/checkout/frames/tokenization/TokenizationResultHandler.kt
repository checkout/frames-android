package com.checkout.frames.tokenization

import com.checkout.tokenization.model.TokenDetails

/**
 * Tokenization result handler
 */
public interface TokenizationResultHandler {

    /**
     * Invoked when tokenization is succeed.
     *
     * @param tokenDetails - [TokenDetails]
     */
    public fun onSuccess(tokenDetails: TokenDetails)

    /** Invoked when tokenization is failed.
     *
     * @param errorMessage - [String]
     */
    public fun onFailure(errorMessage: String)
}
