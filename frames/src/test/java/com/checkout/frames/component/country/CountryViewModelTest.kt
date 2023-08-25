package com.checkout.frames.component.country

import android.annotation.SuppressLint
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.Country
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.mapper.BillingFormAddressToBillingAddressMapper
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.InputComponentStyleToStateMapper
import com.checkout.frames.mapper.InputComponentStyleToViewStyleMapper
import com.checkout.frames.mapper.InputFieldStyleToInputFieldStateMapper
import com.checkout.frames.mapper.InputFieldStyleToViewStyleMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.screen.manager.PaymentFormStateManager
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.CountryComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.internal.assertEquals
import org.junit.After
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class CountryViewModelTest {

    @SpyK
    lateinit var spyInputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>

    @SpyK
    lateinit var spyInputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>

    @SpyK
    var spyPaymentStateManager: PaymentStateManager = PaymentFormStateManager(
        supportedCardSchemes = emptyList(),
        billingFormAddressToBillingAddressMapper = BillingFormAddressToBillingAddressMapper()
    )

    private lateinit var viewModel: CountryViewModel
    private var style = CountryComponentStyle()
    private val dispatcher = StandardTestDispatcher()

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        viewModel = CountryViewModel(
            spyPaymentStateManager,
            spyInputComponentStyleMapper,
            spyInputComponentStateMapper,
            style
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
        verify { spyInputComponentStateMapper.map(style.inputStyle) }
    }

    /** Initial style tests **/

    @Test
    fun `when view model is initialised then component style is mapped to initial component view style`() {
        // Then
        verify { spyInputComponentStyleMapper.map(style.inputStyle) }
    }

    @Test
    fun `when view model is initialised then field is disabled and readonly`() {
        // Then
        assertTrue(viewModel.componentStyle.inputFieldStyle.readOnly)
        assertFalse(viewModel.componentStyle.inputFieldStyle.enabled)
    }

    /** Country field update **/

    @Test
    fun `when country field data is updated it should call onCountryUpdated`() = runTest {
        // Given
        spyPaymentStateManager.billingAddress.value.address?.country = Country.UNITED_KINGDOM

        fun assertUpdatedCountry(expectedCountry: Country, actualCountry: Country?) {
            assertEquals(expectedCountry, actualCountry)
        }
        // When
        viewModel.prepare { actualCountry ->
            // Then
            assertUpdatedCountry(Country.UNITED_KINGDOM, actualCountry)
        }
    }

    @Test
    fun `when country in payment state manager is changed then country field data is updated`() = runTest {
        // Given
        val testCountry = Country.UNITED_STATES_OF_AMERICA
        val expectedCountryFieldText = "\uD83C\uDDFA\uD83C\uDDF8    United States"

        spyPaymentStateManager.billingAddress.value.address?.country = Country.UNITED_KINGDOM
        viewModel.prepare { }

        // When
        spyPaymentStateManager.billingAddress.value.address?.country = testCountry
        testScheduler.advanceUntilIdle()

        // Then
        assertEquals(expectedCountryFieldText, viewModel.componentState.inputFieldState.text.value)
    }

    private fun initMappers() {
        val containerMapper = ContainerStyleToModifierMapper()
        val textLabelStyleMapper = TextLabelStyleToViewStyleMapper(containerMapper)
        val imageMapper = ImageStyleToComposableImageMapper()

        spyInputComponentStyleMapper = InputComponentStyleToViewStyleMapper(
            textLabelStyleMapper,
            InputFieldStyleToViewStyleMapper(textLabelStyleMapper),
            containerMapper
        )
        spyInputComponentStateMapper = InputComponentStyleToStateMapper(
            TextLabelStyleToStateMapper(imageMapper),
            InputFieldStyleToInputFieldStateMapper(imageMapper)
        )
    }
}
