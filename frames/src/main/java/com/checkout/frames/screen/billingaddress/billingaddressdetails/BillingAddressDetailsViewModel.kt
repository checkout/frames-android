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
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentsContainerStyle
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.style.view.BillingAddressInputComponentViewStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
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
    UseCase<List<BillingAddressInputComponentStyle>, BillingAddressInputComponentsContainerState>,
    private val billingAddressDetailsComponentStyleUseCase:
    UseCase<List<BillingAddressInputComponentStyle>, List<BillingAddressInputComponentViewStyle>>,
    private val buttonStyleMapper: Mapper<ButtonStyle, InternalButtonViewStyle>,
    private val buttonStateMapper: Mapper<ButtonStyle, InternalButtonState>,
    private val style: BillingAddressDetailsStyle
) : ViewModel() {
    // Header styling
    val screenTitleStyle = textLabelStyleMapper.map(style.billingAddressHeaderComponentStyle.headerTitleStyle)
    val screenTitleState = provideScreenTitleState(style.billingAddressHeaderComponentStyle.headerTitleStyle)
    val screenModifier = containerMapper.map(style.containerStyle)

    val screenButtonState = provideButtonState()
    val screenButtonStyle = buttonStyleMapper.map(style.billingAddressHeaderComponentStyle.headerButtonStyle)

    val goBack = mutableStateOf(false)

    val billingAddressInputComponentsContainerModifier =
        containerMapper.map(style.billingAddressInputComponentsContainerStyle.containerStyle)

    val billingAddressInputComponentState = provideBillingAddressInputComponentsState(
        style.billingAddressInputComponentsContainerStyle
    )
    val billingAddressInputComponentsStyle = provideBillingAddressInputComponentsStyle(
        style.billingAddressInputComponentsContainerStyle
    )

    private fun provideBillingAddressInputComponentsStyle(
        billingAddressInputComponentsContainerStyle: BillingAddressInputComponentsContainerStyle
    ): List<BillingAddressInputComponentViewStyle> {
        return billingAddressDetailsComponentStyleUseCase.execute(
            billingAddressInputComponentsContainerStyle.billingAddressInputComponentStyleList
        )
    }

    private fun provideBillingAddressInputComponentsState(
        billingAddressInputComponentsContainerStyle:
        BillingAddressInputComponentsContainerStyle
    ): BillingAddressInputComponentsContainerState {
        return billingAddressDetailsComponentStateUseCase.execute(
            billingAddressInputComponentsContainerStyle.billingAddressInputComponentStyleList
        )
    }

    fun onFocusChanged(isFocused: Boolean) {
        Log.d("", "" + isFocused)
    }

    fun onSearchChange(text: String) {
        Log.d("", "" + text)
    }

    private fun provideButtonState(): InternalButtonState {
        val state = buttonStateMapper.map(style.billingAddressHeaderComponentStyle.headerButtonStyle)
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
