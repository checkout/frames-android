package com.checkout.frames.manager

import com.checkout.base.model.CardScheme
import com.checkout.frames.screen.manager.PaymentFormStateManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

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
        paymentFormStateManager = PaymentFormStateManager(null)
        val expectedSupportedSchemes = listOf(
            CardScheme.VISA,
            CardScheme.MASTERCARD,
            CardScheme.MAESTRO,
            CardScheme.AMERICAN_EXPRESS,
            CardScheme.DISCOVER,
            CardScheme.DINERS_CLUB,
            CardScheme.JCB,
            CardScheme.MADA
        )

        // Then
        Assertions.assertEquals(paymentFormStateManager.supportedCardSchemeList, expectedSupportedSchemes)
    }
}
