package com.checkout.frames.di.module

import com.checkout.CardValidatorFactory
import com.checkout.frames.di.component.CardNumberViewModelSubComponent
import com.checkout.validation.api.CardValidator
import dagger.Module
import dagger.Provides

@Module(subcomponents = [CardNumberViewModelSubComponent::class])
internal abstract class ValidationModule {

    companion object {
        @Provides
        fun provideCardValidator(): CardValidator = CardValidatorFactory.createInternal()
    }
}
