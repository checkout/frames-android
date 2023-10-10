package com.checkout.frames.utils.extensions

import android.annotation.SuppressLint
import com.checkout.tokenization.model.ExpiryDate
import io.mockk.junit5.MockKExtension
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class StringExtensionsKtTest {

    @ParameterizedTest(
        name = "When transformation of string {0} is requested then expiry date {1} is provided",
    )
    @MethodSource("testArguments")
    fun `Create expiry date data object from string`(
        inputString: String,
        expectedResult: ExpiryDate,
    ) {
        // When
        val result = inputString.toExpiryDate()

        // Then
        assertEquals(expectedResult, result)
    }

    companion object {
        @JvmStatic
        fun testArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("123", ExpiryDate(1, 23)),
            Arguments.of("0123", ExpiryDate(1, 23)),
            Arguments.of("0123", ExpiryDate(1, 23)),
            Arguments.of("0103", ExpiryDate(1, 3)),
            Arguments.of("01033", ExpiryDate(0, 0)),
            Arguments.of("01", ExpiryDate(0, 0)),
        )
    }
}
