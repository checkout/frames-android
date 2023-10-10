package com.checkout.validation.validator

import android.annotation.SuppressLint
import com.checkout.validation.validator.contract.Checker
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@SuppressLint("NewApi")
internal class LuhnCheckerTest {

    private lateinit var luhnChecker: Checker<String>

    @BeforeEach
    internal fun setUp() {
        luhnChecker = LuhnChecker()
    }

    @ParameterizedTest(name = "Expect luhn validation result = {1} when given card number = {0}")
    @MethodSource("testArguments")
    fun `given month or year is not valid returns failure`(
        cardNumber: String,
        expectedResult: Boolean,
    ) {
        // When
        val result = luhnChecker.check(cardNumber)

        // Then
        assertEquals(expectedResult, result)
    }

    companion object {
        @JvmStatic
        fun testArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("4929939187355598", true),
            Arguments.of("4485383550284604", true),
            Arguments.of("4532307841419094", true),
            Arguments.of("4716014929481859", true),
            Arguments.of("4539677496449015", true),
            Arguments.of("4129939187355598", false),
            Arguments.of("4485383550184604", false),
            Arguments.of("4532307741419094", false),
            Arguments.of("4716014929401859", false),
            Arguments.of("4539672496449015", false),
            Arguments.of("5454422955385717", true),
            Arguments.of("5582087594680466", true),
            Arguments.of("5485727655082288", true),
            Arguments.of("5523335560550243", true),
            Arguments.of("5128888281063960", true),
            Arguments.of("5454452295585717", false),
            Arguments.of("5582087594683466", false),
            Arguments.of("5487727655082288", false),
            Arguments.of("5523335500550243", false),
            Arguments.of("5128888221063960", false),
            Arguments.of("6011574229193527", true),
            Arguments.of("6011908281701522", true),
            Arguments.of("6011638416335074", true),
            Arguments.of("6011454315529985", true),
            Arguments.of("6011123583544386", true),
            Arguments.of("6011574229193127", false),
            Arguments.of("6031908281701522", false),
            Arguments.of("6011638416335054", false),
            Arguments.of("6011454316529985", false),
            Arguments.of("6011123581544386", false),
            Arguments.of("348570250878868", true),
            Arguments.of("341869994762900", true),
            Arguments.of("371040610543651", true),
            Arguments.of("341507151650399", true),
            Arguments.of("371673921387168", true),
            Arguments.of("348570250872868", false),
            Arguments.of("341669994762900", false),
            Arguments.of("371040610573651", false),
            Arguments.of("341557151650399", false),
            Arguments.of("371673901387168", false),
            Arguments.of("6501111111111117", false),
            Arguments.of("4000056655665", false),
            Arguments.of("a492993918735559", false),
        )
    }
}
