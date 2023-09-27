package com.checkout

import com.checkout.validation.validator.CVVComponentDetailsValidator
import org.junit.Assert
import org.junit.Test

internal class CVVComponentValidatorFactoryTest {

    @Test
    fun `when CVVComponentValidatorFactory is created then CVVComponentDetailsValidator is correctly created`() {
        // When
        val cvvComponentValidator = CVVComponentValidatorFactory.create()

        // Then
        Assert.assertEquals(CVVComponentDetailsValidator::class.java, cvvComponentValidator.javaClass)
    }
}
