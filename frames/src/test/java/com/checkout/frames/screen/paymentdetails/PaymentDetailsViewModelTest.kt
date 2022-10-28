package com.checkout.frames.screen.paymentdetails

import android.annotation.SuppressLint
import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.provider.ComponentProvider
import com.checkout.frames.mapper.ImageStyleToClickableComposableImageMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.screen.PaymentDetailsStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.view.TextLabelState
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class PaymentDetailsViewModelTest {

    @RelaxedMockK
    lateinit var mockComponentProvider: ComponentProvider

    @RelaxedMockK
    lateinit var mockClosePaymentFlowUseCase: UseCase<Unit, Unit>

    @SpyK
    lateinit var spyTextLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>

    @SpyK
    lateinit var spyTextLabelStateMapper: Mapper<TextLabelStyle?, TextLabelState>

    @SpyK
    lateinit var spyClickableImageStyleMapper: ImageStyleToClickableComposableImageMapper

    private val style: PaymentDetailsStyle = PaymentDetailsStyle()
    private lateinit var viewModel: PaymentDetailsViewModel

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        viewModel = PaymentDetailsViewModel(
            mockComponentProvider,
            spyTextLabelStyleMapper,
            spyTextLabelStateMapper,
            spyClickableImageStyleMapper,
            mockClosePaymentFlowUseCase,
            style
        )
    }

    /** Initial state tests **/

    @Test
    fun `when view model is initialised then button style is mapped to initial button state`() =
        with(style.paymentDetailsHeaderStyle) {
            // Then
            verify(exactly = 1) {
                spyTextLabelStyleMapper.map(
                    eq(TextLabelStyle(text, textId, textStyle, containerStyle = containerStyle))
                )
            }
        }

    /** Initial style tests **/

    @Test
    fun `when view model is initialised then button style is mapped to initial button view style`() =
        with(style.paymentDetailsHeaderStyle) {
            // Then
            verify(exactly = 1) {
                spyTextLabelStateMapper.map(
                    eq(TextLabelStyle(text, textId, textStyle, containerStyle = containerStyle))
                )
            }
        }

    private fun initMappers() {
        spyTextLabelStyleMapper = TextLabelStyleToViewStyleMapper(ContainerStyleToModifierMapper())
        spyTextLabelStateMapper = TextLabelStyleToStateMapper(ImageStyleToComposableImageMapper())
        spyClickableImageStyleMapper = ImageStyleToClickableComposableImageMapper()
    }
}
