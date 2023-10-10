package com.checkout.tokenization.model

/**
 * Sealed class representing the result of a CVV tokenization operation.
 * It can either be a success containing [Success] or a failure containing [Failure].
 */
public sealed class CVVTokenizationResultHandler {

    /**
     * Represents a successful CVV tokenization result.
     *
     * @property tokenDetails [CVVTokenDetails] The details of the generated CVV token.
     */
    public data class Success(val tokenDetails: CVVTokenDetails) : CVVTokenizationResultHandler()

    /**
     * Represents a failed CVV tokenization result.
     *
     * @property errorMessage A description of the error that occurred during tokenization.
     */
    public data class Failure(val errorMessage: String) : CVVTokenizationResultHandler()
}
