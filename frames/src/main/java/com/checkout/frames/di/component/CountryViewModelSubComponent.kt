package com.checkout.frames.di.component

import com.checkout.frames.component.country.CountryViewModel
import com.checkout.frames.style.component.CountryComponentStyle
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
internal interface CountryViewModelSubComponent {
    val countryViewModel: CountryViewModel

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun style(style: CountryComponentStyle): Builder

        fun build(): CountryViewModelSubComponent
    }
}
