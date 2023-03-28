package com.checkout.frames.component.paybutton

import android.annotation.SuppressLint
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.Country
import com.checkout.base.usecase.UseCase
import com.checkout.frames.logging.PaymentFormEventType
import com.checkout.frames.mapper.ButtonStyleToInternalStateMapper
import com.checkout.frames.mapper.ButtonStyleToInternalViewStyleMapper
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.model.request.InternalCardTokenRequest
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import com.checkout.frames.screen.manager.PaymentFormStateManager
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.PayButtonComponentStyle
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.view.InternalButtonState
import com.checkout.logging.Logger
import com.checkout.logging.model.LoggingEvent
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.internal.assertEquals
import org.junit.After
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class PayButtonViewModelTest {

    @RelaxedMockK
    private lateinit var mockCardTokenizationUseCase: UseCase<InternalCardTokenRequest, Unit>

    @RelaxedMockK
    private lateinit var mockLogger: Logger<LoggingEvent>

    @SpyK
    private lateinit var spyButtonStyleMapper: Mapper<ButtonStyle, InternalButtonViewStyle>

    @SpyK
    private lateinit var spyButtonStateMapper: Mapper<ButtonStyle, InternalButtonState>

    @SpyK
    private var spyPaymentStateManager: PaymentStateManager = PaymentFormStateManager(emptyList())

    private val dispatcher = StandardTestDispatcher()
    private val capturedEvent = slot<LoggingEvent>()
    private val capturedTokenRequest = slot<InternalCardTokenRequest>()
    private val style: PayButtonComponentStyle = PayButtonComponentStyle(ButtonStyle())

    private lateinit var viewModel: PayButtonViewModel

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        every { mockLogger.log(capture(capturedEvent)) } returns Unit
        every { mockCardTokenizationUseCase.execute(capture(capturedTokenRequest)) } returns Unit

        viewModel = PayButtonViewModel(
            style, spyPaymentStateManager, mockCardTokenizationUseCase,
            spyButtonStyleMapper, spyButtonStateMapper, mockLogger
        )
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    /** Initial state tests **/

    @Test
    fun `when view model is initialised then button style is mapped to initial button state`() {
        // Then
        verify { spyButtonStateMapper.map(style.buttonStyle) }
    }

    @Test
    fun `when view model is initialised then button has disable initial state`() {
        // Then
        assertFalse(viewModel.buttonState.isEnabled.value)
    }

    /** Initial style tests **/

    @Test
    fun `when view model is initialised then button style is mapped to initial button view style`() {
        // Then
        verify { spyButtonStyleMapper.map(style.buttonStyle) }
    }

    /** Button state update **/

    @Test
    fun `when data is updated in state manager then button enable state is updated as well`() = runTest {
        // Given
        viewModel.prepare()

        // When
        spyPaymentStateManager.isCardNumberValid.value = true
        spyPaymentStateManager.isCvvValid.value = true
        spyPaymentStateManager.isExpiryDateValid.value = true
        spyPaymentStateManager.isBillingAddressValid.value = true

        // Then
        testScheduler.advanceUntilIdle()
        assertTrue(viewModel.buttonState.isEnabled.value)
    }

    @Test
    fun `when payment is executed then button is disabled`() = runTest {
        // Given
        viewModel.buttonState.isEnabled.value = true

        // When
        viewModel.pay()

        // Then
        testScheduler.advanceUntilIdle()
        assertFalse(viewModel.buttonState.isEnabled.value)
    }

    @Test
    fun `when payment is executed then submitted event is logged`() {
        // Given
        viewModel.buttonState.isEnabled.value = true

        // When
        viewModel.pay()

        // Then
        assertEquals(PaymentFormEventType.SUBMITTED.eventId, capturedEvent.captured.typeIdentifier)
    }

    @Test
    fun `Card should include address if the billing address is enabled and edited`() = runTest {
        // Given
        spyPaymentStateManager.isBillingAddressEnabled.value = true
        spyPaymentStateManager.billingAddress.value = EDITED_BILLING_ADDRESS

        // When
        viewModel.pay()

        // Then
        testScheduler.advanceUntilIdle()
        with(capturedTokenRequest.captured.card) {
            assertEquals(EDITED_BILLING_ADDRESS.address, billingAddress)
            assertEquals(EDITED_BILLING_ADDRESS.name, name)
            assertEquals(EDITED_BILLING_ADDRESS.phone, phone)
        }
    }

    @Test
    fun `Card should include not include name, phone and address if the billing address is disabled`() = runTest {
        // Given
        spyPaymentStateManager.isBillingAddressEnabled.value = false

        // When
        viewModel.pay()

        // Then
        testScheduler.advanceUntilIdle()
        with(capturedTokenRequest.captured.card) {
            assertNull(billingAddress)
            assertNull(name)
            assertNull(phone)
        }
    }

    @Test
    fun `Card should include not include name, phone and  address if the billing address is not edited`() = runTest {
        // Given
        spyPaymentStateManager.isBillingAddressEnabled.value = true
        spyPaymentStateManager.billingAddress.value = BillingAddress()

        // When
        viewModel.pay()

        // Then
        testScheduler.advanceUntilIdle()
        with(capturedTokenRequest.captured.card) {
            assertNull(billingAddress)
            assertNull(name)
            assertNull(phone)
        }
    }

    private fun initMappers() {
        val containerMapper = ContainerStyleToModifierMapper()

        spyButtonStyleMapper = ButtonStyleToInternalViewStyleMapper(
            containerMapper,
            TextLabelStyleToViewStyleMapper(containerMapper)
        )

        spyButtonStateMapper = ButtonStyleToInternalStateMapper(
            TextLabelStyleToStateMapper(ImageStyleToComposableImageMapper())
        )
    }

    private companion object {
        private val EDITED_BILLING_ADDRESS = BillingAddress(
            name = "NAME",
            address = Address("ADDRESS_LINE_1", "ADDRESS_LINE_2", "", "", "", Country.UNITED_KINGDOM),
            phone = Phone("1234", Country.AFGHANISTAN)
        )
    }
}
