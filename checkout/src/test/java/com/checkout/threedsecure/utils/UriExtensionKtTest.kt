package com.checkout.threedsecure.utils

import android.net.Uri
import org.amshove.kluent.internal.assertEquals
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
internal class UriExtensionKtTest(
    private val expectedUrl: String,
    private val targetUrl: String,
    private val result: Boolean,
) {

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(
            name = "When expected Url {0} compared with target Url {1} then {2} is returned",
        )
        fun data() = listOf(
            arrayOf("www.example.com/Success", "https://www.example.com/Success", false),
            arrayOf("https://www.example.com/Success", "www.example.com/Success", false),
            arrayOf("https://www.example.com/Success", "https://www.example.com/Success", true),
            arrayOf("test://www.example.com/test?q=Success#hash", "https://www.example.com/test?q=Success#hash", false),
            arrayOf("https://www.test.com/test?q=Success#hash", "https://www.example.com/test?q=Success#hash", false),
            arrayOf("https://www.example.com?q=Success#hash", "https://www.example.com/test?q=Success#hash", false),
            arrayOf("https://www.example.com/test?#hash", "https://www.example.com/test?q=Success#hash", false),
            arrayOf("https://www.example.com/test?q=Success", "https://www.example.com/test?q=Success#hash", false),
            arrayOf("https://www.example.com/test?q=Success#hash", "https://www.example.com/test?q=Success#hash", true),
            arrayOf(
                "https://www.example.com/test?q=Success",
                "https://www.example.com/test?q=Success&cko-session-id=wrongValue",
                true,
            ),
            arrayOf(
                "https://www.example.com/test?q=Success&hello=world",
                "https://www.example.com/test?hello=world&q=Success",
                true,
            ),
        )
    }

    @org.junit.Test
    fun test() {
        // When
        val actualResult = Uri.parse(expectedUrl).matches(Uri.parse(targetUrl))

        // Then
        assertEquals(result, actualResult)
    }
}
