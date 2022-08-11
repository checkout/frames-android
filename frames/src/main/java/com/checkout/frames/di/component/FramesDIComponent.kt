package com.checkout.frames.di.component

import com.checkout.frames.component.cardnumber.CardNumberViewModel
import com.checkout.frames.di.module.ValidationModule
import com.checkout.frames.di.module.StylesModule
import dagger.Component

@Component(modules = [ValidationModule::class, StylesModule::class])
internal abstract class FramesDIComponent {

    abstract fun inject(factory: CardNumberViewModel.Factory)

    @Component.Builder
    interface Builder {
        fun build(): FramesDIComponent
    }
}
