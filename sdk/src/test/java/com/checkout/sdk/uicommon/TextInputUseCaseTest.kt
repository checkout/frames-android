package com.checkout.sdk.uicommon

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class TextInputUseCaseTest {

    @Mock
    private lateinit var strategy: TextInputStrategy

    private val text = "example text"

    private lateinit var textInputUseCase: TextInputUseCase

    @BeforeEach
    fun setup() {
        textInputUseCase = TextInputUseCase(text, strategy)
    }

    @Test
    fun `when use case is executed then strategy should have text`() {
        textInputUseCase.execute()

        then(strategy).should().textChanged(text)
    }
}
