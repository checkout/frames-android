package com.checkout.android_sdk

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.checkout.android_sdk.Utils.CheckoutTheme
import com.checkout.android_sdk.Utils.Environment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class PaymentFormTest {

    private lateinit var mockFramesLogger: FramesLogger

    private lateinit var paymentForm: PaymentForm

    private lateinit var mockContext: Context

    private lateinit var checkoutTheme: CheckoutTheme


    @Before
    internal fun setUp() {
        mockContext = ApplicationProvider.getApplicationContext()
        paymentForm = PaymentForm(mockContext)
        mockFramesLogger = mock(FramesLogger::class.java)
        checkoutTheme = CheckoutTheme(mockContext)
    }

    @Test
    fun `test success logging of PaymentFormPresentedEvent`() {
        paymentForm.setEnvironment(Environment.SANDBOX, mockFramesLogger, checkoutTheme)

        verify(mockFramesLogger,
            times(1)).sendPaymentFormPresentedEvent(checkoutTheme)
    }
}