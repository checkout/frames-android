package com.checkout.frames.di.component

import com.checkout.frames.component.paybutton.PayButtonViewModel
import com.checkout.frames.style.component.PayButtonComponentStyle
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
internal interface PayButtonViewModelSubComponent {
    val payButtonViewModel: PayButtonViewModel

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun style(style: PayButtonComponentStyle): Builder

        fun build(): PayButtonViewModelSubComponent
    }
}
