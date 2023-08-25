package com.checkout.frames.utils.extensions

import android.annotation.SuppressLint
import com.checkout.base.model.Country
import com.checkout.frames.R
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentState
import com.checkout.frames.mock.BillingAddressDetailsTestData
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@SuppressLint("NewApi")
internal class BillingAddressDetailsExtensionsTest {

    @Test
    fun `when provideBillingAddressDetails is requested then correct billing address should return`() {
        // Given
        val country = Country.UNITED_KINGDOM
        val expectedBillingAddress = BillingAddress(
            name = "Cardholder name",
            address = Address(
                addressLine1 = "address line one",
                addressLine2 = "address line two",
                city = "city",
                country = country,
                state = "dummy state",
                zip = "post code"
            ),
            phone = Phone("9426979314", country)
        )

        // When
        val result = BillingAddressDetailsTestData.fetchInputComponentStateList().provideBillingAddressDetails(country)

        // Then
        assertEquals(expectedBillingAddress, result)
    }

    @ParameterizedTest(
        name = "When getErrorMessage {0} is requested then error message {1} is provided"
    )
    @MethodSource("testGetErrorMessageArguments")
    fun `when getErrorMessage is requested then correct error message should return`(
        inputComponentState: BillingAddressInputComponentState,
        expectedErrorMessageResourceId: Int,
    ) {

        // When
        val actualErrorMessageResourceId = inputComponentState.getErrorMessage()

        // Then
        assertEquals(actualErrorMessageResourceId, expectedErrorMessageResourceId)
    }

    companion object {

        @JvmStatic
        fun testGetErrorMessageArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(
                BillingAddressDetailsTestData.fetchInputComponentStateList()[0],
                R.string.cko_cardholder_name_error,
                BillingAddressDetailsTestData.fetchInputComponentStateList()[1],
                R.string.cko_billing_form_input_field_address_line_one_error,
                BillingAddressDetailsTestData.fetchInputComponentStateList()[2],
                R.string.cko_billing_form_input_field_address_line_two_error,
                BillingAddressDetailsTestData.fetchInputComponentStateList()[3],
                R.string.cko_billing_form_input_field_city_error,
                BillingAddressDetailsTestData.fetchInputComponentStateList()[4],
                R.string.cko_billing_form_input_field_state_error,
                BillingAddressDetailsTestData.fetchInputComponentStateList()[5],
                R.string.cko_billing_form_input_field_post_code_error,
                BillingAddressDetailsTestData.fetchInputComponentStateList()[6],
                R.string.cko_billing_form_input_field_phone_number_error,
            )
        )
    }
}
