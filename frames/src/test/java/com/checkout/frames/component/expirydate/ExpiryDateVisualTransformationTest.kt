package com.checkout.frames.component.expirydate

import android.annotation.SuppressLint
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.google.common.truth.Truth.assertThat
import io.mockk.junit5.MockKExtension
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class ExpiryDateVisualTransformationTest {

    private lateinit var expiryDateVisualTransformation: VisualTransformation

    @BeforeEach
    fun setUp() {
        setUpVisualTransformation()
    }

    private fun setUpVisualTransformation() {
        expiryDateVisualTransformation = ExpiryDateVisualTransformation()
    }

    @ParameterizedTest(
        name = "When visual transformation receives for expiry date {0} then {1} is received",
    )
    @MethodSource("expiryDateVisualTransformationArguments")
    fun `when visual transformation receives a string then correctly formatted string is provided`(
        enteredExpiryDate: String,
        transformedExpiryDate: String,
    ) {
        // When
        val result = expiryDateVisualTransformation.filter(AnnotatedString(enteredExpiryDate)).text.text

        // Then
        assertEquals(transformedExpiryDate, result)
    }

    @Test
    fun `verify offsetMapping for 123`() {
        val result = expiryDateVisualTransformation.filter(AnnotatedString("123"))
        assertCorrectMapping(original = "123", result)
    }

    @Test
    fun `verify offsetMapping for 143`() {
        val result = expiryDateVisualTransformation.filter(AnnotatedString("143"))
        assertCorrectMapping(original = "143", result)
    }

    @Test
    fun `verify offsetMapping for 093`() {
        val result = expiryDateVisualTransformation.filter(AnnotatedString("093"))
        assertCorrectMapping(original = "093", result)
    }

    @Test
    fun `verify offsetMapping for 53`() {
        val result = expiryDateVisualTransformation.filter(AnnotatedString("53"))
        assertCorrectMapping(original = "53", result)
    }

    companion object {
        @JvmStatic
        fun expiryDateVisualTransformationArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("1234", "12 / 34"),
            Arguments.of("1145", "11 / 45"),
            Arguments.of("234", "02 / 34"),
            Arguments.of("345", "03 / 45"),
            Arguments.of("450", "04 / 50"),
            Arguments.of("560", "05 / 60"),
            Arguments.of("670", "06 / 70"),
            Arguments.of("999", "09 / 99"),
            Arguments.of("2", "02 / "),
            Arguments.of("3", "03 / "),
            Arguments.of("44", "04 / 4"),
            Arguments.of("555", "05 / 55"),
            Arguments.of("12", "12 / "),
            Arguments.of("567", "05 / 67"),
            Arguments.of("666", "06 / 66"),
            Arguments.of("897", "08 / 97"),
            Arguments.of("9", "09 / "),
            Arguments.of("856", "08 / 56"),
        )
    }

    private fun assertCorrectMapping(
        original: String,
        result: TransformedText,
    ) {
        val transformed = result.text.text

        for (offset in 0..original.length) {
            val transformedOffset = result.offsetMapping.originalToTransformed(offset)
            assertThat(transformedOffset).isIn(0..transformed.length)
        }

        for (offset in 0..result.text.text.length) {
            val originalOffset = result.offsetMapping.transformedToOriginal(offset)
            assertThat(originalOffset).isIn(0..original.length)
        }
    }
}
