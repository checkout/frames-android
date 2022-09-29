package com.checkout.frames.component.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.checkout.base.mapper.Mapper
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.CountryViewModelSubComponent
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.CountryComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.utils.extensions.emojiFlag
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import javax.inject.Provider

internal class CountryViewModel @Inject constructor(
    private val paymentStateManager: PaymentStateManager,
    private val inputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>,
    private val inputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>,
    style: CountryComponentStyle
) : ViewModel() {

    val componentState = provideViewState(style.inputStyle)
    val componentStyle = provideViewStyle(style.inputStyle)

    fun prepare() {
        viewModelScope.launch {
            paymentStateManager.country.collect {
                val name = Locale(Locale.getDefault().language, it.iso3166Alpha2).displayCountry
                val emojiFlag = it.emojiFlag()
                componentState.inputFieldState.text.value = "$emojiFlag    $name"
            }
        }
    }

    private fun provideViewState(style: InputComponentStyle): InputComponentState =
        inputComponentStateMapper.map(style)

    private fun provideViewStyle(inputStyle: InputComponentStyle): InputComponentViewStyle {
        var viewStyle = inputComponentStyleMapper.map(inputStyle)

        viewStyle = viewStyle.copy(
            inputFieldStyle = viewStyle.inputFieldStyle.copy(
                readOnly = true,
                enabled = false
            )
        )

        return viewStyle
    }

    internal class Factory(
        private val injector: Injector,
        private val style: CountryComponentStyle
    ) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var subComponentProvider: Provider<CountryViewModelSubComponent.Builder>

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector.inject(this)
            return subComponentProvider.get()
                .style(style)
                .build().countryViewModel as T
        }
    }
}
