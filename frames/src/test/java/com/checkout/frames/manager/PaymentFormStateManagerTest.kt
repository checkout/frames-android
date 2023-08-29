package com.checkout.frames.manager

import android.os.Build
import androidx.annotation.RequiresApi
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.CardScheme
import com.checkout.base.model.Country
import com.checkout.frames.mapper.BillingFormAddressToBillingAddressMapper
import com.checkout.frames.mock.PaymentFormConfigTestData
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import com.checkout.frames.screen.manager.PaymentFormStateManager
import com.checkout.frames.screen.paymentform.model.BillingFormAddress
import com.checkout.frames.screen.paymentform.model.PrefillData
import com.checkout.tokenization.model.Phone
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@ExtendWith(MockKExtension::class)
internal class PaymentFormStateManagerTest {

    private lateinit var paymentFormStateManager: PaymentFormStateManager

    @SpyK
    private lateinit var spyBillingFormAddressToBillingAddressMapper: Mapper<BillingFormAddress?, BillingAddress>

    init {
        initMappers()
    }

    @Test
    fun `when custom supported card schemes is provided then supportedCardSchemeList should updated correctly`() {
        // Given
        paymentFormStateManager = PaymentFormStateManager(
            supportedCardSchemes = listOf(CardScheme.AMERICAN_EXPRESS, CardScheme.MADA),
            billingFormAddressToBillingAddressMapper = spyBillingFormAddressToBillingAddressMapper
        )
        val expectedSupportedSchemes = listOf(CardScheme.AMERICAN_EXPRESS, CardScheme.MADA)

        // Then
        Assertions.assertEquals(paymentFormStateManager.supportedCardSchemeList, expectedSupportedSchemes)
    }

    @Test
    fun `when payment form cardHolderName is provided then cardHolderName should updated correctly`() {
        // Given
        paymentFormStateManager = PaymentFormStateManager(
            emptyList(), paymentFormPrefillData = PrefillData("Test Name"), spyBillingFormAddressToBillingAddressMapper
        )
        val expectedTestName = "Test Name"

        // Then
        Assertions.assertEquals(paymentFormStateManager.cardHolderName.value, expectedTestName)
    }

    @Test
    fun `when country is prefilled then selectedCountry in the payment state manager should updated correctly`() {
        // Given
        val givenPrefilledBillingFormAddress = BillingFormAddress(address = PaymentFormConfigTestData.address)
        paymentFormStateManager = PaymentFormStateManager(
            emptyList(),
            paymentFormPrefillData = PrefillData(billingFormAddress = givenPrefilledBillingFormAddress),
            spyBillingFormAddressToBillingAddressMapper
        )
        val expectedCountry = Country.UNITED_KINGDOM

        // Then
        Assertions.assertEquals(paymentFormStateManager.selectedCountry.value, expectedCountry)
    }

    @Test
    fun `when custom supported card schemes isn't provided then checkout's all supportedCardSchemes should updated`() {
        // Given
        paymentFormStateManager = PaymentFormStateManager(
            supportedCardSchemes = emptyList(),
            billingFormAddressToBillingAddressMapper = spyBillingFormAddressToBillingAddressMapper
        )
        val expectedSupportedSchemes = CardScheme.fetchAllSupportedCardSchemes()
        // Then
        Assertions.assertEquals(paymentFormStateManager.supportedCardSchemeList, expectedSupportedSchemes)
    }

    @Test
    fun `when BillingFormAddress is requested in PrefillData then billingAddress in payment state manager is updated correctly`() {
        // Given
        val expectedBillingFormAddress = BillingFormAddress(
            address = PaymentFormConfigTestData.address
        )
        val expectedBillingAddress = BillingAddress(
            address = PaymentFormConfigTestData.address
        )

        // When
        paymentFormStateManager = PaymentFormStateManager(
            supportedCardSchemes = emptyList(),
            billingFormAddressToBillingAddressMapper = spyBillingFormAddressToBillingAddressMapper,
            paymentFormPrefillData = PrefillData(
                billingFormAddress = BillingFormAddress(
                    address = PaymentFormConfigTestData.address
                )
            )
        )

        // Then
        verify(exactly = 1) { spyBillingFormAddressToBillingAddressMapper.map(expectedBillingFormAddress) }
        Assertions.assertEquals(paymentFormStateManager.billingAddress.value, expectedBillingAddress)
    }

    @Test
    fun `when BillingFormAddress is not requested in PrefillData then default billingAddress in payment state manager is updated correctly`() {
        // Given
        val expectedBillingFormAddress = BillingFormAddress(
            address = PaymentFormConfigTestData.address
        )
        val expectedBillingAddress = BillingAddress()

        // When
        paymentFormStateManager = PaymentFormStateManager(
            supportedCardSchemes = emptyList(),
            billingFormAddressToBillingAddressMapper = spyBillingFormAddressToBillingAddressMapper,
            paymentFormPrefillData = PrefillData(
                cardHolderName = "Test Name"
            )
        )

        // Then
        verify(exactly = 0) { spyBillingFormAddressToBillingAddressMapper.map(expectedBillingFormAddress) }
        Assertions.assertEquals(paymentFormStateManager.billingAddress.value, expectedBillingAddress)
    }

    @ParameterizedTest(
        name = "When reset of payment state is requested with: " + "isCvvValid = {0} and isBillingAddressValid = {1};" +
                " " + "Then default cvv isValid state = {0}, cardholderName isValid state = {1}" +
                " " + "address isValid state = {2} and address isEnabled state = {3}"
    )
    @MethodSource("resetArguments")
    fun `when reset of payment state is requested then payment state is returned to a default state`(
        isCvvValid: Boolean,
        isCardHolderNameValid: Boolean,
        isBillingAddressValid: Boolean,
        isBillingAddressEnabled: Boolean,
    ) {
        // Given
        val supportedSchemes = listOf(CardScheme.VISA, CardScheme.DISCOVER)
        val cardHolderName = ""
        paymentFormStateManager = PaymentFormStateManager(
            supportedCardSchemes = supportedSchemes,
            billingFormAddressToBillingAddressMapper = spyBillingFormAddressToBillingAddressMapper
        )
        paymentFormStateManager.cvv.value = "123"
        paymentFormStateManager.isCvvValid.value = !isCvvValid
        paymentFormStateManager.cardNumber.value = "23423423423423423"
        paymentFormStateManager.isCardNumberValid.value = true
        paymentFormStateManager.expiryDate.value = "0234"
        paymentFormStateManager.isExpiryDateValid.value = true
        paymentFormStateManager.billingAddress.value = BillingAddress(
            name = "name",
            phone = Phone("+4332452452345234", Country.UNITED_KINGDOM)
        )
        paymentFormStateManager.isBillingAddressValid.value = !isBillingAddressValid
        paymentFormStateManager.isBillingAddressEnabled.value = !isBillingAddressEnabled
        paymentFormStateManager.visitedCountryPicker.value = true

        // When
        paymentFormStateManager.resetPaymentState(
            isCvvValid,
            isCardHolderNameValid,
            isBillingAddressValid,
            isBillingAddressEnabled
        )

        // Then
        Assertions.assertEquals(paymentFormStateManager.cvv.value, "")
        Assertions.assertEquals(paymentFormStateManager.isCvvValid.value, isCvvValid)
        Assertions.assertEquals(paymentFormStateManager.isCardHolderNameValid.value, isCardHolderNameValid)
        Assertions.assertEquals(paymentFormStateManager.cardNumber.value, "")
        Assertions.assertEquals(paymentFormStateManager.isCardNumberValid.value, false)
        Assertions.assertEquals(paymentFormStateManager.expiryDate.value, "")
        Assertions.assertEquals(paymentFormStateManager.isExpiryDateValid.value, false)
        Assertions.assertEquals(paymentFormStateManager.isBillingAddressValid.value, isBillingAddressValid)
        Assertions.assertEquals(paymentFormStateManager.isBillingAddressEnabled.value, isBillingAddressEnabled)
        Assertions.assertEquals(paymentFormStateManager.visitedCountryPicker.value, false)
        Assertions.assertEquals(paymentFormStateManager.supportedCardSchemeList, supportedSchemes)
        Assertions.assertEquals(paymentFormStateManager.cardHolderName.value, cardHolderName)
        Assertions.assertEquals(paymentFormStateManager.selectedCountry.value, null)
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.N)
        @JvmStatic
        fun resetArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(false, false, false, true),
            Arguments.of(true, true, false, true),
            Arguments.of(false, false, true, true),
            Arguments.of(true, true, true, true),
            Arguments.of(false, false, false, false),
            Arguments.of(true, true, false, false),
            Arguments.of(false, false, true, false),
            Arguments.of(true, true, true, false),
        )
    }

    private fun initMappers() {
        spyBillingFormAddressToBillingAddressMapper = BillingFormAddressToBillingAddressMapper()
    }
}
