package com.checkout.frames.di.component

import com.checkout.frames.component.cardnumber.CardNumberViewModel
import com.checkout.frames.style.component.CardNumberComponentStyle
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
internal interface CardNumberViewModelSubComponent {
    val cardNumberViewModel: CardNumberViewModel

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun style(style: CardNumberComponentStyle): Builder

        fun build(): CardNumberViewModelSubComponent
    }
}
