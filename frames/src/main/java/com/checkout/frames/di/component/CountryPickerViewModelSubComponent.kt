package com.checkout.frames.di.component

import com.checkout.frames.screen.countrypicker.CountryPickerViewModel
import com.checkout.frames.style.screen.CountryPickerStyle
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
internal interface CountryPickerViewModelSubComponent {
    val countryPickerViewModel: CountryPickerViewModel

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun style(style: CountryPickerStyle): Builder

        fun build(): CountryPickerViewModelSubComponent
    }
}
