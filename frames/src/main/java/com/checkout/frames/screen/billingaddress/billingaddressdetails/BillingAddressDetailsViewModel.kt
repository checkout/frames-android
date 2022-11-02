package com.checkout.frames.screen.billingaddress.billingaddressdetails

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.Country
import com.checkout.base.usecase.UseCase
import com.checkout.frames.R
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentState
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.BillingFormViewModelSubComponent
import com.checkout.frames.mapper.ImageStyleToDynamicComposableImageMapper
import com.checkout.frames.model.request.ImageStyleToDynamicImageRequest
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentsContainerState
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.style.view.BillingAddressInputComponentViewStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.style.view.billingformdetails.BillingAddressInputComponentsViewContainerStyle
import com.checkout.frames.utils.extensions.getErrorMessage
import com.checkout.frames.utils.extensions.saveBillingAddressDetails
import com.checkout.frames.utils.extensions.updateAddressFieldText
import com.checkout.frames.view.InternalButtonState
import com.checkout.frames.view.TextLabelState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
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
    val screenTitleStyle = textLabelStyleMapper.map(style.headerComponentStyle.headerTitleStyle)
    val screenTitleState = provideScreenTitleState(style.headerComponentStyle.headerTitleStyle)
    val screenModifier = containerMapper.map(style.containerStyle)

    val screenButtonState = provideButtonState()
    val screenButtonStyle = buttonStyleMapper.map(style.headerComponentStyle.headerButtonStyle)

    val goBack = mutableStateOf(false)

    val inputComponentsContainerModifier =
        containerMapper.map(style.inputComponentsContainerStyle.containerStyle)

    val inputComponentsStateList = provideInputComponentStateList(style)

    val inputComponentViewStyleList = provideInputComponentViewStyleList(style)

    private var wasFocused = false

    internal companion object {
        val onlyDigitsRegex = "[^0-9]".toRegex()
    }

    init {
        viewModelScope.launch {
            isReadyToSaveAddress().collect {
                screenButtonState.isEnabled.value = it
            }
        }

        inputComponentsStateList.forEachIndexed { index, billingAddressInputComponentState ->
            with(inputComponentsStateList[index]) {
                addressFieldText.value = paymentStateManager.billingAddress.value
                    .updateAddressFieldText(billingAddressInputComponentState.addressFieldName)
                if (addressFieldText.value.isNotBlank() && !isInputFieldOptional) {
                    isAddressFieldValid.value = true
                }
            }
        }
    }

    private fun isReadyToSaveAddress(): StateFlow<Boolean> = combine(
        inputComponentsStateList.map { it.isAddressFieldValid }
    ) { values -> values.all { it } }.stateIn(MainScope(), SharingStarted.Lazily, false)

    private fun provideInputComponentViewStyleList(
        billingAddressDetailsStyle: BillingAddressDetailsStyle
    ): List<BillingAddressInputComponentViewStyle> {
        return billingAddressDetailsComponentStyleUseCase
            .execute(billingAddressDetailsStyle)
            .inputComponentViewStyleList
    }

    @Suppress("MagicNumber")
    private fun provideInputComponentStateList(
        billingAddressDetailsStyle: BillingAddressDetailsStyle
    ): List<BillingAddressInputComponentState> {
        val inputComponentStateList = billingAddressDetailsComponentStateUseCase
            .execute(billingAddressDetailsStyle)
            .inputComponentStateList

        // Update phone number max validation length to make compatible it with API requirements
        inputComponentStateList.forEachIndexed { index, billingAddressInputComponentState ->
            if (billingAddressInputComponentState.addressFieldName == BillingFormFields.Phone.name) {
                inputComponentStateList[index].inputComponentState =
                    inputComponentStateList[index].inputComponentState.copy(
                        inputFieldState = inputComponentStateList[index].inputComponentState.inputFieldState.copy(
                            maxLength = mutableStateOf(18)
                        )
                    )
            } else if (billingAddressInputComponentState.addressFieldName == BillingFormFields.Country.name) {
                billingAddressInputComponentState.isAddressFieldValid.value = true
            }
        }

        return inputComponentStateList
    }

    fun onFocusChanged(position: Int, isFocused: Boolean) {
        if (isFocused) wasFocused = isFocused

        with(inputComponentsStateList[position]) {
            // Show error for previously focussed field if text value is empty and is not optional
            if (!isFocused && wasFocused && !isAddressFieldValid.value) showError(getErrorMessage())
        }
    }

    fun onAddressFieldTextChange(position: Int, text: String) {
        val inputComponentsState = inputComponentsStateList[position]

        val changedTextValue = if (
            inputComponentsState.addressFieldName == BillingFormFields.Phone.name
        ) text.replace(onlyDigitsRegex, "") else text

        inputComponentsState.addressFieldText.value = changedTextValue.trim()
        inputComponentsState.isAddressFieldValid.value =
            changedTextValue.isNotBlank() || inputComponentsState.isInputFieldOptional

        inputComponentsState.hideError()
    }

    private fun provideButtonState() = buttonStateMapper.map(style.headerComponentStyle.headerButtonStyle)

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
        updateBillingAddress()
        goBack.value = true
    }

    private fun updateBillingAddress() {
        val country = paymentStateManager.billingAddress.value.address?.country ?: Country.INVALID_COUNTRY
        paymentStateManager.billingAddress.value = inputComponentsStateList.saveBillingAddressDetails(country)
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
