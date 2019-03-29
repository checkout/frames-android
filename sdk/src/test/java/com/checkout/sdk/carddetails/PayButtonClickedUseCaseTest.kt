package com.checkout.sdk.carddetails

import com.checkout.sdk.core.CardDetailsValidator
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class PayButtonClickedUseCaseTest {

    @Mock
    private lateinit var validator: CardDetailsValidator

    private lateinit var payButtonClickedUseCase: PayButtonClickedUseCase

    @Before
    fun onSetup() {
        payButtonClickedUseCase = PayButtonClickedUseCase(validator)
    }

    @Test
    fun `when use case executed then we get the validity from the validator`() {
        payButtonClickedUseCase.execute()

        then(validator).should().getValidity()
    }

}
