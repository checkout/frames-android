package com.checkout.frames.screen.paymentform

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.checkout.base.model.Environment
import com.checkout.frames.mock.PaymentFormConfigTestData
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class PaymentFormViewModelTest {

    @RelaxedMockK
    private lateinit var viewModelStore: ViewModelStore

    @Test
    fun `creating instance of PaymentFormViewModel should update the injector of view model`() {
        // Given
        val factory = PaymentFormViewModel.Factory(
            publicKey = PaymentFormConfigTestData.publicKey,
            context = mockk(relaxed = true),
            environment = Environment.PRODUCTION,
            paymentFlowHandler = PaymentFormConfigTestData.paymentFlowHandler,
            supportedCardSchemes = PaymentFormConfigTestData.supportedCardSchemes,
            prefillData = PaymentFormConfigTestData.prefillData,
        )

        // When
        val viewModelProvider = ViewModelProvider(viewModelStore, factory)
        val viewModel = viewModelProvider[PaymentFormViewModel::class.java]

        // Verify that the factory's injector is updating the viewmodel's injector
        assertEquals(factory.injector, viewModel.injector)
    }
}
