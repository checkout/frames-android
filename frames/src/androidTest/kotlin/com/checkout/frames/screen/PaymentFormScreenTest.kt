package com.checkout.frames.screen

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Country
import com.checkout.base.model.Environment
import com.checkout.frames.api.PaymentFlowHandler
import com.checkout.frames.screen.paymentform.PaymentFormScreen
import com.checkout.frames.screen.paymentform.model.BillingFormAddress
import com.checkout.frames.screen.paymentform.model.PaymentFormConfig
import com.checkout.frames.screen.paymentform.model.PrefillData
import com.checkout.frames.style.screen.PaymentFormStyle
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone
import com.checkout.tokenization.model.TokenDetails
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class PaymentFormScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val expectedMockContext: Context = mockk(relaxed = true)

    @Suppress("EmptyFunctionBlock")
    private val paymentFormConfig = PaymentFormConfig(
        context = expectedMockContext,
        environment = Environment.PRODUCTION,
        publicKey = "Test key",
        style = PaymentFormStyle(),
        supportedCardSchemeList = listOf(CardScheme.VISA, CardScheme.MAESTRO),
        paymentFlowHandler = object : PaymentFlowHandler {
            override fun onSubmit() {}
            override fun onSuccess(tokenDetails: TokenDetails) {}
            override fun onFailure(errorMessage: String) {}
            override fun onBackPressed() {}
        },
        prefillData = PrefillData(
            cardHolderName = "Test Name", billingFormAddress = BillingFormAddress(
                name = "Test Billing Address name", address = Address(
                    addressLine1 = "Checkout.com",
                    addressLine2 = "90 Tottenham Court Road",
                    city = "London",
                    state = "London",
                    zip = "W1T 4TJ",
                    country = Country.from(iso3166Alpha2 = "GB")
                ), phone = Phone(
                    number = "4155552671", country = Country.from(iso3166Alpha2 = "GB")
                )
            )
        )
    )

    @Test
    fun paymentFormScreenShouldBeCreatedWithTheCorrectTestNameFilled() {
        composeTestRule.setContent {
            MaterialTheme { PaymentFormScreen(paymentFormConfig) }
        }

        composeTestRule.onNodeWithText("Test Name").assertIsDisplayed()
    }
}
