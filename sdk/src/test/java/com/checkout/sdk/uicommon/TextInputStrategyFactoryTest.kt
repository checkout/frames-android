package com.checkout.sdk.uicommon

import com.checkout.sdk.billingdetails.CustomerNameStrategy
import com.checkout.sdk.cvvinput.CvvStrategy
import junit.framework.Assert.assertEquals
import org.junit.Test


class TextInputStrategyFactoryTest {

    @Test
    fun `when strategy key is cvv then strategy is a CvvStrategy`() {
        val strategy = TextInputStrategyFactory.createStrategy("cvv")
        assertEquals(CvvStrategy::class.java, strategy.javaClass)
    }

    @Test
    fun `when strategy key is customer_name then strategy is a CustomerNameStrategy`() {
        val strategy = TextInputStrategyFactory.createStrategy("customer_name")
        assertEquals(CustomerNameStrategy::class.java, strategy.javaClass)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `when strategy key is unknown then an IllegalArgumentException is thrown`() {
        val strategy = TextInputStrategyFactory.createStrategy("unknown")
    }
}
