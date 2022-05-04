package com.checkout.android_sdk

import android.content.Context
import androidx.test.core.app.ApplicationProvider
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


    @Before
    internal fun setUp() {
        mockContext = ApplicationProvider.getApplicationContext()
        paymentForm = PaymentForm(mockContext)
        mockFramesLogger = mock(FramesLogger::class.java)
    }

    @Test
    fun `test success logging of PaymentFormPresentedEvent`() {
        paymentForm.setEnvironment(Environment.SANDBOX, mockFramesLogger)

        verify(mockFramesLogger,
            times(1)).sendPaymentFormPresentedEvent(mockContext)
    }
}