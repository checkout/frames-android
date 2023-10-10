package com.checkout.frames.cvvinputfield.viewmodel

import android.annotation.SuppressLint
import androidx.compose.ui.text.input.KeyboardType
import com.checkout.base.error.CheckoutError
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.CardScheme
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.InputFieldStyleToInputFieldStateMapper
import com.checkout.frames.mapper.InputFieldStyleToViewStyleMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.view.InputFieldState
import com.checkout.validation.api.CVVComponentValidator
import com.checkout.validation.model.ValidationResult
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.internal.assertEquals
import org.amshove.kluent.internal.assertFalse
import org.junit.After
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class CVVInputFieldViewModelTest {

    @RelaxedMockK
    private lateinit var mockCVVComponentValidator: CVVComponentValidator

    @SpyK
    private lateinit var spyInputFieldStyleMapper: Mapper<InputFieldStyle, InputFieldViewStyle>

    @SpyK
    private lateinit var spyInputFieldStateMapper: Mapper<InputFieldStyle, InputFieldState>

    private lateinit var viewModel: CVVInputFieldViewModel

    private val dispatcher = StandardTestDispatcher()

    @RelaxedMockK
    private lateinit var cvvComponentConfig: CVVComponentConfig

    init {
        initMappers()
        initConfiguration()
    }

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        every { cvvComponentConfig.cardScheme } returns CardScheme.VISA
        every { cvvComponentConfig.cvvInputFieldStyle } returns InputFieldStyle()

        viewModel = CVVInputFieldViewModel(
            spyInputFieldStateMapper, spyInputFieldStyleMapper, cvvComponentConfig, mockCVVComponentValidator,
        )
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    /** Initial state tests **/

    @Test
    fun `when view model is initialised then CVVInputField style is mapped to initial CVVInputField state`() {
        // Then
        verify { spyInputFieldStateMapper.map(any()) }
    }

    @Test
    fun `when view model is initialised then initial state has empty cvv text`() {
        // Then
        assertTrue(viewModel.cvvInputFieldState.cvv.value.isEmpty())
    }

    @Test
    fun `when view model is initialised then initial state has max cvv length updated as per card scheme from the cvvComponentConfig`() {
        // Then
        assertEquals(cvvComponentConfig.cardScheme.cvvLength.max(), viewModel.cvvInputFieldState.cvvLength.value)
    }

    /** Initial style tests **/

    @Test
    fun `when view model is initialised then CVVInputField style is mapped to initial CVVInputField view style`() {
        // Then
        verify { spyInputFieldStyleMapper.map(any()) }
    }

    @Test
    fun `when view model is initialised then initial style has not forced LTR`() {
        // Then
        assertFalse(viewModel.cvvInputFieldStyle.forceLTR)
    }

    @Test
    fun `when view model is initialised then initial style has correct keyboard type`() {
        // Then
        assertEquals(
            viewModel.cvvInputFieldStyle.keyboardOptions.keyboardType,
            KeyboardType.Number,
        )
    }

    /** Validation related tests **/
    @Test
    fun `when on CvvChange triggered then cvv validation is invoked and cvv value updated in cvvInputFieldState`() {
        // Given
        val testCvv = "123"
        val testCardScheme = CardScheme.VISA

        every {
            mockCVVComponentValidator.validate(eq(testCvv), eq(testCardScheme))
        } returns ValidationResult.Success(Unit)

        // When
        viewModel.onCvvChange(testCvv)

        // Then
        verify(exactly = 1) { mockCVVComponentValidator.validate(eq(testCvv), eq(testCardScheme)) }
        assertEquals(viewModel.cvvInputFieldState.cvv.value, testCvv)
    }

    @Test
    fun `when input cvv is valid then onCvvChange should call onCVVValueChange with true`() {
        // Given
        val text = "123"
        val testCardScheme = CardScheme.VISA
        every {
            mockCVVComponentValidator.validate(eq(text), eq(testCardScheme))
        } returns ValidationResult.Success(Unit)

        // When
        viewModel.onCvvChange(text)

        // Then
        verify { cvvComponentConfig.onCVVValueChange(true) }
    }

    @Test
    fun `when input cvv is invalid then onCvvChange should call onCVVValueChange with true`() {
        // Given
        val text = "1"
        val testCardScheme = CardScheme.VISA
        every {
            mockCVVComponentValidator.validate(eq(text), eq(testCardScheme))
        } returns ValidationResult.Failure(CheckoutError("Test Error code"))

        // When
        viewModel.onCvvChange(text)

        // Then
        verify { cvvComponentConfig.onCVVValueChange(false) }
    }

    @Test
    fun `when input cvv is updating from invalid to valid value then onCvvChange should call onCVVValueChange with true`() {
        // Given
        val invalidCVV = "1"
        val validCVV = "134"
        val testCardScheme = CardScheme.VISA
        every {
            mockCVVComponentValidator.validate(eq(invalidCVV), eq(testCardScheme))
        } returns ValidationResult.Failure(CheckoutError("Test Error code"))

        every {
            mockCVVComponentValidator.validate(eq(validCVV), eq(testCardScheme))
        } returns ValidationResult.Success(Unit)

        // When
        viewModel.onCvvChange(invalidCVV)
        viewModel.onCvvChange(validCVV)

        // Then
        verify { cvvComponentConfig.onCVVValueChange(true) }
    }

    @Test
    fun `when input cvv is valid for Maestro scheme with zero length then onCvvChange should call onCVVValueChange with false`() {
        // Given
        val validCVV = ""
        val testCardScheme = CardScheme.MAESTRO
        every { cvvComponentConfig.cardScheme } returns CardScheme.MAESTRO
        every {
            mockCVVComponentValidator.validate(eq(validCVV), eq(testCardScheme))
        } returns ValidationResult.Failure(CheckoutError("Test Error code"))

        // When
        viewModel.onCvvChange(validCVV)

        // Then
        verify { cvvComponentConfig.onCVVValueChange(false) }
    }

    @Test
    fun `when input cvv is updating from valid to invalid value onCvvChange should call onCVVValueChange with true`() {
        // Given
        val invalidCVV = "1"
        val validCVV = "134"
        val testCardScheme = CardScheme.VISA
        every {
            mockCVVComponentValidator.validate(eq(invalidCVV), eq(testCardScheme))
        } returns ValidationResult.Failure(CheckoutError("Test Error code"))

        every {
            mockCVVComponentValidator.validate(eq(validCVV), eq(testCardScheme))
        } returns ValidationResult.Success(Unit)

        // When
        viewModel.onCvvChange(validCVV)
        viewModel.onCvvChange(invalidCVV)

        // Then
        verify { cvvComponentConfig.onCVVValueChange(false) }
    }

    private fun initMappers() {
        val containerMapper = ContainerStyleToModifierMapper()
        spyInputFieldStyleMapper = InputFieldStyleToViewStyleMapper(
            TextLabelStyleToViewStyleMapper(
                containerMapper,
            ),
        )
        spyInputFieldStateMapper = InputFieldStyleToInputFieldStateMapper(ImageStyleToComposableImageMapper())
    }

    private fun initConfiguration() {
        cvvComponentConfig = CVVComponentConfig(CardScheme.VISA, { }, InputFieldStyle())
    }
}
