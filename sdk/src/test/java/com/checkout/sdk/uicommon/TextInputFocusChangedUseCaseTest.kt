package com.checkout.sdk.uicommon

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class TextInputFocusChangedUseCaseTest {

    @Mock
    private lateinit var strategy: TextInputStrategy

    private val hasFocus = true

    private lateinit var textInputFocusChangedUseCase: TextInputFocusChangedUseCase

    @BeforeEach
    fun setup() {
        textInputFocusChangedUseCase = TextInputFocusChangedUseCase(hasFocus, strategy)
    }

    @Test
    fun `when use case is executed we tell the strategy to reset`() {
        textInputFocusChangedUseCase.execute()

        then(strategy).should().focusChanged(hasFocus)
    }
}
