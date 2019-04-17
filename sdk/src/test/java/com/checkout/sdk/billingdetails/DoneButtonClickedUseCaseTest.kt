package com.checkout.sdk.billingdetails

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DoneButtonClickedUseCaseTest {

    @Mock
    private lateinit var billingDetailsValidator: BillingDetailsValidator

    @InjectMocks
    private lateinit var doneButtonClickedUseCase: DoneButtonClickedUseCase

    @Test
    fun `when done button clicked use case then we get validity from billing details validator`() {
        doneButtonClickedUseCase.execute()

        then(billingDetailsValidator).should().getValidity()
    }
}
