package com.checkout.frames.di.component

import com.checkout.frames.component.cardholdername.CardHolderNameViewModel
import com.checkout.frames.style.component.CardHolderNameComponentStyle
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
internal interface CardHolderNameViewModelSubComponent {
    val cardHolderNameViewModel: CardHolderNameViewModel

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun style(style: CardHolderNameComponentStyle): Builder

        fun build(): CardHolderNameViewModelSubComponent
    }
}
