package com.checkout.sdk.uicommon

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TextInputUseCaseTest {

    @Mock
    private lateinit var strategy: TextInputStrategy

    private val text = "example text"

    private lateinit var textInputUseCase: TextInputUseCase

    @Before
    fun setup() {
        textInputUseCase = TextInputUseCase(text, strategy)
    }

    @Test
    fun `when use case is executed then strategy should have text`() {
        textInputUseCase.execute()

        then(strategy).should().textChanged(text)
    }
}
