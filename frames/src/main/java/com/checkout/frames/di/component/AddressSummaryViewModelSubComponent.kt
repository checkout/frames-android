package com.checkout.frames.di.component

import com.checkout.frames.component.addresssummary.AddressSummaryViewModel
import com.checkout.frames.di.module.AddressSummaryModule
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [AddressSummaryModule::class])
internal interface AddressSummaryViewModelSubComponent {
    val addressSummaryViewModel: AddressSummaryViewModel

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun style(style: AddressSummaryComponentStyle): Builder

        fun build(): AddressSummaryViewModelSubComponent
    }
}
