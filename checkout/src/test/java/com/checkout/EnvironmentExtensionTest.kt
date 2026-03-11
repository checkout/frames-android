package com.checkout

import com.checkout.base.model.Environment
import com.checkout.base.util.EnvironmentConstants
import com.checkout.base.util.EnvironmentConstants.PRODUCTION_SERVER_URL
import com.checkout.logging.utils.toBaseUrl
import org.junit.Assert.assertEquals
import org.junit.Test

internal class EnvironmentExtensionTest {

    @Test
    fun `when toBaseUrl PRODUCTION with null subDomainPrefix returns production server URL`() {
        val result = Environment.PRODUCTION.toBaseUrl(null)
        assertEquals(EnvironmentConstants.PRODUCTION_SERVER_URL, result)
    }

    @Test
    fun `when toBaseUrl PRODUCTION with no subDomainPrefix argument returns production server URL`() {
        val result = Environment.PRODUCTION.toBaseUrl()
        assertEquals(EnvironmentConstants.PRODUCTION_SERVER_URL, result)
    }

    @Test
    fun `when toBaseUrl PRODUCTION with subDomainPrefix returns custom production URL`() {
        val result = Environment.PRODUCTION.toBaseUrl("custom")
        assertEquals("https://custom.api.checkout.com/tokens", result)
    }

    @Test
    fun `when toBaseUrl PRODUCTION with alphanumeric subDomainPrefix returns custom production URL`() {
        val result = Environment.PRODUCTION.toBaseUrl("mySubdomain123")
        assertEquals("https://mySubdomain123.api.checkout.com/tokens", result)
    }

    @Test
    fun `when toBaseUrl SANDBOX with null subDomainPrefix returns sandbox server URL`() {
        val result = Environment.SANDBOX.toBaseUrl(null)
        assertEquals(EnvironmentConstants.SANDBOX_SERVER_URL, result)
    }

    @Test
    fun `when toBaseUrl SANDBOX with no subDomainPrefix argument returns sandbox server URL`() {
        val result = Environment.SANDBOX.toBaseUrl()
        assertEquals(EnvironmentConstants.SANDBOX_SERVER_URL, result)
    }

    @Test
    fun `when toBaseUrl SANDBOX with subDomainPrefix returns custom sandbox URL`() {
        val result = Environment.SANDBOX.toBaseUrl("custom")
        assertEquals("https://custom.api.sandbox.checkout.com/tokens", result)
    }

    @Test
    fun `when toBaseUrl SANDBOX with alphanumeric subDomainPrefix returns custom sandbox URL`() {
        val result = Environment.SANDBOX.toBaseUrl("test99")
        assertEquals("https://test99.api.sandbox.checkout.com/tokens", result)
    }

    @Test
    fun `when toBaseUrl with empty string subDomainPrefix uses default URL for production`() {
        val result = Environment.PRODUCTION.toBaseUrl("")
        assertEquals("https://api.checkout.com/tokens", result)
    }

    @Test
    fun `toBaseUrl PRODUCTION returns default URL for any non-alphanumeric prefix`() {
        val invalidPrefixList = listOf(
            "invalid_prefix",
            "invalid-prefix",
            "invalid.prefix",
            "invalid@prefix",
            "invalid!prefix",
            "invalid#prefix",
            "invalid\$prefix",
            "invalid%prefix",
            "invalid^prefix",
            "invalid&prefix",
            "invalid*prefix",
            "invalid(prefix",
            "invalid)prefix",
            "invalid prefix",
        )

        invalidPrefixList.forEach { invalidPrefix ->
            val result = Environment.PRODUCTION.toBaseUrl(invalidPrefix)
            assertEquals(
                PRODUCTION_SERVER_URL,
                result,
            )
        }
    }
}
