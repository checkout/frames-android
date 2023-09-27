package com.checkout.validation.api

import androidx.annotation.RestrictTo
import com.checkout.base.model.CardScheme
import com.checkout.validation.model.ValidationResult

/**
 * Validates individual CVV details components.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface CVVComponentValidator {

    /**
     * Validates the given [cvv] according to the [cardScheme].
     *
     * @param cvv - The cvv number represented as [String], should be digits only.
     * @param cardScheme - The card scheme represented as [CardScheme].
     * @return [ValidationResult] with [ValidationResult.Success] or [ValidationResult.Failure].
     */
    public fun validateCvv(cvv: String, cardScheme: CardScheme): ValidationResult<Unit>
}
