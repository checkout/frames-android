package com.checkout.frames.utils.extensions

import android.annotation.SuppressLint
import com.checkout.base.model.Country
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone
import io.mockk.junit5.MockKExtension
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class BillingAddressExtensionsTest {

    @ParameterizedTest(
        name = "When summary of billing address {0} is requested then addressPreview {1} is provided"
    )
    @MethodSource("testBillingAddressSummaryArguments")
    fun `Requested billing address summary should provide correct address preview text`(
        billingAddress: BillingAddress,
        expectedAddressPreview: String,
    ) {
        // When
        val result = billingAddress.summary()

        // Then
        assertEquals(expectedAddressPreview, result)
    }

    @ParameterizedTest(
        name = "When summary of billing address {0} is requested then addressPreview {1} is provided"
    )
    @MethodSource("testIsBillingAddressValidArguments")
    fun `Requested to verify valid billing address should provide correct validation`(
        billingAddress: BillingAddress,
        expectedIsBillingAddressValid: Boolean,
    ) {
        // When
        val result = billingAddress.isValid()

        // Then
        assertEquals(expectedIsBillingAddressValid, result)
    }

    companion object {
        @JvmStatic
        fun testBillingAddressSummaryArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(
                BillingAddress(
                    address = Address(
                        addressLine1 = "LINE 1",
                        addressLine2 = "LINE 2",
                        city = "city",
                        state = "state",
                        zip = "zipcode",
                        country = Country.UNITED_KINGDOM
                    ),
                    phone = Phone("123", Country.UNITED_KINGDOM)
                ),
                "LINE 1\nLINE 2\ncity\nstate\nzipcode\nUnited Kingdom\n+44 123",
                BillingAddress(
                    name = "Test name",
                    phone = Phone("123", Country.UNITED_KINGDOM)
                ),
                "Test name\n+44 123",
                BillingAddress(
                    name = "Test name"
                ),
                "Test name",
                BillingAddress(
                    name = "Test name",
                    address = Address(
                        addressLine1 = "",
                        addressLine2 = "",
                        city = "",
                        state = "state",
                        zip = "zipcode",
                        country = Country.UNITED_KINGDOM
                    )
                ),
                "Test name\nstate\nzipcode\nUnited Kingdom",
                BillingAddress(), ""
            )
        )

        @JvmStatic
        fun testIsBillingAddressValidArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(
                BillingAddress(
                    address = Address(
                        addressLine1 = "LINE 1",
                        addressLine2 = "LINE 2",
                        city = "city",
                        state = "state",
                        zip = "zipcode",
                        country = Country.UNITED_KINGDOM
                    ),
                    phone = Phone("123", Country.UNITED_KINGDOM)
                ),
                true,
                BillingAddress(
                    name = "Test name", phone = Phone("123", Country.UNITED_KINGDOM)
                ),
                false,
                BillingAddress(
                    name = "Test name"
                ),
                false,
                BillingAddress(
                    name = "Test name",
                    address = Address(
                        addressLine1 = "",
                        addressLine2 = "",
                        city = "",
                        state = "state",
                        zip = "zipcode",
                        country = Country.INVALID_COUNTRY
                    )
                ),
                false,
                BillingAddress(
                    name = "Test name",
                    address = Address(
                        addressLine1 = "",
                        addressLine2 = "",
                        city = "",
                        state = "state",
                        zip = "zipcode",
                        country = Country.UNITED_KINGDOM
                    )
                ),
                false
            )
        )
    }
}
