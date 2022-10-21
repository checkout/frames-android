package com.checkout.frames.di.module

import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentsContainerState
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentState
import com.checkout.frames.mapper.billingaddressdetails.BillingAddressInputComponentStyleToStateMapper
import com.checkout.frames.mapper.billingaddressdetails.BillingAddressInputComponentStyleToViewStyleMapper
import com.checkout.frames.screen.billingaddress.usecase.BillingAddressInputComponentStateUseCase
import com.checkout.frames.screen.billingaddress.usecase.BillingAddressInputComponentStyleUseCase
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle
import com.checkout.frames.style.view.BillingAddressInputComponentViewStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import dagger.Module
import dagger.Provides

@Module
internal class BillingAddressDetailsModule {
    companion object {
        @Provides
        fun provideBillingAddressInputComponentsStyleMapper(
            inputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>,
        ): Mapper<BillingAddressInputComponentStyle, BillingAddressInputComponentViewStyle> =
            BillingAddressInputComponentStyleToViewStyleMapper(inputComponentStyleMapper)

        @Provides
        fun provideBillingAddressInputComponentsStateMapper(
            inputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>
        ): Mapper<BillingAddressInputComponentStyle, BillingAddressInputComponentState> =
            BillingAddressInputComponentStyleToStateMapper(inputComponentStateMapper)

        @Provides
        fun provideBillingAddressInputComponentsStateUseCase(
            test: Mapper<BillingAddressInputComponentStyle, BillingAddressInputComponentState>,
            paymentStateManager: PaymentStateManager
        ): UseCase<List<BillingAddressInputComponentStyle>, BillingAddressInputComponentsContainerState> =
            BillingAddressInputComponentStateUseCase(test, paymentStateManager)

        @Provides
        fun provideBillingAddressInputComponentsStyleUseCase(
            test: Mapper<BillingAddressInputComponentStyle, BillingAddressInputComponentViewStyle>,
            paymentStateManager: PaymentStateManager
        ): UseCase<List<BillingAddressInputComponentStyle>, List<BillingAddressInputComponentViewStyle>> =
            BillingAddressInputComponentStyleUseCase(test, paymentStateManager)
    }
}
