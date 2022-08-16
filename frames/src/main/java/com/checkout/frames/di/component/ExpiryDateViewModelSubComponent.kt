package com.checkout.frames.di.component

import com.checkout.frames.component.expirydate.ExpiryDateViewModel
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
internal interface ExpiryDateViewModelSubComponent {
    val expiryDateViewModel: ExpiryDateViewModel

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun style(style: ExpiryDateComponentStyle): Builder

        fun build(): ExpiryDateViewModelSubComponent
    }
}
