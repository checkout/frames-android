package com.checkout.frames.component.cardholdername

import android.annotation.SuppressLint
import com.checkout.base.mapper.Mapper
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.InputComponentStyleToViewStyleMapper
import com.checkout.frames.mapper.InputFieldStyleToViewStyleMapper
import com.checkout.frames.mapper.InputComponentStyleToStateMapper
import com.checkout.frames.mapper.InputFieldStyleToInputFieldStateMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.style.component.CardHolderNameComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
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
internal class CardHolderNameViewModelTest {

    @SpyK
    lateinit var spyInputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>

    @SpyK
    lateinit var spyInputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>

    private var style: CardHolderNameComponentStyle = CardHolderNameComponentStyle()

    private lateinit var viewModel: CardHolderNameViewModel

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        viewModel = CardHolderNameViewModel(
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
    fun `when view model is initialised then initial state has empty card holder name`() {
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

    /** Input data filtering **/

    @Test
    fun `when card holder name with special characters and digits entered then these symbols are removed`() {
        // Given
        val sourceInput = "Denny@123"
        val filteredInput = "Denny"

        // When
        viewModel.onCardHolderNameChange(sourceInput)

        // Then
        assertEquals(viewModel.componentState.cardHolderName.value, filteredInput)
    }

    @ParameterizedTest(
        name = "When on card holder name change invoked with {0} then {1} set to text field state"
    )
    @MethodSource("onTextChangedArguments")
    fun `When on card holder name change invoked with a string then cleaned string should be set to a text field state`(
        enteredText: String,
        cleanedText: String,
    ) {
        // When
        viewModel.onCardHolderNameChange(enteredText)

        // Then
        assertEquals(viewModel.componentState.cardHolderName.value, cleanedText)
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

    companion object {
        @JvmStatic
        fun onTextChangedArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("TestName_£Charles", "TestNameCharles"),
            Arguments.of("TestName's Charles123 ", "TestName's Charles"),
            Arguments.of("TestName31343443243424324 ", "TestName"),
            Arguments.of("TestName's %$3%Charles", "TestName's Charles"),
            Arguments.of("TestName's&^$£$$$%% %$3%Charles", "TestName's Charles")
        )
    }
}
