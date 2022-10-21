package com.checkout.frames.manager

import com.checkout.base.model.CardScheme
import com.checkout.base.model.Country
import com.checkout.frames.screen.billingformdetails.models.BillingAddress
import com.checkout.frames.screen.manager.PaymentFormStateManager
import com.checkout.tokenization.model.Phone
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class PaymentFormStateManagerTest {

    private lateinit var paymentFormStateManager: PaymentFormStateManager

    @Test
    fun `when custom supported card schemes is provided then supportedCardSchemeList should updated correctly `() {
        // Given
        paymentFormStateManager = PaymentFormStateManager(
            listOf(CardScheme.AMERICAN_EXPRESS, CardScheme.MADA),
            emptyList()
        )
        val expectedSupportedSchemes = listOf(CardScheme.AMERICAN_EXPRESS, CardScheme.MADA)

        // Then
        Assertions.assertEquals(paymentFormStateManager.supportedCardSchemeList, expectedSupportedSchemes)
    }

    @Test
    fun `when custom supported card schemes isn't provided then checkout's all supportedCardSchemes should updated`() {
        // Given
        paymentFormStateManager = PaymentFormStateManager(emptyList(), emptyList())
        val expectedSupportedSchemes = CardScheme.fetchAllSupportedCardSchemes()
        // Then
        Assertions.assertEquals(paymentFormStateManager.supportedCardSchemeList, expectedSupportedSchemes)
    }

    @Test
    fun `when reset of payment state is requested then payment state is returned to a default state`() {
        // Given
        val supportedSchemes = listOf(CardScheme.VISA, CardScheme.DISCOVER)
        paymentFormStateManager = PaymentFormStateManager(supportedSchemes)
        paymentFormStateManager.cvv.value = "123"
        paymentFormStateManager.isCvvValid.value = true
        paymentFormStateManager.cardNumber.value = "23423423423423423"
        paymentFormStateManager.isCardNumberValid.value = true
        paymentFormStateManager.expiryDate.value = "0234"
        paymentFormStateManager.isExpiryDateValid.value = true
        paymentFormStateManager.billingAddress.value = BillingAddress(
            name = "name",
            phone = Phone("+4332452452345234", Country.UNITED_KINGDOM)
        )

        // When
        paymentFormStateManager.resetPaymentState()

        // Then
        Assertions.assertEquals(paymentFormStateManager.cvv.value, "")
        Assertions.assertEquals(paymentFormStateManager.isCvvValid.value, false)
        Assertions.assertEquals(paymentFormStateManager.cardNumber.value, "")
        Assertions.assertEquals(paymentFormStateManager.isCardNumberValid.value, false)
        Assertions.assertEquals(paymentFormStateManager.expiryDate.value, "")
        Assertions.assertEquals(paymentFormStateManager.isExpiryDateValid.value, false)
        Assertions.assertEquals(paymentFormStateManager.billingAddress.value, BillingAddress())
        Assertions.assertEquals(paymentFormStateManager.supportedCardSchemeList, supportedSchemes)
    }
}
