package com.checkout.frames.screen.countrypicker

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.mapper.Mapper
import com.checkout.base.model.Country
import com.checkout.frames.R
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.CountryPickerViewModelSubComponent
import com.checkout.frames.mapper.ImageStyleToDynamicComposableImageMapper
import com.checkout.frames.model.request.ImageStyleToDynamicImageRequest
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.screen.CountryPickerStyle
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.utils.extensions.emojiFlag
import com.checkout.frames.view.InputFieldState
import com.checkout.frames.view.TextLabelState
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject
import javax.inject.Provider

internal class CountryPickerViewModel @Inject constructor(
    private val paymentStateManager: PaymentStateManager,
    private val inputFieldStyleMapper: Mapper<InputFieldStyle, InputFieldViewStyle>,
    private val inputFieldStateMapper: Mapper<InputFieldStyle, InputFieldState>,
    private val textLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>,
    private val textLabelStateMapper: Mapper<TextLabelStyle?, TextLabelState>,
    private val containerMapper: Mapper<ContainerStyle, Modifier>,
    private val imageMapper: ImageStyleToDynamicComposableImageMapper,
    private val style: CountryPickerStyle
) : ViewModel() {

    val screenTitleStyle = textLabelStyleMapper.map(style.screenTitleStyle)
    val screenTitleState = provideScreenTitleState(style.screenTitleStyle)
    val searchFieldStyle = inputFieldStyleMapper.map(style.searchFieldStyle)
    val searchFieldState = provideSearchFieldState(style.searchFieldStyle)
    val searchItemStyle = textLabelStyleMapper.map(style.searchItemStyle)
    val screenModifier = containerMapper.map(style.containerStyle)

    private val allCountries: List<CountryItem> = provideAllCountryItems()
    val searchCountries = mutableStateOf(allCountries)

    val isSearchActive = mutableStateOf(false)
    val goBack = mutableStateOf(false)

    private var isInputFocused = false

    fun onFocusChanged(isFocused: Boolean) {
        isInputFocused = isFocused
        if (isInputFocused) isSearchActive.value = true
    }

    fun onSearchChange(text: String) {
        searchFieldState.text.value = text
        searchCountries.value =
            if (text.isEmpty()) allCountries else allCountries.filter { it.name.contains(text, true) }
    }

    fun onCountryChosen(iso2: String) {
        paymentStateManager.billingAddress.value.address?.country = Country.from(iso2)
        goBack.value = true
    }

    @VisibleForTesting
    fun onReset() {
        isSearchActive.value = false
        onSearchChange("")
    }

    @VisibleForTesting
    fun onClear() {
        if (isInputFocused) onSearchChange("")
        else onReset()
    }

    private fun provideScreenTitleState(style: TextLabelStyle): TextLabelState {
        val state = textLabelStateMapper.map(style)

        state.textId.value = R.string.cko_country_picker_screen_title
        state.leadingIcon.value = imageMapper.map(
            ImageStyleToDynamicImageRequest(
                style.leadingIconStyle,
                flowOf(R.drawable.cko_ic_cross_close),
                flowOf { goBack.value = true }
            )
        )

        return state
    }

    private fun provideSearchFieldState(style: InputFieldStyle): InputFieldState {
        val viewState = inputFieldStateMapper.map(style)

        viewState.leadingIcon.value = imageMapper.map(
            ImageStyleToDynamicImageRequest(
                style.leadingIconStyle,
                snapshotFlow { isSearchActive.value }.map {
                    if (it) R.drawable.cko_ic_arrow_back
                    else R.drawable.cko_ic_search
                },
                snapshotFlow { isSearchActive.value }.map {
                    if (it) this::onReset else null
                }
            )
        )

        viewState.trailingIcon.value = imageMapper.map(
            ImageStyleToDynamicImageRequest(
                style.trailingIconStyle,
                snapshotFlow { viewState.text.value }.map {
                    if (it.isEmpty()) null else R.drawable.cko_ic_cross_clear
                },
                flowOf { onClear() }
            )
        )

        return viewState
    }

    private fun provideAllCountryItems(): List<CountryItem> = Country.values().toList().map {
        CountryItem(
            name = Locale(Locale.getDefault().language, it.iso3166Alpha2).displayCountry,
            emojiFlag = it.emojiFlag(),
            iso2 = it.iso3166Alpha2
        )
    }

    internal class Factory(
        private val injector: Injector,
        private val style: CountryPickerStyle
    ) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var subComponentProvider: Provider<CountryPickerViewModelSubComponent.Builder>

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector.inject(this)
            return subComponentProvider.get()
                .style(style)
                .build().countryPickerViewModel as T
        }
    }
}
