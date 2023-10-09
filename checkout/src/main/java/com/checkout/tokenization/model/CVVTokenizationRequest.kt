package com.checkout.tokenization.model

import com.checkout.base.model.CardScheme

/**
 * Represents a request to tokenize a CVV.
 *
 * @param cvv The CVV value to tokenize.
 * @param cardScheme (Optional) The card scheme for the CVV to validate, if known. Default is [CardScheme.UNKNOWN].
 * @param resultHandler A callback function to handle the tokenization result. It will receive a
 *                      [CVVTokenizationResultHandler] which can be used to handle success or failure outcomes.
 *
 */
public data class CVVTokenizationRequest(
    val cvv: String,
    val cardScheme: CardScheme = CardScheme.UNKNOWN,
    val resultHandler: (CVVTokenizationResultHandler) -> Unit,
)
