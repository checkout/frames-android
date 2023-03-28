package com.checkout.frames.screen.billingaddressdetails.models

import com.checkout.base.model.Country
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress.Companion.DEFAULT_BILLING_ADDRESS
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress.Companion.isEdited
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


internal class BillingAddressTest {
    @Test
    fun `A default BillingAddress should not be edited`() = assertFalse(BillingAddress().isEdited())

    @Test
    fun `If BillingAddress name is edited then edited() should return true`() =
        assertTrue(BillingAddress(name = "NAME").isEdited())

    @Test
    fun `If BillingAddress phone is edited then edited() should return true`() =
        assertTrue(BillingAddress(phone = Phone(number = "1234", Country.AFGHANISTAN)).isEdited())

    @Test
    fun `If BillingAddress addressLine1 is edited then edited() should return true`() =
        assertTrue(BillingAddress(address = buildBillingAddress(addressLine1 = "addressLine1")).isEdited())

    @Test
    fun `If BillingAddress addressLine2 is edited then edited() should return true`() =
        assertTrue(BillingAddress(address = buildBillingAddress(addressLine2 = "addressLine2")).isEdited())

    @Test
    fun `If BillingAddress city is edited then edited() should return true`() =
        assertTrue(BillingAddress(address = buildBillingAddress(city = "city")).isEdited())

    @Test
    fun `If BillingAddress state is edited then edited() should return true`() =
        assertTrue(BillingAddress(address = buildBillingAddress(state = "state")).isEdited())

    @Test
    fun `If BillingAddress zip is edited then edited() should return true`() =
        assertTrue(BillingAddress(address = buildBillingAddress(zip = "zip")).isEdited())

    @Test
    fun `If BillingAddress country is edited then edited() should return true`() =
        assertTrue(BillingAddress(address = buildBillingAddress(country = Country.ANGOLA)).isEdited())

    private companion object {
        private fun buildBillingAddress(
            addressLine1: String = "",
            addressLine2: String = "",
            city: String = "",
            state: String = "",
            zip: String = "",
            country: Country = DEFAULT_BILLING_ADDRESS.address!!.country,
        ) = Address(addressLine1, addressLine2, city, state, zip, country)
    }
}
