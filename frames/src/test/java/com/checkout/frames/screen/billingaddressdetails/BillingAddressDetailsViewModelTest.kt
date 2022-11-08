package com.checkout.frames.screen.billingaddressdetails

import android.annotation.SuppressLint
import androidx.compose.ui.Modifier
import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentsContainerState
import com.checkout.frames.mapper.*
import com.checkout.frames.screen.billingaddress.billingaddressdetails.BillingAddressDetailsViewModel
import com.checkout.frames.screen.manager.PaymentFormStateManager
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.style.view.billingformdetails.BillingAddressInputComponentsViewContainerStyle
import com.checkout.frames.view.InternalButtonState
import com.checkout.frames.view.TextLabelState
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@SuppressLint("NewApi")
@ExtendWith(MockKExtension::class)
internal class BillingAddressDetailsViewModelTest {

    @RelaxedMockK
    lateinit var mockBillingAddressDetailsComponentStateUseCase: UseCase<BillingAddressDetailsStyle, BillingAddressInputComponentsContainerState>

    @RelaxedMockK
    lateinit var mockBillingAddressDetailsComponentStyleUseCase: UseCase<BillingAddressDetailsStyle, BillingAddressInputComponentsViewContainerStyle>

    @SpyK
    var spyPaymentStateManager: PaymentStateManager = PaymentFormStateManager(emptyList())

    @SpyK
    lateinit var spyTextLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>

    @SpyK
    lateinit var spyContainerMapper: Mapper<ContainerStyle, Modifier>

    @SpyK
    lateinit var spyTextLabelStateMapper: Mapper<TextLabelStyle?, TextLabelState>

    @SpyK
    lateinit var spyButtonStyleMapper: Mapper<ButtonStyle, InternalButtonViewStyle>

    @SpyK
    lateinit var spyButtonStateMapper: Mapper<ButtonStyle, InternalButtonState>

    @SpyK
    lateinit var spyImageMapper: ImageStyleToDynamicComposableImageMapper

    private val style: BillingAddressDetailsStyle = BillingAddressDetailsStyle()

    private lateinit var viewModel: BillingAddressDetailsViewModel

    private val dispatcher = StandardTestDispatcher()

    init {
        initMappers()
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        viewModel = BillingAddressDetailsViewModel(
            spyPaymentStateManager,
            spyTextLabelStyleMapper,
            spyTextLabelStateMapper,
            spyContainerMapper,
            spyImageMapper,
            mockBillingAddressDetailsComponentStateUseCase,
            mockBillingAddressDetailsComponentStyleUseCase,
            spyButtonStyleMapper,
            spyButtonStateMapper,
            style
        )
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    /** Initial state tests **/
    @Test
    fun `when view model is initialised then header button style is mapped to initial button state`() =
        // Then
        verify(exactly = 1) {
            spyButtonStateMapper.map(
                style.headerComponentStyle.headerButtonStyle
            )
        }

//    @Test
//    fun `when view model is initialised then header title style is mapped to initial header title state`() =
//        // Then
//        verify(exactly = 1) {
//            spyTextLabelStateMapper.map(
//                style.headerComponentStyle.headerTitleStyle
//            )
//        }
//
//
//    @Test
//    fun `when view model is initialised then initial button isEnabled value has correct value`() {
//        // Then
//        Assertions.assertEquals(
//            viewModel.screenButtonState.isEnabled,
//            false
//        )
//    }
//
//    /** Initial style tests **/
//    @Test
//    fun `when view model is initialised then header button style is mapped to initial button style`() =
//        // Then
//        verify(exactly = 1) {
//            spyButtonStyleMapper.map(
//                style.headerComponentStyle.headerButtonStyle
//            )
//        }
//
//    @Test
//    fun `when view model is initialised then header title style is mapped to initial header title style`() =
//        // Then
//        verify(exactly = 1) {
//            spyTextLabelStyleMapper.map(
//                style.headerComponentStyle.headerTitleStyle
//            )
//        }
//
//    @Test
//    fun `when view model is initialised then inputComponents container style is mapped to inputComponents modifier`() {
//        // Then
//        verify { spyContainerMapper.map(style.inputComponentsContainerStyle.containerStyle) }
//    }
//
//    @Test
//    fun `when view model is initialised then screen container style is mapped to screen modifier`() {
//        // Then
//        verify { spyContainerMapper.map(style.containerStyle) }
//    }

    ///** Functionality **/


    private fun initMappers() {
        spyImageMapper = ImageStyleToDynamicComposableImageMapper()
        spyTextLabelStyleMapper = TextLabelStyleToViewStyleMapper(ContainerStyleToModifierMapper())
        spyTextLabelStateMapper = TextLabelStyleToStateMapper(ImageStyleToComposableImageMapper())
        spyContainerMapper = ContainerStyleToModifierMapper()
        spyButtonStateMapper = ButtonStyleToInternalStateMapper(spyTextLabelStateMapper)
        spyButtonStyleMapper = ButtonStyleToInternalViewStyleMapper(spyContainerMapper, spyTextLabelStyleMapper)
    }
}
