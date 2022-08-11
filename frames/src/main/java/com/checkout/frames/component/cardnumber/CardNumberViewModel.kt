package com.checkout.frames.component.cardnumber

import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.mapper.Mapper
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.CardNumberViewModelSubComponent
import com.checkout.frames.style.component.ContainerStyle
import com.checkout.frames.style.component.InputComponentStyle
import com.checkout.validation.api.CardValidator
import javax.inject.Inject
import javax.inject.Provider

// TODO: PIMOB-1349 - Payment form component: card number
@SuppressWarnings("UnusedPrivateMember")
internal class CardNumberViewModel @Inject constructor(
    private val cardValidator: CardValidator,
    private val containerMapper: Mapper<ContainerStyle, Modifier>,
    private val style: InputComponentStyle
) : ViewModel() {

    internal class Factory(
        private val injector: Injector,
        private val style: InputComponentStyle
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
