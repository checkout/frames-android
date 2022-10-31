package com.checkout.frames.screen.billingaddress.billingaddressdetails

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.R
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.BillingFormViewModelSubComponent
import com.checkout.frames.mapper.ImageStyleToDynamicComposableImageMapper
import com.checkout.frames.model.request.ImageStyleToDynamicImageRequest
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentsContainerState
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
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Provider

@Suppress("LongParameterList", "UnusedPrivateMember")
internal class BillingAddressDetailsViewModel @Inject constructor(
    private val paymentStateManager: PaymentStateManager,
    private val textLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>,
    private val textLabelStateMapper: Mapper<TextLabelStyle?, TextLabelState>,
    private val containerMapper: Mapper<ContainerStyle, Modifier>,
    private val imageMapper: ImageStyleToDynamicComposableImageMapper,
    private val billingAddressDetailsComponentStateUseCase:
    UseCase<BillingAddressDetailsStyle, BillingAddressInputComponentsContainerState>,
    private val billingAddressDetailsComponentStyleUseCase:
    UseCase<BillingAddressDetailsStyle, BillingAddressInputComponentsViewContainerStyle>,
    private val buttonStyleMapper: Mapper<ButtonStyle, InternalButtonViewStyle>,
    private val buttonStateMapper: Mapper<ButtonStyle, InternalButtonState>,
    private val style: BillingAddressDetailsStyle
) : ViewModel() {
    // Header styling
    val screenTitleStyle = textLabelStyleMapper.map(style.headerComponentStyle.headerTitleStyle)
    val screenTitleState = provideScreenTitleState(style.headerComponentStyle.headerTitleStyle)
    val screenModifier = containerMapper.map(style.containerStyle)

    val screenButtonState = provideButtonState()
    val screenButtonStyle = buttonStyleMapper.map(style.headerComponentStyle.headerButtonStyle)

    val goBack = mutableStateOf(false)

    val inputComponentsContainerModifier =
        containerMapper.map(style.inputComponentsContainerStyle.containerStyle)

    val inputComponentsState = provideBillingAddressInputComponentsState(
        style
    )
    val inputComponentsViewContainerStyle = provideBillingAddressInputComponentsViewContainerStyle(
        style
    )

    private fun provideBillingAddressInputComponentsViewContainerStyle(
        billingAddressDetailsStyle: BillingAddressDetailsStyle
    ): BillingAddressInputComponentsViewContainerStyle {
        return billingAddressDetailsComponentStyleUseCase.execute(billingAddressDetailsStyle)
    }

    private fun provideBillingAddressInputComponentsState(billingAddressDetailsStyle: BillingAddressDetailsStyle):
            BillingAddressInputComponentsContainerState {
        return billingAddressDetailsComponentStateUseCase.execute(
            billingAddressDetailsStyle
        )
    }

    fun onFocusChanged(isFocused: Boolean) {
        Log.d("", "" + isFocused)
    }

    fun onSearchChange(text: String) {
        Log.d("", "" + text)
    }

    private fun provideButtonState(): InternalButtonState {
        val state = buttonStateMapper.map(style.headerComponentStyle.headerButtonStyle)
        state.textState.textId.value = R.string.cko_billing_form_button_save
        return state
    }

    private fun provideScreenTitleState(style: TextLabelStyle): TextLabelState {
        val state = textLabelStateMapper.map(style)
        state.textId.value = R.string.cko_billing_form_screen_title
        state.leadingIcon.value = imageMapper.map(
            ImageStyleToDynamicImageRequest(
                style.leadingIconStyle,
                flowOf(R.drawable.cko_ic_cross_close),
                flowOf { goBack.value = true }
            )
        )

        return state
    }

    fun onTapDoneButton() {
        goBack.value = true
    }

    internal class Factory(
        private val injector: Injector,
        private val style: BillingAddressDetailsStyle
    ) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var subComponentProvider: Provider<BillingFormViewModelSubComponent.Builder>

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector.inject(this)
            return subComponentProvider.get()
                .style(style)
                .build().billingAddressDetailsViewModel as T
        }
    }
}
