package com.checkout.frames.cvvcomponent.viewmodel

import android.annotation.SuppressLint
import androidx.compose.ui.text.input.KeyboardType
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.CardScheme
import com.checkout.frames.cvvcomponent.models.CVVComponentConfig
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.InputFieldStyleToViewStyleMapper
import com.checkout.frames.mapper.InputFieldStyleToInputFieldStateMapper
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.view.InputFieldState
import com.checkout.validation.api.CVVComponentValidator
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
internal class CvvComponentViewModelTest {

    @RelaxedMockK
    private lateinit var cvvComponentValidator: CVVComponentValidator

    @SpyK
    private lateinit var spyInputFieldStyleMapper: Mapper<InputFieldStyle, InputFieldViewStyle>

    @SpyK
    private lateinit var spyInputFieldStateMapper: Mapper<InputFieldStyle, InputFieldState>

    private lateinit var viewModel: CVVComponentViewModel

    private val dispatcher = StandardTestDispatcher()

    private lateinit var cvvComponentConfig: CVVComponentConfig

    init {
        initMappers()
        initConfiguration()
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        viewModel = CVVComponentViewModel(
            spyInputFieldStateMapper, spyInputFieldStyleMapper, cvvComponentConfig, cvvComponentValidator
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
        verify { spyInputFieldStateMapper.map(cvvComponentConfig.cvvInputFieldStyle) }
    }

    @Test
    fun `when view model is initialised then initial state has empty cvv text`() {
        // Then
        assertTrue(viewModel.cvvInputFieldState.text.value.isEmpty())
    }

    /** Initial style tests **/

    @Test
    fun `when view model is initialised then CVVInputField style is mapped to initial CVVInputField view style`() {
        // Then
        verify { spyInputFieldStyleMapper.map(cvvComponentConfig.cvvInputFieldStyle) }
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
            viewModel.cvvInputFieldStyle.keyboardOptions.keyboardType, KeyboardType.Number
        )
    }

    private fun initMappers() {
        val containerMapper = ContainerStyleToModifierMapper()

        spyInputFieldStyleMapper = InputFieldStyleToViewStyleMapper(
            TextLabelStyleToViewStyleMapper(
                containerMapper
            )
        )
        spyInputFieldStateMapper = InputFieldStyleToInputFieldStateMapper(ImageStyleToComposableImageMapper())
    }

    private fun initConfiguration() {
        cvvComponentConfig = CVVComponentConfig(CardScheme.VISA, { }, InputFieldStyle())
    }
}
