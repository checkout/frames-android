package com.checkout.frames.component.cardnumber

import android.annotation.SuppressLint
import androidx.compose.ui.text.input.KeyboardType
import com.checkout.base.error.CheckoutError
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.CardScheme
import com.checkout.frames.R
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.mapper.ImageStyleToDynamicComposableImageMapper
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
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.validation.api.CardValidator
import com.checkout.validation.model.ValidationResult
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertEquals

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class CardNumberViewModelTest {

    @RelaxedMockK
    lateinit var mockCardValidator: CardValidator

    @SpyK
    lateinit var spyInputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>

    @SpyK
    lateinit var spyInputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>

    @SpyK
    lateinit var spyDynamicImageMapper: ImageStyleToDynamicComposableImageMapper

    @SpyK
    var spyPaymentStateManager: PaymentStateManager = PaymentFormStateManager(emptyList())

    private var style: CardNumberComponentStyle = CardNumberComponentStyle()

    private lateinit var viewModel: CardNumberViewModel

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        viewModel = CardNumberViewModel(
            spyPaymentStateManager,
            mockCardValidator,
            spyInputComponentStyleMapper,
            spyInputComponentStateMapper,
            spyDynamicImageMapper,
            style
        )

        every { mockCardValidator.validateCardNumber(any()) } returns ValidationResult.Success(CardScheme.UNKNOWN)
        every { mockCardValidator.eagerValidateCardNumber(any()) } returns ValidationResult.Success(CardScheme.UNKNOWN)
    }

    /** Initial state tests **/

    @Test
    fun `when view model is initialised then component style is mapped to initial component state`() {
        // Then
        verify { spyInputComponentStateMapper.map(style.inputStyle) }
    }

    @Test
    fun `when view model is initialised then initial state has empty card number and unknown card scheme`() {
        // Then
        assertTrue(viewModel.componentState.cardNumber.value.isEmpty())
        assertEquals(viewModel.componentState.cardScheme.value, CardScheme.UNKNOWN)
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

    @Test
    fun `when view model is initialised then initial style has correct keyboard type`() {
        // Then
        assertEquals(viewModel.componentStyle.inputFieldStyle.keyboardOptions.keyboardType, KeyboardType.Number)
    }

    /** Payment state related tests **/

    @Test
    fun `when card number updated then card number state in payment state manager is updated`() {
        // Given
        val testCardNumber = "123123123123123"
        val expectedCardScheme = CardScheme.AMERICAN_EXPRESS
        every {
            mockCardValidator.eagerValidateCardNumber(eq(testCardNumber))
        } returns ValidationResult.Success(expectedCardScheme)

        // When
        viewModel.onCardNumberChange(testCardNumber)

        // Then
        assertEquals(spyPaymentStateManager.cardNumber.value, testCardNumber)
        assertEquals(spyPaymentStateManager.cardScheme.value, expectedCardScheme)
    }

    @Test
    fun `when focus change triggered more than once then card number state in payment state manager is updated`() {
        // Given
        val testCardNumber = "123123123123123"
        viewModel.componentState.cardNumber.value = testCardNumber
        every {
            mockCardValidator.validateCardNumber(eq(testCardNumber))
        } returns ValidationResult.Success(CardScheme.MADA)

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // Then
        assertTrue(spyPaymentStateManager.isCardNumberValid.value)
    }

    /** Input data filtering **/

    @Test
    fun `when card number with non digits entered then non digit symbols are removed`() {
        // Given
        val sourceInput = "234234_234.234wer1"
        val filteredInput = "2342342342341"

        // When
        viewModel.onCardNumberChange(sourceInput)

        // Then
        assertEquals(viewModel.componentState.cardNumber.value, filteredInput)
    }

    /** Validation related tests **/

    @Test
    fun `when focus change triggered for the first time then full card validation is not invoked`() {
        // When
        viewModel.onFocusChanged(false)

        // Then
        verify(exactly = 0) { mockCardValidator.validateCardNumber(any()) }
    }

    @Test
    fun `when focus change triggered more than once then full card validation is invoked`() {
        // Given
        val testCardNumber = "123123123123123"
        viewModel.componentState.cardNumber.value = testCardNumber

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // Then
        verify(exactly = 1) { mockCardValidator.validateCardNumber(testCardNumber) }
    }

    @Test
    fun `when eager validation returns card scheme then card scheme state updated correctly`() {
        // Given
        val expectedCardScheme = CardScheme.MASTERCARD
        val testCardNumber = "123123123123123"
        every {
            mockCardValidator.eagerValidateCardNumber(eq(testCardNumber))
        } returns ValidationResult.Success(expectedCardScheme)

        // When
        viewModel.onCardNumberChange(testCardNumber)

        // Then
        verify(exactly = 1) { mockCardValidator.eagerValidateCardNumber(eq(testCardNumber)) }
        assertEquals(viewModel.componentState.cardScheme.value, expectedCardScheme)
    }

    @Test
    fun `when full card number validation succeeds then just hide error`() {
        // Given
        val testCardNumber = "3424234234234"
        viewModel.componentState.cardNumber.value = testCardNumber
        every {
            mockCardValidator.validateCardNumber(eq(testCardNumber))
        } returns ValidationResult.Success(CardScheme.MADA)
        // Show error from the start to check that it will be hidden.
        viewModel.componentState.showError("Test error.")

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // Then
        verify(exactly = 1) { mockCardValidator.validateCardNumber(eq(testCardNumber)) }
        with(viewModel.componentState.inputState) {
            assertFalse(inputFieldState.isError.value)
            assertFalse(errorState.isVisible.value)
        }
        // Check that initial card scheme wasn't changed, as we don't change card scheme for full card validation result.
        assertEquals(viewModel.componentState.cardScheme.value, CardScheme.UNKNOWN)
    }

    @Test
    fun `when full card number validation fails then error is shown`() {
        // Given
        val testCardNumber = "3424234234234"
        viewModel.componentState.cardNumber.value = testCardNumber
        every {
            mockCardValidator.validateCardNumber(eq(testCardNumber))
        } returns ValidationResult.Failure(CheckoutError("123"))

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // Then
        verify(exactly = 1) { mockCardValidator.validateCardNumber(eq(testCardNumber)) }
        with(viewModel.componentState.inputState) {
            assertTrue(inputFieldState.isError.value)
            assertTrue(errorState.isVisible.value)
            assertEquals(errorState.textId.value, R.string.cko_base_invalid_card_number_error)
        }
    }

    @Test
    fun `when eager card number validation succeeds then hide error and update scheme with max length`() {
        // Given
        val testCardNumber = "4242424242424242"
        every {
            mockCardValidator.eagerValidateCardNumber(eq(testCardNumber))
        } returns ValidationResult.Success(CardScheme.VISA)
        // Show error from the start to check that it will be hidden.
        viewModel.componentState.showError("Test error.")

        // When
        viewModel.onCardNumberChange(testCardNumber)

        // Then
        verify(exactly = 1) { mockCardValidator.eagerValidateCardNumber(eq(testCardNumber)) }
        with(viewModel.componentState.inputState) {
            assertFalse(inputFieldState.isError.value)
            assertFalse(errorState.isVisible.value)
        }
        assertEquals(viewModel.componentState.cardScheme.value, CardScheme.VISA)
        assertEquals(viewModel.componentState.cardNumberLength.value, CardScheme.VISA.maxNumberLength)
    }

    @Test
    fun `when eager card number validation fails then error is shown`() {
        // Given
        val testCardNumber = "4242424242424242"
        every {
            mockCardValidator.eagerValidateCardNumber(eq(testCardNumber))
        } returns ValidationResult.Failure(CheckoutError("123"))

        // When
        viewModel.onCardNumberChange(testCardNumber)

        // Then
        verify(exactly = 1) { mockCardValidator.eagerValidateCardNumber(eq(testCardNumber)) }
        with(viewModel.componentState.inputState) {
            assertTrue(inputFieldState.isError.value)
            assertTrue(errorState.isVisible.value)
            assertEquals(errorState.textId.value, R.string.cko_base_invalid_card_number_error)
        }
    }

    @ParameterizedTest(
        name = "When on card number change invoked with {0} then {1} set to text field state"
    )
    @MethodSource("onTextChangedArguments")
    fun `When on card number change invoked with a string then cleaned string should be set to a text field state`(
        enteredText: String,
        cleanedText: String
    ) {
        // When
        viewModel.onCardNumberChange(enteredText)

        // Then
        assertEquals(viewModel.componentState.cardNumber.value, cleanedText)
    }

    private fun initMappers() {
        val containerMapper = ContainerStyleToModifierMapper()
        val textLabelStyleMapper = TextLabelStyleToViewStyleMapper(containerMapper)
        val imageMapper = ImageStyleToComposableImageMapper()

        spyDynamicImageMapper = ImageStyleToDynamicComposableImageMapper()
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

    companion object {
        @JvmStatic
        fun onTextChangedArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("123", "123"),
            Arguments.of("123 ", "123"),
            Arguments.of(" 123", "123"),
            Arguments.of("12 3", "123"),
            Arguments.of("123234werw", "123234"),
            Arguments.of("42424.2424242_111,11", "42424242424211111")
        )
    }
}
