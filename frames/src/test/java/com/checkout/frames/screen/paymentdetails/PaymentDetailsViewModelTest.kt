package com.checkout.frames.screen.paymentdetails

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.Country
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.provider.ComponentProvider
import com.checkout.frames.logging.PaymentFormEventType
import com.checkout.frames.mapper.ImageStyleToClickableComposableImageMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mock.PaymentFormConfigTestData
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress.Companion.isEdited
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.default.DefaultCardHolderNameComponentStyle
import com.checkout.frames.style.screen.PaymentDetailsStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.utils.extensions.isValid
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
import org.amshove.kluent.internal.assertNotEquals
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
        every { mockPaymentStateManager.billingAddress.value } returns BillingAddress()
        every { mockPaymentStateManager.cardHolderName.value } returns ""

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

    @Test
    fun `when resetCountrySelection is invoked then SelectedCountry should be updated from the billing address`() {
        // Given
        val givenSelectedCountry = Country.UKRAINE
        mockPaymentStateManager.selectedCountry.value = givenSelectedCountry
        mockPaymentStateManager.billingAddress.value = BillingAddress(
            address = PaymentFormConfigTestData.address,
            name = "Test name",
            phone = PaymentFormConfigTestData.phone
        )

        // When
        viewModel.resetCountrySelection()

        // Then
        assertNotEquals(givenSelectedCountry, mockPaymentStateManager.selectedCountry.value)
    }

    @Test
    fun `when view model is initialised then provideIsBillingAddressValid function should provide correct values`() {
        // Given
        val expectedIsBillingAddressEdited = true
        val expectedIsBillingAddressValid = true
        val expectedIsBillingAddressValidByDefault = true
        every { mockPaymentStateManager.billingAddress.value } returns BillingAddress(
            address = PaymentFormConfigTestData.address,
            name = "Test name",
            phone = PaymentFormConfigTestData.phone
        )

        // When
        initViewModel(style)

        // Then
        assertEquals(expectedIsBillingAddressEdited, mockPaymentStateManager.billingAddress.value.isEdited())
        assertEquals(expectedIsBillingAddressValid, mockPaymentStateManager.billingAddress.value.isValid())
        assertEquals(true, style.addressSummaryStyle != null)
        assertEquals(expectedIsBillingAddressValidByDefault, viewModel.provideIsBillingAddressValid())
    }

    @Test
    fun `when view model is initialised with default style then provideIsCardHolderNameValid function should provide correct values`() {
        // Given
        val expectedIsCardHolderNameNotBlank = true
        val expectedIsCardHolderNameValidByDefault = true
        every { mockPaymentStateManager.cardHolderName.value } returns "Test name"

        // When
        initViewModel(style)

        // Then
        assertEquals(expectedIsCardHolderNameNotBlank, mockPaymentStateManager.cardHolderName.value.isNotBlank())
        assertEquals(true, style.cardHolderNameStyle != null)
        assertEquals(expectedIsCardHolderNameValidByDefault, viewModel.provideIsCardHolderNameValid())
    }

    @Test
    fun `when view model is initialised with mandatory cardHolderName style then provideIsCardHolderNameValid function should provide correct values`() {
        // Given
        val expectedIsCardHolderNameNotBlank = false
        val expectedIsCardHolderNameValidByDefault = false
        every { mockPaymentStateManager.cardHolderName.value } returns ""
        val style = PaymentDetailsStyle().apply {
            cardHolderNameStyle = DefaultCardHolderNameComponentStyle.light(isOptional = false)
        }

        // When
        initViewModel(style)

        // Then
        assertEquals(expectedIsCardHolderNameNotBlank, mockPaymentStateManager.cardHolderName.value.isNotBlank())
        assertEquals(true, style.cardHolderNameStyle != null)
        assertEquals(expectedIsCardHolderNameValidByDefault, viewModel.provideIsCardHolderNameValid())
    }

    @ParameterizedTest(
        name = "When view model initialised with: cvvComponentStyle is null = {0}, " +
                "CardHolderNameComponentStyle is null = {1}, \"CardHolderNameComponentStyle is optional = {2} " +
                "addressSummaryComponentStyle is null = {3} " + "and addressSummaryComponentStyle is optional = {4}; " +
                "Then payment state is reset with: " +
                "isCvvValid = {5} isBillingAddressValid = {6} and isCardHolderNameValid = {7}"
    )
    @MethodSource("resetArguments")
    fun `when view model is initialised then payment state is reset with correct values`(
        isCvvStyleNull: Boolean,
        isCardHolderNameStyleNull: Boolean,
        isCardHolderNameOptional: Boolean,
        isAddressStyleNull: Boolean,
        isAddressOptional: Boolean,
        isCvvValid: Boolean,
        isBillingAddressValid: Boolean,
        isCardHolderNameValid: Boolean
    ) {
        // Given
        val style = PaymentDetailsStyle().apply {
            if (isCvvStyleNull) cvvStyle = null

            cardHolderNameStyle = if (isCardHolderNameStyleNull) null
            else DefaultCardHolderNameComponentStyle.light(isOptional = isCardHolderNameOptional)

            addressSummaryStyle =
                if (isAddressStyleNull) null else addressSummaryStyle?.copy(isOptional = isAddressOptional)
        }

        // When
        initViewModel(style)

        // Then
        val isBillingAddressEnabled = !isAddressStyleNull
        verify {
            mockPaymentStateManager.resetPaymentState(
                isCvvValid, isCardHolderNameValid, isBillingAddressValid, isBillingAddressEnabled
            )
        }
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
            Arguments.of(false, false, false, false, false, false, false, false),
            Arguments.of(true, true, true, false, false, true, false, true),
            Arguments.of(true, false, true, true, false, true, true, true),
            Arguments.of(true, true, false, false, true, true, true, true)
        )
    }
}
