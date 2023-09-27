package com.checkout

import androidx.annotation.RestrictTo
import com.checkout.validation.api.CVVComponentValidator
import com.checkout.validation.validator.CVVComponentDetailsValidator
import com.checkout.validation.validator.CvvValidator

/**
 * Factory class for cvv validator creation
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public object CVVComponentValidatorFactory {

    /**
     * Creates cvv validator.
     * @return [CVVComponentValidator] for individual CVV details components validation.
     */
    public fun create(): CVVComponentValidator {
        return CVVComponentDetailsValidator(CvvValidator())
    }
}
