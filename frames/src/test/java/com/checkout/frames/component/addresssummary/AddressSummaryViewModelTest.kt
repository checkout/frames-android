package com.checkout.frames.component.addresssummary

import android.annotation.SuppressLint
import android.text.BidiFormatter
import android.text.TextDirectionHeuristics
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.Country
import com.checkout.frames.mapper.BillingFormAddressToBillingAddressMapper
import com.checkout.frames.mapper.ButtonStyleToInternalStateMapper
import com.checkout.frames.mapper.ButtonStyleToInternalViewStyleMapper
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.DividerStyleToViewStyleMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.addresssummary.AddressSummaryComponentStyleToStateMapper
import com.checkout.frames.mapper.addresssummary.AddressSummaryComponentStyleToViewStyleMapper
import com.checkout.frames.mapper.addresssummary.AddressSummarySectionStyleToViewStyleMapper
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress.Companion.isEdited
import com.checkout.frames.screen.manager.PaymentFormStateManager
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.screen.paymentform.model.BillingFormAddress
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.style.view.addresssummary.AddressSummaryComponentViewStyle
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone
import io.mockk.every
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class AddressSummaryViewModelTest {

    private val bidiFormatter: BidiFormatter = mockk()

    @SpyK
    private lateinit var spyBillingFormAddressToBillingAddressMapper: Mapper<BillingFormAddress?, BillingAddress>

    @SpyK
    private var spyPaymentStateManager: PaymentStateManager

    @SpyK
    lateinit var spyComponentStateMapper: Mapper<AddressSummaryComponentStyle, AddressSummaryComponentState>

    @SpyK
    lateinit var spyComponentStyleMapper: Mapper<AddressSummaryComponentStyle, AddressSummaryComponentViewStyle>

    private val dispatcher = StandardTestDispatcher()
    private var style: AddressSummaryComponentStyle = AddressSummaryComponentStyle()
    private lateinit var viewModel: AddressSummaryViewModel

    init {
        initMappers()
        spyPaymentStateManager = PaymentFormStateManager(
            supportedCardSchemes = emptyList(),
            billingFormAddressToBillingAddressMapper = spyBillingFormAddressToBillingAddressMapper,
        )
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        viewModel = AddressSummaryViewModel(
            style, spyPaymentStateManager, spyComponentStateMapper, spyComponentStyleMapper,
        )
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    /** Initial state tests **/

    @Test
    fun `when view model is initialised then component style is mapped to initial component state`() {
        // Then
        verify { spyComponentStateMapper.map(style) }
    }

    @Test
    fun `when view model is initialised then initial component state contains enabled button`() {
        // Then
        assertTrue(viewModel.componentState.addAddressButtonState.isEnabled.value)
    }

    /** Initial style tests **/

    @Test
    fun `when view model is initialised then component style is mapped to initial component view style`() {
        // Then
        verify { spyComponentStyleMapper.map(style) }
    }

    /** Address summary update **/

    @Test
    fun `when address in payment state manager is changed then address summary is updated`() = runTest {
        // Given
        val country = Country.UNITED_KINGDOM
        val isBillingAddressEdited = true
        spyPaymentStateManager.isBillingAddressEnabled.value = true
        val testAddress = BillingAddress(
            address = Address(
                "LINE 1",
                "LINE 2",
                "",
                "",
                "ssdfsdf",
                country,
            ),
            phone = Phone("123", country),
        )
        every { bidiFormatter.unicodeWrap(any(), TextDirectionHeuristics.LTR) } returns "+44 123"
        val expectedAddressPreview = "LINE 1\nLINE 2\nssdfsdf\nUnited Kingdom\n+44 123"
        spyPaymentStateManager.billingAddress.value = testAddress

        // When
        viewModel.prepare(bidiFormatter)
        testScheduler.advanceUntilIdle()

        // Then
        assertEquals(isBillingAddressEdited, spyPaymentStateManager.billingAddress.value.isEdited())
        assertEquals(expectedAddressPreview, viewModel.componentState.addressPreviewState.text.value)
    }

    @Test
    fun `when address in payment state manager is not changed then address summary should be blank`() = runTest {
        // Given
        val isBillingAddressEdited = false
        val expectedAddressPreview = ""
        spyPaymentStateManager.isBillingAddressEnabled.value = false

        // When
        viewModel.prepare(bidiFormatter)
        testScheduler.advanceUntilIdle()

        // Then
        assertEquals(isBillingAddressEdited, spyPaymentStateManager.billingAddress.value.isEdited())
        assertEquals(expectedAddressPreview, viewModel.componentState.addressPreviewState.text.value)
    }

    private fun initMappers() {
        spyBillingFormAddressToBillingAddressMapper = BillingFormAddressToBillingAddressMapper()
        val textLabelStateMapper = TextLabelStyleToStateMapper(ImageStyleToComposableImageMapper())
        val containerMapper = ContainerStyleToModifierMapper()
        val textLabelStyleMapper = TextLabelStyleToViewStyleMapper(containerMapper)
        val buttonStyleMapper: Mapper<ButtonStyle, InternalButtonViewStyle> =
            ButtonStyleToInternalViewStyleMapper(containerMapper, textLabelStyleMapper)
        val addressSectionStyleMapper = AddressSummarySectionStyleToViewStyleMapper(
            textLabelStyleMapper,
            DividerStyleToViewStyleMapper(),
            buttonStyleMapper,
            containerMapper,
        )

        spyComponentStyleMapper = AddressSummaryComponentStyleToViewStyleMapper(
            textLabelStyleMapper, buttonStyleMapper, addressSectionStyleMapper, containerMapper,
        )
        spyComponentStateMapper = AddressSummaryComponentStyleToStateMapper(
            textLabelStateMapper, ButtonStyleToInternalStateMapper(textLabelStateMapper),
        )
    }
}
