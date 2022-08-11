package com.checkout.frames.di.component

import com.checkout.frames.component.cardnumber.CardNumberViewModel
import com.checkout.frames.style.component.InputComponentStyle
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
internal interface CardNumberViewModelSubComponent {
    val cardNumberViewModel: CardNumberViewModel

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun style(style: InputComponentStyle): Builder

        fun build(): CardNumberViewModelSubComponent
    }
}
