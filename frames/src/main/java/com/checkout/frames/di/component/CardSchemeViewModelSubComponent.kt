package com.checkout.frames.di.component

import com.checkout.frames.component.cardscheme.CardSchemeViewModel
import com.checkout.frames.style.component.CardSchemeComponentStyle
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
internal interface CardSchemeViewModelSubComponent {
    val cardSchemeViewModel: CardSchemeViewModel

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun style(style: CardSchemeComponentStyle): Builder

        fun build(): CardSchemeViewModelSubComponent
    }
}
