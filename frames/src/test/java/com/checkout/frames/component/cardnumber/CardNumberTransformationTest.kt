package com.checkout.frames.component.cardnumber

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.VisualTransformation
import com.checkout.base.model.CardScheme
import io.mockk.junit5.MockKExtension
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class CardNumberTransformationTest {

    private val cardSchemeState: MutableState<CardScheme> = mutableStateOf(CardScheme.UNKNOWN)
    private lateinit var cardNumberTransformation: VisualTransformation

    @BeforeEach
    fun setUp() {
        setUpVisualTransformation()
    }

    @ParameterizedTest(
        name = "When visual transformation receives {0} then {1} is received"
    )
    @MethodSource("cardNumberTransformationArguments")
    fun `when visual transformation receives a string then correctly formatted string is provided`(
        separator: Char,
        cardScheme: CardScheme,
        enteredNumber: String,
        transformedNumber: String
    ) {
        // Given
        setUpVisualTransformation(separator)
        cardSchemeState.value = cardScheme

        // When
        val result = cardNumberTransformation.filter(AnnotatedString(enteredNumber)).text.text

        // Then
        assertEquals(transformedNumber, result)
    }

    private fun setUpVisualTransformation(separator: Char = ' ') {
        cardNumberTransformation = CardNumberTransformation(separator, cardSchemeState)
    }

    companion object {
        @JvmStatic
        fun cardNumberTransformationArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(' ', CardScheme.UNKNOWN, "4242424242421111111", "4242 4242 4242 1111 111"),
            Arguments.of('-', CardScheme.UNKNOWN, "424242424242", "4242-4242-4242-"),
            Arguments.of(' ', CardScheme.AMERICAN_EXPRESS, "111111111111111", "1111 111111 11111"),
            Arguments.of(' ', CardScheme.AMERICAN_EXPRESS, "11111111111", "1111 111111 1"),
            Arguments.of(' ', CardScheme.DINERS_CLUB, "4242424242421111111", "4242 4242 4242 1111 111"),
            Arguments.of(' ', CardScheme.DINERS_CLUB, "4242424242", "4242 4242 42"),
            Arguments.of(' ', CardScheme.DISCOVER, "4242424242421111111", "4242 4242 4242 1111 111"),
            Arguments.of(' ', CardScheme.DISCOVER, "424242424211", "4242 4242 4211 "),
            Arguments.of(' ', CardScheme.JCB, "4242424242421111111", "4242 4242 4242 1111 111"),
            Arguments.of(' ', CardScheme.JCB, "42424242", "4242 4242 "),
            Arguments.of(' ', CardScheme.MADA, "1234123412341234", "1234 1234 1234 1234"),
            Arguments.of(' ', CardScheme.MADA, "12341234123", "1234 1234 123"),
            Arguments.of(' ', CardScheme.MAESTRO, "4242424242421111111", "4242 4242 4242 1111 111"),
            Arguments.of(' ', CardScheme.MAESTRO, "42424242123", "4242 4242 123"),
            Arguments.of(' ', CardScheme.MASTERCARD, "1234123412341111", "1234 1234 1234 1111"),
            Arguments.of(' ', CardScheme.MASTERCARD, "12341234123", "1234 1234 123"),
            Arguments.of(' ', CardScheme.VISA, "1234123412341111", "1234 1234 1234 1111"),
            Arguments.of(' ', CardScheme.VISA, "12341234123", "1234 1234 123")
        )
    }
}
