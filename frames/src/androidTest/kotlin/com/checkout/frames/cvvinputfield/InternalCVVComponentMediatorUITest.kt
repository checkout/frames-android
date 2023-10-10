package com.checkout.frames.cvvinputfield

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.checkout.CheckoutApiServiceFactory
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Environment
import com.checkout.frames.cvvinputfield.api.InternalCVVComponentMediator
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig
import com.checkout.frames.cvvinputfield.usecase.CVVTokenizationUseCase
import com.checkout.frames.style.component.base.InputFieldStyle
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue

internal class InternalCVVComponentMediatorUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var cvvComponentConfig: CVVComponentConfig
    private lateinit var cvvComponentMediator: InternalCVVComponentMediator
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        cvvComponentConfig = CVVComponentConfig(CardScheme.VISA, {}, InputFieldStyle())
        cvvComponentMediator = InternalCVVComponentMediator(
            cvvComponentConfig = cvvComponentConfig,
            cvvTokenizationUseCase = CVVTokenizationUseCase(
                CheckoutApiServiceFactory.create(
                    publicKey = "", environment = Environment.SANDBOX, context = context
                )
            ),
        )
    }

    @Test
    fun isCVVComponentCalledValueShouldUpdateWhenCVVComponentInvoked() {
        // Given
        cvvComponentMediator.setIsCVVComponentCalled(false)

        // When
        composeTestRule.setContent {
            cvvComponentMediator.CVVComponent()
        }

        // Then
        assertTrue(cvvComponentMediator.getIsCVVComponentCalled().value)
    }

    @Test
    fun isCVVComponentCalledValueShouldNotUpdateWhenCVVComponentAlreadyInvoked() {
        // Given
        cvvComponentMediator.setIsCVVComponentCalled(true)

        // When
        composeTestRule.setContent {
            cvvComponentMediator.CVVComponent()
        }

        // Then
        assertTrue(cvvComponentMediator.getIsCVVComponentCalled().value)
    }
}
