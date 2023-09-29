package com.checkout.validation.validator

import com.checkout.base.model.CardScheme
import com.checkout.validation.api.CVVComponentValidator
import com.checkout.validation.model.CvvValidationRequest
import com.checkout.validation.validator.contract.Validator

internal class CVVComponentDetailsValidator(private val cvvValidator: Validator<CvvValidationRequest, Unit>) :
    CVVComponentValidator {

    override fun validate(cvv: String, cardScheme: CardScheme) = cvvValidator.validate(
        CvvValidationRequest(cvv, cardScheme)
    )
}
