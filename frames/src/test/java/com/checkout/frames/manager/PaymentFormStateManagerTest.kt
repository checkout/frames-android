package com.checkout.frames.manager

import android.os.Build
import androidx.annotation.RequiresApi
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Country
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import com.checkout.frames.screen.manager.PaymentFormStateManager
import com.checkout.tokenization.model.Phone
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class PaymentFormStateManagerTest {

    private lateinit var paymentFormStateManager: PaymentFormStateManager

    @Test
    fun `when custom supported card schemes is provided then supportedCardSchemeList should updated correctly `() {
        // Given
        paymentFormStateManager = PaymentFormStateManager(listOf(CardScheme.AMERICAN_EXPRESS, CardScheme.MADA))
        val expectedSupportedSchemes = listOf(CardScheme.AMERICAN_EXPRESS, CardScheme.MADA)

        // Then
        Assertions.assertEquals(paymentFormStateManager.supportedCardSchemeList, expectedSupportedSchemes)
    }

    @Test
    fun `when custom supported card schemes isn't provided then checkout's all supportedCardSchemes should updated`() {
        // Given
        paymentFormStateManager = PaymentFormStateManager(emptyList())
        val expectedSupportedSchemes = CardScheme.fetchAllSupportedCardSchemes()
        // Then
        Assertions.assertEquals(paymentFormStateManager.supportedCardSchemeList, expectedSupportedSchemes)
    }

    @ParameterizedTest(
        name = "When reset of payment state is requested with: " +
                "isCvvValid = {0} and isBillingAddressValid = {1}; " +
                "Then default cvv isValid state = {0}, address isValid state = {1} and address isEnabled state = {2}"
    )
    @MethodSource("resetArguments")
    fun `when reset of payment state is requested then payment state is returned to a default state`(
        isCvvValid: Boolean,
        isBillingAddressValid: Boolean,
        isBillingAddressEnabled: Boolean,
    ) {
        // Given
        val supportedSchemes = listOf(CardScheme.VISA, CardScheme.DISCOVER)
        paymentFormStateManager = PaymentFormStateManager(supportedSchemes)
        paymentFormStateManager.cvv.value = "123"
        paymentFormStateManager.isCvvValid.value = !isCvvValid
        paymentFormStateManager.cardNumber.value = "23423423423423423"
        paymentFormStateManager.isCardNumberValid.value = true
        paymentFormStateManager.expiryDate.value = "0234"
        paymentFormStateManager.isExpiryDateValid.value = true
        paymentFormStateManager.billingAddress.value = BillingAddress(
            name = "name",
            phone = Phone("+4332452452345234", Country.UNITED_KINGDOM)
        )
        paymentFormStateManager.isBillingAddressValid.value = !isBillingAddressValid
        paymentFormStateManager.isBillingAddressEnabled.value = !isBillingAddressEnabled
        paymentFormStateManager.visitedCountryPicker.value = true

        // When
        paymentFormStateManager.resetPaymentState(isCvvValid, isBillingAddressValid, isBillingAddressEnabled)

        // Then
        Assertions.assertEquals(paymentFormStateManager.cvv.value, "")
        Assertions.assertEquals(paymentFormStateManager.isCvvValid.value, isCvvValid)
        Assertions.assertEquals(paymentFormStateManager.cardNumber.value, "")
        Assertions.assertEquals(paymentFormStateManager.isCardNumberValid.value, false)
        Assertions.assertEquals(paymentFormStateManager.expiryDate.value, "")
        Assertions.assertEquals(paymentFormStateManager.isExpiryDateValid.value, false)
        Assertions.assertEquals(paymentFormStateManager.billingAddress.value, BillingAddress())
        Assertions.assertEquals(paymentFormStateManager.isBillingAddressValid.value, isBillingAddressValid)
        Assertions.assertEquals(paymentFormStateManager.isBillingAddressEnabled.value, isBillingAddressEnabled)
        Assertions.assertEquals(paymentFormStateManager.visitedCountryPicker.value, false)
        Assertions.assertEquals(paymentFormStateManager.supportedCardSchemeList, supportedSchemes)
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.N)
        @JvmStatic
        fun resetArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(false, false, true),
            Arguments.of(true, false, true),
            Arguments.of(false, true, true),
            Arguments.of(true, true, true),
            Arguments.of(false, false, false),
            Arguments.of(true, false, false),
            Arguments.of(false, true, false),
            Arguments.of(true, true, false),
        )
    }
}
