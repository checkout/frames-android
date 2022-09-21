package com.checkout.frames.component.cvv

import android.annotation.SuppressLint
import androidx.compose.ui.text.input.KeyboardType
import com.checkout.base.error.CheckoutError
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.CardScheme
import com.checkout.frames.R
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.InputComponentStyleToViewStyleMapper
import com.checkout.frames.mapper.InputFieldStyleToViewStyleMapper
import com.checkout.frames.mapper.InputFieldStyleToInputFieldStateMapper
import com.checkout.frames.mapper.InputComponentStyleToStateMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.screen.manager.PaymentFormStateManager
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.CvvComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.validation.api.CardValidator
import com.checkout.validation.model.ValidationResult
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.internal.assertEquals
import org.amshove.kluent.internal.assertFalse
import org.junit.After
import org.junit.jupiter.api.Assertions.assertTrue

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@ExperimentalCoroutinesApi
@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class CvvViewModelTest {

    @RelaxedMockK
    lateinit var mockCardValidator: CardValidator

    @SpyK
    lateinit var spyInputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>

    @SpyK
    lateinit var spyInputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>

    @SpyK
    var spyPaymentStateManager: PaymentStateManager = PaymentFormStateManager()

    private var style: CvvComponentStyle = CvvComponentStyle()

    private lateinit var viewModel: CvvViewModel

    private val dispatcher = StandardTestDispatcher()

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        viewModel = CvvViewModel(
            spyPaymentStateManager,
            mockCardValidator,
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

    @Test
    fun `when view model is initialised then initial state has empty card number and unknown card scheme`() {
        // Then
        assertTrue(viewModel.componentState.cvv.value.isEmpty())
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
        assertEquals(
            viewModel.componentStyle.inputFieldStyle.keyboardOptions.keyboardType,
            KeyboardType.Number
        )
    }

    /** Payment state related tests **/

    @Test
    fun `when cvv updated then cvv state in payment state manager is updated`() {
        // Given
        val testCvv = "123"
        val testCardScheme = CardScheme.AMERICAN_EXPRESS
        spyPaymentStateManager.cardScheme.value = testCardScheme

        // When
        viewModel.onCvvChange(testCvv)

        // Then
        assertEquals(spyPaymentStateManager.cvv.value, testCvv)
    }

    @Test
    fun `when focus change triggered more than once then cvv state in payment state manager is updated`() {
        // Given
        val testCvv = "123"
        val testCardScheme = CardScheme.JCB
        viewModel.componentState.cvv.value = testCvv
        spyPaymentStateManager.cardScheme.value = testCardScheme
        every {
            mockCardValidator.validateCvv(eq(testCvv), eq(testCardScheme))
        } returns ValidationResult.Success(Unit)

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // Then
        assertTrue(spyPaymentStateManager.isCvvValid.value)
    }

    /** Input data filtering **/

    @Test
    fun `when cvv with non digits entered then non digit symbols are removed`() {
        // Given
        val sourceInput = "1.2"
        val filteredInput = "12"

        // When
        viewModel.onCvvChange(sourceInput)

        // Then
        assertEquals(viewModel.componentState.cvv.value, filteredInput)
    }

    /** Max length related tests **/

    @Test
    fun `when card scheme state in payment state manager is changed then cvv max length is updated`() = runTest {
        // Given
        val testCardScheme = CardScheme.VISA
        viewModel.prepare()

        // When
        spyPaymentStateManager.cardScheme.value = testCardScheme

        // Then
        launch {
            assertEquals(testCardScheme.cvvLength.max(), viewModel.componentState.cvvLength.value)
        }
    }

    /** Validation related tests **/

    @Test
    fun `when focus change triggered for the first time then cvv validation is not invoked`() {
        // When
        viewModel.onFocusChanged(false)

        // Then
        verify(exactly = 0) { mockCardValidator.validateCvv(any(), any()) }
    }

    @Test
    fun `when focus change triggered more than once then cvv validation is invoked`() {
        // Given
        val testCvv = "1234"
        val testCardScheme = CardScheme.AMERICAN_EXPRESS
        viewModel.componentState.cvv.value = testCvv
        spyPaymentStateManager.cardScheme.value = testCardScheme
        every {
            mockCardValidator.validateCvv(eq(testCvv), eq(testCardScheme))
        } returns ValidationResult.Success(Unit)

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // Then
        verify(exactly = 1) { mockCardValidator.validateCvv(eq(testCvv), eq(testCardScheme)) }
    }

    @Test
    fun `when cvv is valid on focus change then error is hidden`() {
        // Given
        val testCvv = "1234"
        val testCardScheme = CardScheme.AMERICAN_EXPRESS
        viewModel.componentState.cvv.value = testCvv
        spyPaymentStateManager.cardScheme.value = testCardScheme
        every {
            mockCardValidator.validateCvv(eq(testCvv), eq(testCardScheme))
        } returns ValidationResult.Success(Unit)

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // Then
        verify(exactly = 1) { mockCardValidator.validateCvv(eq(testCvv), eq(testCardScheme)) }
        with(viewModel.componentState.inputState) {
            assertFalse(inputFieldState.isError.value)
            assertFalse(errorState.isVisible.value)
        }
    }

    @Test
    fun `when cvv is invalid on focus change then error is shown`() {
        // Given
        val testCvv = "123"
        val testCardScheme = CardScheme.AMERICAN_EXPRESS
        viewModel.componentState.cvv.value = testCvv
        spyPaymentStateManager.cardScheme.value = testCardScheme
        every {
            mockCardValidator.validateCvv(eq(testCvv), eq(testCardScheme))
        } returns ValidationResult.Failure(CheckoutError(""))

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)

        // Then
        verify(exactly = 1) { mockCardValidator.validateCvv(eq(testCvv), eq(testCardScheme)) }
        with(viewModel.componentState.inputState) {
            assertTrue(inputFieldState.isError.value)
            assertTrue(errorState.isVisible.value)
            assertEquals(errorState.textId.value, R.string.cko_cvv_component_error)
        }
    }

    @Test
    fun `when card scheme state in payment state manager is changed but field wasn't focused before then cvv validation is not invoked`() =
        runTest {
            // Given
            val testCardScheme = CardScheme.VISA
            viewModel.prepare()

            // When
            spyPaymentStateManager.cardScheme.value = testCardScheme

            // Then
            launch {
                verify(exactly = 0) { mockCardValidator.validateCvv(any(), any()) }
            }
        }

    @ParameterizedTest(
        name = "When card scheme is updated to {0} then {1} max length is set for cvv and validation invoked"
    )
    @MethodSource("onCardSchemeChangedArguments")
    fun `when card scheme state in payment state manager is changed then cvv validation is not invoked`(
        cardScheme: CardScheme,
        maxCvvLength: Int
    ) = runTest {
        // Given
        val testCvv = "123"
        every { mockCardValidator.validateCvv(eq(testCvv), any()) } returns ValidationResult.Success(Unit)

        viewModel.componentState.cvv.value = testCvv
        viewModel.prepare()

        // When
        viewModel.onFocusChanged(true)
        viewModel.onFocusChanged(false)
        spyPaymentStateManager.cardScheme.value = cardScheme

        // Then
        launch {
            // Initial validation after focus change
            verify(exactly = 1) { mockCardValidator.validateCvv(eq(testCvv), eq(CardScheme.UNKNOWN)) }
            // Card scheme changed when focused on another field
            verify(exactly = 1) { mockCardValidator.validateCvv(eq(testCvv), eq(cardScheme)) }
            assertEquals(maxCvvLength, viewModel.componentState.cvvLength.value)
        }
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

    companion object {
        @JvmStatic
        fun onCardSchemeChangedArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(CardScheme.AMERICAN_EXPRESS, CardScheme.AMERICAN_EXPRESS.cvvLength.max()),
            Arguments.of(CardScheme.DINERS_CLUB, CardScheme.DINERS_CLUB.cvvLength.max()),
            Arguments.of(CardScheme.DISCOVER, CardScheme.DISCOVER.cvvLength.max()),
            Arguments.of(CardScheme.JCB, CardScheme.JCB.cvvLength.max()),
            Arguments.of(CardScheme.MADA, CardScheme.MADA.cvvLength.max()),
            Arguments.of(CardScheme.MAESTRO, CardScheme.MAESTRO.cvvLength.max()),
            Arguments.of(CardScheme.MASTERCARD, CardScheme.MASTERCARD.cvvLength.max()),
            Arguments.of(CardScheme.VISA, CardScheme.VISA.cvvLength.max())
        )
    }
}
