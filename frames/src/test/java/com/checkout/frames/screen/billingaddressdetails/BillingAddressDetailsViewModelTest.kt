package com.checkout.frames.screen.billingaddressdetails

import android.annotation.SuppressLint
import androidx.compose.ui.Modifier
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.Country
import com.checkout.base.usecase.UseCase
import com.checkout.frames.R
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentsContainerState
import com.checkout.frames.mapper.ImageStyleToDynamicComposableImageMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.ButtonStyleToInternalStateMapper
import com.checkout.frames.mapper.ButtonStyleToInternalViewStyleMapper
import com.checkout.frames.mock.BillingAddressDetailsTestData
import com.checkout.frames.screen.billingaddress.billingaddressdetails.BillingAddressDetailsViewModel
import com.checkout.frames.screen.manager.PaymentFormStateManager
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.style.screen.default.DefaultBillingAddressDetailsStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.style.view.billingformdetails.BillingAddressInputComponentsViewContainerStyle
import com.checkout.frames.utils.extensions.provideBillingAddressDetails
import com.checkout.frames.view.InternalButtonState
import com.checkout.frames.view.TextLabelState
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
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
import java.util.Locale

@ExperimentalCoroutinesApi
@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class BillingAddressDetailsViewModelTest {

    @RelaxedMockK
    lateinit var mockBillingAddressDetailsComponentStateUseCase:
            UseCase<BillingAddressDetailsStyle, BillingAddressInputComponentsContainerState>

    @RelaxedMockK
    lateinit var mockBillingAddressDetailsComponentStyleUseCase:
            UseCase<BillingAddressDetailsStyle, BillingAddressInputComponentsViewContainerStyle>

    @SpyK
    var spyPaymentStateManager: PaymentStateManager = PaymentFormStateManager(emptyList())

    @SpyK
    lateinit var spyTextLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>

    @SpyK
    lateinit var spyContainerMapper: Mapper<ContainerStyle, Modifier>

    @SpyK
    lateinit var spyTextLabelStateMapper: Mapper<TextLabelStyle?, TextLabelState>

    @SpyK
    lateinit var spyButtonStyleMapper: Mapper<ButtonStyle, InternalButtonViewStyle>

    @SpyK
    lateinit var spyButtonStateMapper: Mapper<ButtonStyle, InternalButtonState>

    @SpyK
    lateinit var spyImageMapper: ImageStyleToDynamicComposableImageMapper

    private val style: BillingAddressDetailsStyle = BillingAddressDetailsStyle(
        DefaultBillingAddressDetailsStyle.headerComponentStyle(),
        DefaultBillingAddressDetailsStyle.inputComponentsContainerStyle()
    )

    private lateinit var viewModel: BillingAddressDetailsViewModel

    private val dispatcher = StandardTestDispatcher()

    private var defaultState =
        BillingAddressInputComponentsContainerState(BillingAddressDetailsTestData.fetchInputComponentStateList())
    private var defaultViewStyle =
        BillingAddressInputComponentsViewContainerStyle(BillingAddressDetailsTestData.fetchInputComponentStyleList())

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        every { mockBillingAddressDetailsComponentStateUseCase.execute(any()) } returns defaultState
        every { mockBillingAddressDetailsComponentStyleUseCase.execute(any()) } returns defaultViewStyle

        viewModel = BillingAddressDetailsViewModel(
            spyPaymentStateManager,
            spyTextLabelStyleMapper,
            spyTextLabelStateMapper,
            spyContainerMapper,
            spyImageMapper,
            mockBillingAddressDetailsComponentStateUseCase,
            mockBillingAddressDetailsComponentStyleUseCase,
            spyButtonStyleMapper,
            spyButtonStateMapper,
            style
        )
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    /** Initial state tests **/
    @Test
    fun `when view model is initialised then header button style is mapped to initial button state`() =
        // Then
        verify(exactly = 1) {
            spyButtonStateMapper.map(
                style.headerComponentStyle.headerButtonStyle
            )
        }

    @Test
    fun `when view model is initialised then header title style is mapped to initial header title state`() =
        // Then
        verify(exactly = 1) {
            spyTextLabelStateMapper.map(
                style.headerComponentStyle.headerTitleStyle
            )
        }

    /** Initial style tests **/
    @Test
    fun `when view model is initialised then header button style is mapped to initial button style`() =
        // Then
        verify(exactly = 1) {
            spyButtonStyleMapper.map(
                style.headerComponentStyle.headerButtonStyle
            )
        }

    @Test
    fun `when view model is initialised then header title style is mapped to initial header title style`() =
        // Then
        verify(exactly = 1) {
            spyTextLabelStyleMapper.map(
                style.headerComponentStyle.headerTitleStyle
            )
        }

    @Test
    fun `when view model is initialised then inputComponents container style is mapped to inputComponents modifier`() {
        // Then
        verify { spyContainerMapper.map(style.inputComponentsContainerStyle.containerStyle) }
    }

    @Test
    fun `when view model is initialised then screen container style is mapped to screen modifier`() {
        // Then
        verify { spyContainerMapper.map(style.containerStyle) }
    }

    /** Init internal screen state **/
    @Test
    fun `when view model is initialised then save button has disable initial state`() {
        // Then
        assertEquals(
            viewModel.screenButtonState.isEnabled.value,
            false
        )
    }

    /** Functionality **/
    @Test
    fun `when save button is clicked then billing address details screen is closed`() {
        // When
        viewModel.onTapDoneButton()

        // Then
        assertTrue(viewModel.goBack.value)
    }

    @Test
    fun `when save button is clicked then billing address in payment state manager marked as valid`() {
        // Given
        spyPaymentStateManager.isBillingAddressValid.value = false

        // When
        viewModel.onTapDoneButton()

        // Then
        assertTrue(spyPaymentStateManager.isBillingAddressValid.value)
    }

    @Test
    fun `when data is updated in inputComponentsStateList then button enable state is updated as well`() = runTest {
        // When
        viewModel.inputComponentsStateList.forEach {
            it.addressFieldText.value = "dummy"
            it.isAddressFieldValid.value = true
        }

        // Then
        testScheduler.advanceUntilIdle()
        assertTrue(viewModel.screenButtonState.isEnabled.value)
    }

    @Test
    fun `when button click invoked then data is updated in state manager correctly`() = runTest {
        // Given
        viewModel.onAddressFieldTextChange(0, "Test address one")
        viewModel.onAddressFieldTextChange(1, "Test address two")
        val expectedBillingAddress =
            viewModel.inputComponentsStateList.provideBillingAddressDetails(Country.from(Locale.getDefault().country))

        // When
        viewModel.onTapDoneButton()

        // Then
        testScheduler.advanceUntilIdle()
        assertEquals(spyPaymentStateManager.billingAddress.value, expectedBillingAddress)
    }

    @Test
    fun `when user open the edit billing address details then initial data loading correctly`() = runTest {
        // Given (user save the billing address details while coming from the add billing address details button)
        viewModel.onAddressFieldTextChange(0, "Test address one")
        viewModel.onAddressFieldTextChange(1, "Test address two")
        viewModel.onAddressFieldTextChange(2, "city")
        viewModel.onAddressFieldTextChange(3, "state")
        viewModel.onAddressFieldTextChange(4, "postcode")
        viewModel.onAddressFieldTextChange(5, "12345")
        val expectedBillingAddress =
            viewModel.inputComponentsStateList.provideBillingAddressDetails(Country.from(Locale.getDefault().country))
        viewModel.onTapDoneButton()

        // When (user launch the billing address details from the edit billing address details button)
        viewModel.prepare()

        // Then
        testScheduler.advanceUntilIdle()
        assertEquals(viewModel.screenButtonState.isEnabled.value, true)
        assertEquals(
            viewModel.inputComponentsStateList[0].addressFieldText.value,
            expectedBillingAddress.address?.addressLine1
        )
        assertEquals(
            viewModel.inputComponentsStateList[1].addressFieldText.value,
            expectedBillingAddress.address?.addressLine2
        )
        assertEquals(
            viewModel.inputComponentsStateList[2].addressFieldText.value,
            expectedBillingAddress.address?.city
        )
        assertEquals(
            viewModel.inputComponentsStateList[3].addressFieldText.value,
            expectedBillingAddress.address?.state
        )
        assertEquals(
            viewModel.inputComponentsStateList[4].addressFieldText.value,
            expectedBillingAddress.address?.zip
        )
        assertEquals(
            viewModel.inputComponentsStateList[5].addressFieldText.value,
            expectedBillingAddress.phone?.number
        )
    }

    @Test
    fun `when address field validation fails on onFocusChanged then error is shown`() {
        // Given
        val givenPosition = 0
        viewModel.inputComponentsStateList[givenPosition].addressFieldText.value = ""
        viewModel.inputComponentsStateList[givenPosition].isAddressFieldValid.value = false

        // When
        viewModel.onFocusChanged(0, true)
        viewModel.onFocusChanged(0, false)

        // Then
        with(viewModel.inputComponentsStateList[givenPosition].inputComponentState) {
            assertTrue(inputFieldState.isError.value)
            assertTrue(errorState.isVisible.value)
            assertEquals(errorState.textId.value, R.string.cko_billing_form_input_field_address_line_one_error)
        }
    }

    @Test
    fun `when on AddressField TextChange invoked with a string then correct data should be set to a text field state`() {
        // Given
        val givenPosition = 0
        val expectedText = "address Line one"
        val expectedIsAddressFieldValid =
            viewModel.inputComponentsStateList[givenPosition].isInputFieldOptional || expectedText.isNotBlank()

        // When
        viewModel.onAddressFieldTextChange(0, "address Line one")

        // Then
        assertEquals(viewModel.inputComponentsStateList[givenPosition].addressFieldText.value, expectedText)
        assertEquals(
            viewModel.inputComponentsStateList[givenPosition].isAddressFieldValid.value,
            expectedIsAddressFieldValid
        )
    }

    @Test
    fun `when onAddressFieldTextChange invoked for phone then correct clean text should be set to  text field state`() {
        // Given
        val givenPosition = 5
        val cleanedText = "12345"
        val expectedIsAddressFieldValid =
            viewModel.inputComponentsStateList[givenPosition].isInputFieldOptional || cleanedText.isNotBlank()

        // When
        viewModel.onAddressFieldTextChange(5, "1234,...,5")

        // Then
        assertEquals(viewModel.inputComponentsStateList[givenPosition].addressFieldText.value, cleanedText)
        assertEquals(
            viewModel.inputComponentsStateList[givenPosition].isAddressFieldValid.value,
            expectedIsAddressFieldValid
        )
    }

    private fun initMappers() {
        spyImageMapper = ImageStyleToDynamicComposableImageMapper()
        spyTextLabelStyleMapper = TextLabelStyleToViewStyleMapper(ContainerStyleToModifierMapper())
        spyTextLabelStateMapper = TextLabelStyleToStateMapper(ImageStyleToComposableImageMapper())
        spyContainerMapper = ContainerStyleToModifierMapper()
        spyButtonStateMapper = ButtonStyleToInternalStateMapper(spyTextLabelStateMapper)
        spyButtonStyleMapper = ButtonStyleToInternalViewStyleMapper(spyContainerMapper, spyTextLabelStyleMapper)
    }
}
