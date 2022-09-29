package com.checkout.frames.component.cardscheme

import android.annotation.SuppressLint
import com.checkout.base.mapper.Mapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.screen.manager.PaymentFormStateManager
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.CardSchemeComponentStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.view.TextLabelState
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class CardSchemeViewModelTest {

    @SpyK
    lateinit var spyTextLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>

    @SpyK
    lateinit var spyTextLabelStateMapper: Mapper<TextLabelStyle?, TextLabelState>

    @SpyK
    lateinit var spyImageStyleToComposableImageMapper: ImageStyleToComposableImageMapper

    @SpyK
    var spyPaymentStateManager: PaymentStateManager = PaymentFormStateManager(listOf())

    private var style: CardSchemeComponentStyle = CardSchemeComponentStyle()

    private lateinit var viewModel: CardSchemeViewModel

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        viewModel = CardSchemeViewModel(
            spyPaymentStateManager,
            spyTextLabelStyleMapper,
            spyTextLabelStateMapper,
            spyImageStyleToComposableImageMapper,
            style
        )
    }

    /** Initial state tests **/

    @Test
    fun `when view model is initialised then component style is mapped to initial component state`() {
        // Then
        verify { spyTextLabelStateMapper.map(style.titleStyle) }
    }

    @Test
    fun `when view model is initialised then initial state has empty value for text label`() {
        // Then
        viewModel.componentState.textLabelState?.text?.value?.isEmpty()?.let { Assertions.assertTrue(it) }
    }

    /** Initial style tests **/

    @Test
    fun `when view model is initialised then component style is mapped to initial component view style`() {
        // Then
        verify { spyTextLabelStyleMapper.map(style.titleStyle) }
    }

    @Test
    fun `when componentSupportedCardSchemeIcons is requested then supportedCardSchemeList & imageMapper invoked`() {
        // Then
        verify(exactly = 1) { spyPaymentStateManager.supportedCardSchemeList }
        verify(exactly = viewModel.componentSupportedCardSchemeIcons.size) {
            spyImageStyleToComposableImageMapper.map(any())
        }
    }

    private fun initMappers() {
        val containerMapper = ContainerStyleToModifierMapper()

        spyImageStyleToComposableImageMapper = ImageStyleToComposableImageMapper()
        spyTextLabelStyleMapper = TextLabelStyleToViewStyleMapper(containerMapper)
        spyTextLabelStateMapper = TextLabelStyleToStateMapper(spyImageStyleToComposableImageMapper)
    }
}
