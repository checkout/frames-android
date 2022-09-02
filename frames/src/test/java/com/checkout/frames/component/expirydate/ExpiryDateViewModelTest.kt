package com.checkout.frames.component.expirydate

import android.annotation.SuppressLint
import androidx.compose.ui.text.input.KeyboardType
import com.checkout.base.error.CheckoutError
import com.checkout.base.mapper.Mapper
import com.checkout.frames.R
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.component.expirydate.model.SmartExpiryDateValidationRequest
import com.checkout.frames.component.expirydate.usecase.SmartExpiryDateValidationUseCase
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.InputComponentStyleToViewStyleMapper
import com.checkout.frames.mapper.InputFieldStyleToViewStyleMapper
import com.checkout.frames.mapper.InputComponentStyleToStateMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.utils.constants.EXPIRY_DATE_MAXIMUM_LENGTH_FOUR
import com.checkout.frames.utils.constants.EXPIRY_DATE_MAXIMUM_LENGTH_THREE
import com.checkout.validation.error.ValidationError
import com.checkout.validation.model.ValidationResult
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class ExpiryDateViewModelTest {

    @RelaxedMockK
    lateinit var mockSmartExpiryDateValidationUseCase: SmartExpiryDateValidationUseCase

    @SpyK
    lateinit var spyInputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>

    @SpyK
    lateinit var spyInputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>

    private var style: ExpiryDateComponentStyle = ExpiryDateComponentStyle(InputComponentStyle())

    private lateinit var viewModel: ExpiryDateViewModel

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        viewModel = ExpiryDateViewModel(
            mockSmartExpiryDateValidationUseCase,
            spyInputComponentStyleMapper,
            spyInputComponentStateMapper,
            style
        )
        every { mockSmartExpiryDateValidationUseCase.execute(any()) } returns ValidationResult.Success("")
    }

    /** Initial state tests **/

    @Test
    fun `when view model is initialised then component style is mapped to initial component state`() {
        // Then
        verify { spyInputComponentStateMapper.map(style.inputStyle) }
    }

    @Test
    fun `when view model is initialised then initial state has default max length of expiry date is four`() {
        // Then
        Assertions.assertTrue(viewModel.componentState.expiryDateMaxLength.value == EXPIRY_DATE_MAXIMUM_LENGTH_FOUR)
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
        Assertions.assertTrue(viewModel.componentStyle.inputFieldStyle.forceLTR)
    }

    @Test
    fun `when view model is initialised then initial style has correct keyboard type`() {
        // Then
        Assertions.assertEquals(
            viewModel.componentStyle.inputFieldStyle.keyboardOptions.keyboardType,
            KeyboardType.Number
        )
    }

    /** Validation related tests **/

    @Test
    fun `when focus change triggered for the first time then SmartExpiryDateValidationUseCase is not invoked`() {
        // When
        viewModel.onFocusChanged(false)

        // Then
        verify(exactly = 0) { mockSmartExpiryDateValidationUseCase.execute(any()) }
    }

    @Test
    fun `when focus change triggered more then once then SmartExpiryDateValidationUseCase is invoked`() {
        // Given
        viewModel.componentState.expiryDate.value = "1234"
        val testExpiryDate = viewModel.componentState.expiryDate.value

        val smartExpiryDateValidationRequest = SmartExpiryDateValidationRequest(false, testExpiryDate)

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // Then
        verify(exactly = 1) { mockSmartExpiryDateValidationUseCase.execute(smartExpiryDateValidationRequest) }
    }

    @Test
    fun `when validateExpiryDate validation succeeds on focus change then just hide error`() {
        // Given
        val testExpiryDate = "234"
        viewModel.componentState.expiryDate.value = testExpiryDate
        val smartExpiryDateValidationRequest = SmartExpiryDateValidationRequest(false, testExpiryDate)

        every {
            mockSmartExpiryDateValidationUseCase.execute(eq(smartExpiryDateValidationRequest))
        } returns ValidationResult.Success(testExpiryDate)
        // Show error from the start to check that it will be hidden.
        viewModel.componentState.showError(R.string.cko_base_invalid_expiry_date_error)

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // Then
        verify(exactly = 1) { mockSmartExpiryDateValidationUseCase.execute(eq(smartExpiryDateValidationRequest)) }
        with(viewModel.componentState.inputState) {
            Assertions.assertFalse(inputFieldState.isError.value)
            Assertions.assertFalse(errorState.isVisible.value)
        }
    }

    @Test
    fun `when validateExpiryDate validation fails on focus change then error is shown`() {
        // Given
        val testExpiryDate = "121"
        viewModel.componentState.expiryDate.value = testExpiryDate
        val smartExpiryDateValidationRequest = SmartExpiryDateValidationRequest(false, testExpiryDate)

        every {
            mockSmartExpiryDateValidationUseCase.execute(eq(smartExpiryDateValidationRequest))
        } returns ValidationResult.Failure(CheckoutError("56"))

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // Then
        verify(exactly = 1) { mockSmartExpiryDateValidationUseCase.execute(eq(smartExpiryDateValidationRequest)) }
        with(viewModel.componentState.inputState) {
            Assertions.assertTrue(inputFieldState.isError.value)
            Assertions.assertTrue(errorState.isVisible.value)
            Assertions.assertEquals(errorState.textId.value, R.string.cko_base_invalid_expiry_date_error)
        }
    }

    @Test
    fun `when validateExpiryDate validation fails on input change then show error`() {
        // Given
        val testExpiryDate = "121"
        viewModel.componentState.expiryDate.value = testExpiryDate
        val smartExpiryDateValidationRequest = SmartExpiryDateValidationRequest(true, testExpiryDate)

        every {
            mockSmartExpiryDateValidationUseCase.execute(eq(smartExpiryDateValidationRequest))
        } returns ValidationResult.Failure(CheckoutError(ValidationError.EXPIRY_DATE_IN_PAST))

        // When
        viewModel.onExpiryDateInputChange(testExpiryDate)

        // Then
        verify(exactly = 1) { mockSmartExpiryDateValidationUseCase.execute(eq(smartExpiryDateValidationRequest)) }
        with(viewModel.componentState.inputState) {
            Assertions.assertTrue(inputFieldState.isError.value)
            Assertions.assertTrue(errorState.isVisible.value)
            Assertions.assertEquals(errorState.textId.value, R.string.cko_base_invalid_past_expiry_date_error)
        }
    }

    @Test
    fun `when validateExpiryDate validation succeeds on input change then hide error and update expiry date max length`() {
        // Given
        val testExpiryDate = "234"
        viewModel.componentState.expiryDate.value = testExpiryDate
        val smartExpiryDateValidationRequest = SmartExpiryDateValidationRequest(true, testExpiryDate)

        every {
            mockSmartExpiryDateValidationUseCase.execute(eq(smartExpiryDateValidationRequest))
        } returns ValidationResult.Success(testExpiryDate)
        // Show error from the start to check that it will be hidden.
        viewModel.componentState.showError(R.string.cko_base_invalid_past_expiry_date_error)

        // When
        viewModel.onExpiryDateInputChange(testExpiryDate)

        // Then
        verify(exactly = 1) { mockSmartExpiryDateValidationUseCase.execute(eq(smartExpiryDateValidationRequest)) }
        with(viewModel.componentState.inputState) {
            Assertions.assertFalse(inputFieldState.isError.value)
            Assertions.assertFalse(errorState.isVisible.value)
        }
        Assertions.assertEquals(viewModel.componentState.expiryDate.value, testExpiryDate)
        Assertions.assertEquals(viewModel.componentState.expiryDateMaxLength.value, EXPIRY_DATE_MAXIMUM_LENGTH_THREE)
    }

    @ParameterizedTest(
        name = "When on expiry date change invoked with {0} then {1} validation (maxLength = {2}) set to field state"
    )
    @MethodSource("onTextChangedArguments")
    fun `When expiryDate change called with valid input then expected text & maxLength should be set to componentState`(
        enteredText: String,
        expectedText: String,
        maxLength: Int
    ) {
        // Given
        val smartExpiryDateValidationRequest = SmartExpiryDateValidationRequest(true, enteredText)
        every {
            mockSmartExpiryDateValidationUseCase.execute(eq(smartExpiryDateValidationRequest))
        } returns ValidationResult.Success(enteredText)

        // When
        viewModel.onExpiryDateInputChange(enteredText)

        // Then
        Assertions.assertEquals(viewModel.componentState.expiryDateMaxLength.value, maxLength)
        Assertions.assertEquals(viewModel.componentState.expiryDate.value, expectedText)
    }

    companion object {
        @JvmStatic
        fun onTextChangedArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("123", "123", EXPIRY_DATE_MAXIMUM_LENGTH_FOUR),
            Arguments.of("234", "234", EXPIRY_DATE_MAXIMUM_LENGTH_THREE),
            Arguments.of("1254", "1254", EXPIRY_DATE_MAXIMUM_LENGTH_FOUR),
            Arguments.of("1234", "1234", EXPIRY_DATE_MAXIMUM_LENGTH_FOUR),
            Arguments.of("116", "116", EXPIRY_DATE_MAXIMUM_LENGTH_FOUR),
            Arguments.of("223", "223", EXPIRY_DATE_MAXIMUM_LENGTH_THREE),
            Arguments.of("223", "223", EXPIRY_DATE_MAXIMUM_LENGTH_THREE),
            Arguments.of("445", "445", EXPIRY_DATE_MAXIMUM_LENGTH_THREE),
            Arguments.of("333", "333", EXPIRY_DATE_MAXIMUM_LENGTH_THREE),
            Arguments.of("1187", "1187", EXPIRY_DATE_MAXIMUM_LENGTH_FOUR)
        )
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
            imageMapper,
            TextLabelStyleToStateMapper(imageMapper)
        )
    }
}
