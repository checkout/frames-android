package com.checkout.sdk.uicommon

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class TextInputResetUseCaseTest {

    @Mock
    private lateinit var strategy: TextInputStrategy

    private lateinit var textInputResetUseCase: TextInputResetUseCase

    @BeforeEach
    fun setup() {
        textInputResetUseCase = TextInputResetUseCase(strategy)
    }

    @Test
    fun `when use case is executed we tell the strategy to reset`() {
        textInputResetUseCase.execute()

        then(strategy).should().reset()
    }
}
