package com.checkout.frames.screen.paymentdetails

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.provider.ComponentProvider
import com.checkout.frames.logging.PaymentFormEventType
import com.checkout.frames.mapper.ImageStyleToClickableComposableImageMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.screen.PaymentDetailsStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.view.TextLabelState
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.internal.assertEquals

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
internal class PaymentDetailsViewModelTest {

    @RelaxedMockK
    lateinit var mockComponentProvider: ComponentProvider

    @RelaxedMockK
    lateinit var mockClosePaymentFlowUseCase: UseCase<Unit, Unit>

    @RelaxedMockK
    lateinit var mockPaymentStateManager: PaymentStateManager

    @RelaxedMockK
    lateinit var mockLogger: Logger<LoggingEvent>

    @SpyK
    lateinit var spyTextLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>

    @SpyK
    lateinit var spyTextLabelStateMapper: Mapper<TextLabelStyle?, TextLabelState>

    @SpyK
    lateinit var spyContainerMapper: Mapper<ContainerStyle, Modifier>

    @SpyK
    lateinit var spyClickableImageStyleMapper: ImageStyleToClickableComposableImageMapper

    private val capturedEvent = slot<LoggingEvent>()
    private val style: PaymentDetailsStyle = PaymentDetailsStyle()
    private lateinit var viewModel: PaymentDetailsViewModel

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        every { mockLogger.log(capture(capturedEvent)) } returns Unit
        every { mockClosePaymentFlowUseCase.execute(Unit) } returns Unit

        initViewModel(style)
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

    @Test
    fun `when view model is initialised then fields container style is mapped to Modifier`() {
        // Then
        verify(exactly = 1) { spyContainerMapper.map(style.fieldsContainerStyle) }
    }

    /** Functionality **/

    @Test
    fun `when view model is initialised then presented event is logged`() {
        // Then
        assertEquals(PaymentFormEventType.PRESENTED.eventId, capturedEvent.captured.typeIdentifier)
    }

    @Test
    fun `when close invoked then close use case executed and canceled event logged`() {
        // When
        viewModel.onClose()

        // Then
        verify(exactly = 1) { mockClosePaymentFlowUseCase.execute(Unit) }
        assertEquals(PaymentFormEventType.CANCELED.eventId, capturedEvent.captured.typeIdentifier)
    }

    @ParameterizedTest(
        name = "When view model initialised with: " +
                "cvvComponentStyle is null = {0}, " +
                "addressSummaryComponentStyle is null = {1} " +
                "and addressSummaryComponentStyle is optional = {2}; " +
                "Then payment state is reset with: " +
                "isCvvValid = {3} and isBillingAddressValid = {4}"
    )
    @MethodSource("resetArguments")
    fun `when view model is initialised then payment state is reset with correct values`(
        isCvvStyleNull: Boolean,
        isAddressStyleNull: Boolean,
        isAddressOptional: Boolean,
        isCvvValid: Boolean,
        isBillingAddressValid: Boolean
    ) {
        // Given
        val style = PaymentDetailsStyle().apply {
            if (isCvvStyleNull) cvvStyle = null
            addressSummaryStyle =
                if (isAddressStyleNull) null else addressSummaryStyle?.copy(isOptional = isAddressOptional)
        }

        // When
        initViewModel(style)

        // Then
        val isBillingAddressEnabled = !isAddressStyleNull
        verify { mockPaymentStateManager.resetPaymentState(isCvvValid, isBillingAddressValid, isBillingAddressEnabled) }
    }

    private fun initViewModel(style: PaymentDetailsStyle) {
        viewModel = PaymentDetailsViewModel(
            mockComponentProvider,
            spyTextLabelStyleMapper,
            spyTextLabelStateMapper,
            spyContainerMapper,
            spyClickableImageStyleMapper,
            mockClosePaymentFlowUseCase,
            mockPaymentStateManager,
            mockLogger,
            style
        )
    }

    private fun initMappers() {
        spyTextLabelStyleMapper = TextLabelStyleToViewStyleMapper(ContainerStyleToModifierMapper())
        spyTextLabelStateMapper = TextLabelStyleToStateMapper(ImageStyleToComposableImageMapper())
        spyClickableImageStyleMapper = ImageStyleToClickableComposableImageMapper()
        spyContainerMapper = ContainerStyleToModifierMapper()
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.N)
        @JvmStatic
        fun resetArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(false, false, false, false, false),
            Arguments.of(true, false, false, true, false),
            Arguments.of(true, true, false, true, true),
            Arguments.of(true, false, true, true, true)
        )
    }
}
