package com.checkout.frames.mapper

import android.annotation.SuppressLint
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.Country
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import com.checkout.frames.screen.paymentform.model.BillingFormAddress
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone
import io.mockk.junit5.MockKExtension
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class BillingFormAddressToBillingAddressMapperTest {
    private lateinit var billingFormAddressToBillingAddressMapper: Mapper<BillingFormAddress?, BillingAddress>

    @BeforeEach
    fun setUp() {
        billingFormAddressToBillingAddressMapper = BillingFormAddressToBillingAddressMapper()
    }

    @ParameterizedTest(
        name = "When summary of billing address {0} is requested then addressPreview {1} is provided"
    )
    @MethodSource("testBillingAddressSummaryArguments")
    fun `mapping of BillingFormAddress to BillingAddress data should return correct data`(
        billingFormAddress: BillingFormAddress,
        expectedBillingAddress: BillingAddress,
    ) {
        // When
        val actualBillingAddress = billingFormAddressToBillingAddressMapper.map(billingFormAddress)

        // Then
        assertEquals(expectedBillingAddress, actualBillingAddress)
    }

    @Test
    fun `test lazy initialization of BillingFormAddressToBillingAddressMapper`() {
        // When
        val mapperInstance = BillingFormAddressToBillingAddressMapper.INSTANCE

        // Then
        assertNotNull(mapperInstance)
    }

    companion object {
        @JvmStatic
        fun testBillingAddressSummaryArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(
                BillingFormAddress(
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
                BillingFormAddress(
                    name = "Test name", phone = Phone("123", Country.UNITED_KINGDOM)
                ),
                BillingAddress(
                    name = "Test name", phone = Phone("123", Country.UNITED_KINGDOM)
                ),
                BillingFormAddress(name = "Test name"), BillingAddress(name = "Test name"),
                BillingFormAddress(
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
                BillingFormAddress(), BillingAddress(),
                BillingFormAddress(name = "Test name", address = null),
                BillingAddress(name = "Test name", address = BillingAddress().address)
            )
        )
    }
}
