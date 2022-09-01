package com.checkout.frames.di.module

import com.checkout.CardValidatorFactory
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.expirydate.model.SmartLogicExpiryDateRequest
import com.checkout.frames.component.expirydate.usecase.SmartLogicExpiryDateUseCase
import com.checkout.frames.di.component.CardNumberViewModelSubComponent
import com.checkout.frames.di.component.ExpiryDateViewModelSubComponent
import com.checkout.validation.api.CardValidator
import com.checkout.validation.model.ValidationResult
import dagger.Module
import dagger.Provides

@Module(subcomponents = [CardNumberViewModelSubComponent::class, ExpiryDateViewModelSubComponent::class])
internal abstract class ValidationModule {

    companion object {
        @Provides
        fun provideCardValidator(): CardValidator = CardValidatorFactory.create()

        @Provides
        fun provideValidateExpiryDateUseCase(cardValidator: CardValidator):
                UseCase<SmartLogicExpiryDateRequest, ValidationResult<String>> =
            SmartLogicExpiryDateUseCase(cardValidator)
    }
}
