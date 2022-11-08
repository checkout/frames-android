package com.checkout.frames.component.paybutton

import android.annotation.SuppressLint
import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.ButtonStyleToInternalViewStyleMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.ButtonStyleToInternalStateMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.screen.manager.PaymentFormStateManager
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.PayButtonComponentStyle
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.paymentflow.InternalCardTokenRequest
import com.checkout.frames.view.InternalButtonState
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class PayButtonViewModelTest {

    @RelaxedMockK
    lateinit var mockCardTokenizationUseCase: UseCase<InternalCardTokenRequest, Unit>

    @SpyK
    lateinit var spyButtonStyleMapper: Mapper<ButtonStyle, InternalButtonViewStyle>

    @SpyK
    lateinit var spyButtonStateMapper: Mapper<ButtonStyle, InternalButtonState>

    @SpyK
    var spyPaymentStateManager: PaymentStateManager = PaymentFormStateManager(emptyList())

    private val dispatcher = StandardTestDispatcher()
    private val style: PayButtonComponentStyle = PayButtonComponentStyle(ButtonStyle())

    private lateinit var viewModel: PayButtonViewModel

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        viewModel = PayButtonViewModel(
            style, spyPaymentStateManager, mockCardTokenizationUseCase,
            spyButtonStyleMapper, spyButtonStateMapper
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
}
