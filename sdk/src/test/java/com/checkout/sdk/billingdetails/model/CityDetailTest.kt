package com.checkout.sdk.billingdetails.model

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class CityDetailTest {

    @Test
    fun `test one character is not valid`() {
        val billingDetail = CityDetail("1")
        assertFalse(billingDetail.isValid())
    }

    @Test
    fun `test two characters is valid`() {
        val billingDetail = CityDetail("12")
        assertTrue(billingDetail.isValid())
    }
}
