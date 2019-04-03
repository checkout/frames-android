package com.checkout.sdk.uicommon

import com.checkout.sdk.uicommon.TextInputResetUseCase
import com.checkout.sdk.uicommon.TextInputStrategy
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TextInputResetUseCaseTest {

    @Mock
    private lateinit var strategy: TextInputStrategy

    private lateinit var textInputResetUseCase: TextInputResetUseCase

    @Before
    fun setup() {
        textInputResetUseCase = TextInputResetUseCase(strategy)
    }

    @Test
    fun `when use case is executed we tell the strategy to reset`() {
        textInputResetUseCase.execute()

        then(strategy).should().reset()
    }
}
