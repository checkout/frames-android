package com.checkout.sdk.carddetails

import com.checkout.sdk.core.CardDetailsValidator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class PayButtonClickedUseCaseTest {

    @Mock
    private lateinit var validator: CardDetailsValidator

    private lateinit var payButtonClickedUseCase: PayButtonClickedUseCase

    @BeforeEach
    fun onSetup() {
        payButtonClickedUseCase = PayButtonClickedUseCase(validator)
    }

    @Test
    fun `when use case executed then we get the validity from the validator`() {
        payButtonClickedUseCase.execute()

        then(validator).should().getValidity()
    }

}
