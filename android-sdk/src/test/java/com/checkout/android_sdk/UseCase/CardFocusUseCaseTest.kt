package com.checkout.android_sdk.UseCase

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardFocusUseCaseTest {

    @Mock
    lateinit var callbackMock: CardFocusUseCase.Callback

    @Test
    fun `given valid card without focus then card focus result is error`() {
        CardFocusUseCase(false, "1234567812345678", callbackMock).execute()

        then(callbackMock).should()
            .onCardFocusResult(true)
    }

    @Test
    fun `given valid card with focus then card focus result is no error`() {
        CardFocusUseCase(true, "4242424242424242", callbackMock).execute()

        then(callbackMock).should()
            .onCardFocusResult(false)
    }

    @Test
    fun `given invalid card without focus then card focus result is error`() {
        CardFocusUseCase(false, "11", callbackMock).execute()

        then(callbackMock).should()
            .onCardFocusResult(true)
    }

}
