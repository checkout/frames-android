package com.checkout.sdk.billingdetails

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.billingdetails.model.BillingDetails
import com.checkout.sdk.billingdetails.model.CityDetail
import com.checkout.sdk.models.PhoneModel
import com.checkout.sdk.store.InMemoryStore
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class BillingDetailsValidatorTest {

    @Mock
    private lateinit var store: InMemoryStore

    @InjectMocks
    private lateinit var billingDetailsValidator: BillingDetailsValidator

    @Test
    fun `given all billing details are valid then getValidity returns true`() {
        val phoneModel = PhoneModel("+21", "34924")
        val billingDetails = BillingDetails(
            BillingDetail("addressOne"),
            BillingDetail("addressTwo"),
            CityDetail("city"),
            BillingDetail("state"),
            BillingDetail("postcode"),
            "country",
            phoneModel
        )
        given(store.billingDetails).willReturn(billingDetails)
        given(store.customerName).willReturn(BillingDetail("MC Hammer"))

        assertTrue(billingDetailsValidator.isValid())
    }

    @Test
    fun `given address two invalid then getValidity returns false`() {
        val phoneModel = PhoneModel("+21", "34924")
        val billingDetails = BillingDetails(
            BillingDetail("addressOne"),
            BillingDetail("ad"),
            CityDetail("city"),
            BillingDetail("state"),
            BillingDetail("postcode"),
            "country",
            phoneModel
        )
        given(store.billingDetails).willReturn(billingDetails)
        given(store.customerName).willReturn(BillingDetail("MC Hammer"))

        assertFalse(billingDetailsValidator.isValid())
    }

}
