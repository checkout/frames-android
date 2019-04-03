package com.checkout.sdk.uicommon

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TextInputFocusChangedUseCaseTest {

    @Mock
    private lateinit var strategy: TextInputStrategy

    private val text = "any text"
    private val hasFocus = true

    private lateinit var textInputFocusChangedUseCase: TextInputFocusChangedUseCase

    @Before
    fun setup() {
        textInputFocusChangedUseCase = TextInputFocusChangedUseCase(text, hasFocus, strategy)
    }

    @Test
    fun `when use case is executed we tell the strategy to reset`() {
        textInputFocusChangedUseCase.execute()

        then(strategy).should().focusChanged(text, hasFocus)
    }
}
