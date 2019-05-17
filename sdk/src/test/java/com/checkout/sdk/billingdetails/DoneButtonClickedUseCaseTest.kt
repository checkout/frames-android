package com.checkout.sdk.billingdetails

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
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
