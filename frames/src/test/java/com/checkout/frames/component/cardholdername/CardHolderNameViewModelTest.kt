package com.checkout.frames.component.cardholdername

import android.annotation.SuppressLint
import com.checkout.base.mapper.Mapper
import com.checkout.frames.R
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.InputComponentStyleToViewStyleMapper
import com.checkout.frames.mapper.InputFieldStyleToViewStyleMapper
import com.checkout.frames.mapper.InputComponentStyleToStateMapper
import com.checkout.frames.mapper.InputFieldStyleToInputFieldStateMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.screen.manager.PaymentFormStateManager
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.CardHolderNameComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.amshove.kluent.internal.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class CardHolderNameViewModelTest {

    @SpyK
    lateinit var spyInputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>

    @SpyK
    lateinit var spyInputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>

    private val style: CardHolderNameComponentStyle = CardHolderNameComponentStyle()

    private lateinit var viewModel: CardHolderNameViewModel

    @SpyK
    lateinit var spyPaymentStateManager: PaymentStateManager

    init {
        initMappers()
        initPaymentStateManager()
    }

    @BeforeEach
    fun setUp() {
        viewModel = CardHolderNameViewModel(
            spyPaymentStateManager,
            spyInputComponentStyleMapper, spyInputComponentStateMapper, style
        )
    }

    /** Initial state tests **/

    @Test
    fun `when view model is initialised then component style is mapped to initial component state`() {
        // Then
        verify { spyInputComponentStateMapper.map(style.inputStyle) }
    }

    @Test
    fun `when view model is initialised then initial state has empty cardHolderName`() {
        // Then
        assertTrue(viewModel.componentState.cardHolderName.value.isEmpty())
    }

    /** Initial style tests **/

    @Test
    fun `when view model is initialised then component style is mapped to initial component view style`() {
        // Then
        verify { spyInputComponentStyleMapper.map(style.inputStyle) }
    }

    @Test
    fun `when view model is initialised then initial style has forced LTR`() {
        // Then
        assertTrue(viewModel.componentStyle.inputFieldStyle.forceLTR)
    }

    /** Payment state related tests **/

    @Test
    fun `when cardHolderName updated then cardHolderName state in payment state manager is updated`() {
        // Given
        val testCardHolderName = "TestName"

        // When
        viewModel.onCardHolderNameChange(testCardHolderName)

        // Then
        assertEquals(spyPaymentStateManager.cardHolderName.value, testCardHolderName)
    }

    @Test
    fun `when focus change triggered more than once then cardHolderName state in payment state manager is updated`() {
        // Given
        val testCardHolderName = "TestName"
        viewModel.componentState.cardHolderName.value = testCardHolderName

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // Then
        assertTrue(spyPaymentStateManager.isCardHolderNameValid.value)
    }

    @Test
    fun `when cardHolderName is already updated in paymentStateManager then cardHolderName in componentState is updated`() {
        // Given
        val expectedCardHolderName = "TestName"
        spyPaymentStateManager.cardHolderName.value = expectedCardHolderName

        // When
        viewModel.prepare()

        // Then
        assertEquals(expectedCardHolderName, viewModel.componentState.cardHolderName.value)
    }

    /** Validation related tests **/

    @Test
    fun `when valid cardHolderName entered then error is hidden`() {
        // Given
        val testCardHolderName = "TestName"
        viewModel.componentState.showError(R.string.cko_cardholder_name_error)

        // When
        viewModel.onCardHolderNameChange(testCardHolderName)

        // Then
        with(viewModel.componentState.inputState) {
            assertFalse(inputFieldState.isError.value)
            assertFalse(errorState.isVisible.value)
        }
    }

    @Test
    fun `when cardHolderName is empty on focus change then error is shown`() {
        // Given
        val testCardHolderName = ""
        viewModel.componentState.cardHolderName.value = testCardHolderName
        viewModel.componentState.hideError()

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // Then
        with(viewModel.componentState.inputState) {
            assertTrue(inputFieldState.isError.value)
            assertTrue(errorState.isVisible.value)
            assertEquals(errorState.textId.value, R.string.cko_cardholder_name_error)
        }
    }

    @Test
    fun `when cardHolderName is valid on focus change then error is hidden`() {
        // Given
        val testCardHolderName = "TestName"
        viewModel.componentState.cardHolderName.value = testCardHolderName
        viewModel.componentState.showError(R.string.cko_cardholder_name_error)

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // Then
        with(viewModel.componentState.inputState) {
            assertFalse(inputFieldState.isError.value)
            assertFalse(errorState.isVisible.value)
        }
    }

    private fun initMappers() {
        val containerMapper = ContainerStyleToModifierMapper()
        val textLabelStyleMapper = TextLabelStyleToViewStyleMapper(containerMapper)
        val imageMapper = ImageStyleToComposableImageMapper()

        spyInputComponentStyleMapper = InputComponentStyleToViewStyleMapper(
            textLabelStyleMapper, InputFieldStyleToViewStyleMapper(textLabelStyleMapper), containerMapper
        )
        spyInputComponentStateMapper = InputComponentStyleToStateMapper(
            TextLabelStyleToStateMapper(imageMapper), InputFieldStyleToInputFieldStateMapper(imageMapper)
        )
    }

    private fun initPaymentStateManager() {
        spyPaymentStateManager = PaymentFormStateManager(
            supportedCardSchemes = emptyList(),
            paymentFormCardHolderName = ""
        )
    }
}
