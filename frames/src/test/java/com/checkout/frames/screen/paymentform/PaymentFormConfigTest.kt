package com.checkout.frames.screen.paymentform

import android.content.Context
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Country
import com.checkout.base.model.Environment
import com.checkout.frames.mock.PaymentFormConfigTestData
import com.checkout.frames.screen.paymentform.model.BillingFormAddress
import com.checkout.frames.screen.paymentform.model.PaymentFormConfig
import com.checkout.frames.screen.paymentform.model.PrefillData
import com.checkout.frames.style.screen.PaymentFormStyle
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class PaymentFormConfigTest {

    @Suppress("LongMethod")
    @Test
    fun `when PaymentFormConfig is requested then data should be updated correctly`() {
        // Given
        val expectedMockContext: Context = mockk(relaxed = true)

        // When
        val paymentFormConfig = PaymentFormConfig(
            context = expectedMockContext,
            environment = Environment.PRODUCTION,
            publicKey = "Test key",
            style = PaymentFormStyle(),
            supportedCardSchemeList = listOf(CardScheme.VISA, CardScheme.MAESTRO),
            paymentFlowHandler = PaymentFormConfigTestData.paymentFlowHandler,
            prefillData = PrefillData(
                cardHolderName = "Test Name",
                billingFormAddress = BillingFormAddress(
                    name = "Test Billing Address name",
                    address = Address(
                        addressLine1 = "Checkout.com",
                        addressLine2 = "90 Tottenham Court Road",
                        city = "London",
                        state = "London",
                        zip = "W1T 4TJ",
                        country = Country.from(iso3166Alpha2 = "GB")
                    ),
                    phone = Phone(
                        number = "4155552671", country = Country.from(iso3166Alpha2 = "GB")
                    )
                )
            )
        )

        // Then
        with(paymentFormConfig) {
            assertEquals(Environment.PRODUCTION, environment)
            assertEquals(PaymentFormConfigTestData.style, style)
            assertEquals(PaymentFormConfigTestData.publicKey, publicKey)
            assertEquals(expectedMockContext, context)
            assertEquals(PaymentFormConfigTestData.supportedCardSchemes, supportedCardSchemeList)

            prefillData?.billingFormAddress.let { billingFormAddress ->
                assertEquals(PaymentFormConfigTestData.prefillData.cardHolderName, prefillData?.cardHolderName)
                assertEquals(PaymentFormConfigTestData.prefillData.billingFormAddress?.name, billingFormAddress?.name)
                assertEquals(
                    PaymentFormConfigTestData.prefillData.billingFormAddress?.phone?.country,
                    billingFormAddress?.phone?.country
                )
                assertEquals(
                    PaymentFormConfigTestData.prefillData.billingFormAddress?.phone?.number,
                    billingFormAddress?.phone?.number
                )

                val actualAddress = billingFormAddress?.address
                assertEquals(
                    PaymentFormConfigTestData.prefillData.billingFormAddress?.address?.addressLine1,
                    actualAddress?.addressLine1
                )
                assertEquals(
                    PaymentFormConfigTestData.prefillData.billingFormAddress?.address?.addressLine2,
                    actualAddress?.addressLine2
                )
                assertEquals(
                    PaymentFormConfigTestData.prefillData.billingFormAddress?.address?.country?.name,
                    actualAddress?.country?.name
                )
                assertEquals(
                    PaymentFormConfigTestData.prefillData.billingFormAddress?.address?.country?.iso3166Alpha2,
                    actualAddress?.country?.iso3166Alpha2
                )
                assertEquals(PaymentFormConfigTestData.prefillData.billingFormAddress?.address?.zip, actualAddress?.zip)
                assertEquals(
                    PaymentFormConfigTestData.prefillData.billingFormAddress?.address?.city, actualAddress?.city
                )
                assertEquals(
                    PaymentFormConfigTestData.prefillData.billingFormAddress?.address?.state, actualAddress?.state
                )
            }
        }
    }
}
