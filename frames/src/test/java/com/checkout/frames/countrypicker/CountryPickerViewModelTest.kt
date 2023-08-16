package com.checkout.frames.countrypicker

import android.annotation.SuppressLint
import androidx.compose.ui.Modifier
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.Country
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.InputFieldStyleToViewStyleMapper
import com.checkout.frames.mapper.InputFieldStyleToInputFieldStateMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.mapper.ImageStyleToDynamicComposableImageMapper
import com.checkout.frames.screen.countrypicker.CountryPickerViewModel
import com.checkout.frames.screen.manager.PaymentFormStateManager
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.screen.CountryPickerStyle
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.view.InputFieldState
import com.checkout.frames.view.TextLabelState
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class CountryPickerViewModelTest {

    @SpyK
    lateinit var spyInputFieldStyleMapper: Mapper<InputFieldStyle, InputFieldViewStyle>

    @SpyK
    lateinit var spyInputFieldStateMapper: Mapper<InputFieldStyle, InputFieldState>

    @SpyK
    lateinit var spyTextLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>

    @SpyK
    lateinit var spyTextLabelStateMapper: Mapper<TextLabelStyle?, TextLabelState>

    @SpyK
    lateinit var spyContainerMapper: Mapper<ContainerStyle, Modifier>

    @SpyK
    lateinit var spyDynamicImageMapper: ImageStyleToDynamicComposableImageMapper

    @SpyK
    var paymentStateManager: PaymentStateManager = PaymentFormStateManager(supportedCardSchemes = emptyList())

    private val style: CountryPickerStyle = CountryPickerStyle()
    private lateinit var viewModel: CountryPickerViewModel

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        viewModel = CountryPickerViewModel(
            paymentStateManager,
            spyInputFieldStyleMapper,
            spyInputFieldStateMapper,
            spyTextLabelStyleMapper,
            spyTextLabelStateMapper,
            spyContainerMapper,
            spyDynamicImageMapper,
            style
        )
    }

    /** Initial state tests **/

    @Test
    fun `when view model is initialised then screen title style is mapped to initial title state`() {
        // Then
        verify { spyTextLabelStateMapper.map(style.screenTitleStyle) }
    }

    @Test
    fun `when view model is initialised then search field style is mapped to initial search field state`() {
        // Then
        verify { spyInputFieldStateMapper.map(style.searchFieldStyle) }
    }

    /** Initial style tests **/

    @Test
    fun `when view model is initialised then screen title style is mapped to initial title style`() {
        // Then
        verify { spyTextLabelStyleMapper.map(style.screenTitleStyle) }
    }

    @Test
    fun `when view model is initialised then search field style is mapped to initial search field style`() {
        // Then
        verify { spyInputFieldStyleMapper.map(style.searchFieldStyle) }
    }

    @Test
    fun `when view model is initialised then search item style is mapped to initial search item style`() {
        // Then
        verify { spyTextLabelStyleMapper.map(style.searchItemStyle) }
    }

    @Test
    fun `when view model is initialised then screen container style is mapped to screen modifier`() {
        // Then
        verify { spyContainerMapper.map(style.containerStyle) }
    }

    /** Init internal screen state **/

    @Test
    fun `when view model is initialised then country picker shows all countries`() {
        // Then
        assertEquals(
            Country.values().filter { it != Country.INVALID_COUNTRY }.size,
            viewModel.searchCountries.value.size
        )
    }

    @Test
    fun `when view model is initialised then search is inactive`() {
        // Then
        assertFalse(viewModel.isSearchActive.value)
    }

    /** Main functionality **/

    @Test
    fun `when search is focused then search is active`() {
        // When
        viewModel.onFocusChanged(true)

        // Then
        assertTrue(viewModel.isSearchActive.value)
    }

    @Test
    fun `when search data is changed then search countries updated`() {
        // Given
        val searchData = "kin"

        // When
        viewModel.onSearchChange(searchData)

        // Then
        assertTrue(viewModel.searchCountries.value.all { it.name.contains(searchData, true) })
    }

    @Test
    fun `when search data is changed with unsupported country name then search countries is empty`() {
        // Given
        val searchData = "wertwertwetwecxvdfgewrtesxgfdfg"

        // When
        viewModel.onSearchChange(searchData)

        // Then
        assertTrue(viewModel.searchCountries.value.isEmpty())
    }

    @Test
    fun `when country is chosen then country is updated in payment state manager`() {
        // Given
        val expectedCountry = Country.UKRAINE

        // When
        viewModel.onCountryChosen("UA")

        // Then
        val actualBillingForm = paymentStateManager.billingAddress.value
        assertEquals(expectedCountry, actualBillingForm.address?.country)
    }

    @Test
    fun `when country is chosen then country picker screen is closed`() {
        // When
        viewModel.onCountryChosen("GB")

        // Then
        assertTrue(viewModel.goBack.value)
    }

    @Test
    fun `when reset is pressed then search is inactive`() {
        // When
        viewModel.onReset()

        // Then
        assertFalse(viewModel.isSearchActive.value)
    }

    @Test
    fun `when reset is pressed then search data is reset to empty state`() {
        // Given
        viewModel.onSearchChange("rei")

        // When
        viewModel.onReset()

        // Then
        assertTrue(viewModel.searchFieldState.text.value.isEmpty())
    }

    @Test
    fun `when clear is pressed and field in a focus state then search data is reset to empty state`() {
        // Given
        viewModel.onSearchChange("rei")
        viewModel.onFocusChanged(true)

        // When
        viewModel.onClear()

        // Then
        assertTrue(viewModel.searchFieldState.text.value.isEmpty())
    }

    @Test
    fun `when clear is pressed and field unfocused then search data is reset to empty state and search is inactive`() {
        // Given
        viewModel.onSearchChange("rei")
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // When
        viewModel.onClear()

        // Then
        assertFalse(viewModel.isSearchActive.value)
        assertTrue(viewModel.searchFieldState.text.value.isEmpty())
    }

    @Test
    fun `onLeaveScreen should update visitedCountryPicker to true in PaymentStateManager`() {
        paymentStateManager.visitedCountryPicker.value = false
        assertFalse(paymentStateManager.visitedCountryPicker.value)
        viewModel.onLeaveScreen()
        assertTrue(paymentStateManager.visitedCountryPicker.value)
    }

    private fun initMappers() {
        val imageMapper = ImageStyleToComposableImageMapper()

        spyContainerMapper = ContainerStyleToModifierMapper()
        spyTextLabelStyleMapper = TextLabelStyleToViewStyleMapper(spyContainerMapper)
        spyInputFieldStyleMapper = InputFieldStyleToViewStyleMapper(spyTextLabelStyleMapper)
        spyInputFieldStateMapper = InputFieldStyleToInputFieldStateMapper(imageMapper)
        spyTextLabelStateMapper = TextLabelStyleToStateMapper(imageMapper)
        spyDynamicImageMapper = ImageStyleToDynamicComposableImageMapper()
    }
}
