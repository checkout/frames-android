package com.checkout.sdk.billingdetails.model

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test


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
