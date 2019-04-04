package com.checkout.sdk.uicommon

import com.checkout.sdk.billingdetails.AddressOneStrategy
import com.checkout.sdk.billingdetails.AddressTwoStrategy
import com.checkout.sdk.billingdetails.CustomerNameStrategy
import com.checkout.sdk.cvvinput.CvvStrategy
import junit.framework.Assert.assertEquals
import org.junit.Test


class TextInputStrategyFactoryTest {

    @Test
    fun `when strategy key is cvv then strategy is CvvStrategy`() {
        val strategy = TextInputStrategyFactory.createStrategy("cvv")
        assertEquals(CvvStrategy::class.java, strategy.javaClass)
    }

    @Test
    fun `when strategy key is customer_name then strategy is CustomerNameStrategy`() {
        val strategy = TextInputStrategyFactory.createStrategy("customer_name")
        assertEquals(CustomerNameStrategy::class.java, strategy.javaClass)
    }

    @Test
    fun `when strategy key is address_one then strategy is AddressOneStrategy`() {
        val strategy = TextInputStrategyFactory.createStrategy("address_one")
        assertEquals(AddressOneStrategy::class.java, strategy.javaClass)
    }

    @Test
    fun `when strategy key is address_two then strategy is AddressTwoStrategy`() {
        val strategy = TextInputStrategyFactory.createStrategy("address_two")
        assertEquals(AddressTwoStrategy::class.java, strategy.javaClass)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `when strategy key is unknown then an IllegalArgumentException is thrown`() {
        TextInputStrategyFactory.createStrategy("unknown")
    }
}
