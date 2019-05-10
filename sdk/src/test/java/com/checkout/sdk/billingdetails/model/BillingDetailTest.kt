package com.checkout.sdk.billingdetails.model

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class BillingDetailTest {

    @Test
    fun `test two character is not valid`() {
        val billingDetail = BillingDetail("12")
        assertFalse(billingDetail.isValid())
    }

    @Test
    fun `test three characters is valid`() {
        val billingDetail = BillingDetail("123")
        assertTrue(billingDetail.isValid())
    }
}
