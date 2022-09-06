package com.checkout.frames.di.component

import com.checkout.frames.component.cvv.CvvViewModel
import com.checkout.frames.style.component.CvvComponentStyle
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
internal interface CvvViewModelSubComponent {
    val cvvViewModel: CvvViewModel

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun style(style: CvvComponentStyle): Builder

        fun build(): CvvViewModelSubComponent
    }
}
