package com.checkout.frames.component.cardnumber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.mapper.Mapper
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.CardNumberViewModelSubComponent
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.validation.api.CardValidator
import javax.inject.Inject
import javax.inject.Provider

// TODO: PIMOB-1349 - Payment form component: card number
@SuppressWarnings("UnusedPrivateMember")
internal class CardNumberViewModel @Inject constructor(
    private val cardValidator: CardValidator,
    private val inputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>,
    private val inputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>,
    private val style: CardNumberComponentStyle
) : ViewModel() {

    val viewStyle = inputComponentStyleMapper.map(style.inputStyle)
    val viewState = inputComponentStateMapper.map(style.inputStyle)

    internal class Factory(
        private val injector: Injector,
        private val style: CardNumberComponentStyle
    ) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var subComponentProvider: Provider<CardNumberViewModelSubComponent.Builder>

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector.inject(this)
            return subComponentProvider.get()
                .style(style)
                .build().cardNumberViewModel as T
        }
    }
}
