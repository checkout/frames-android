package com.checkout.frames.di.module

import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentState
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentsContainerState
import com.checkout.frames.mapper.billingaddressdetails.BillingAddressInputComponentStyleToStateMapper
import com.checkout.frames.mapper.billingaddressdetails.BillingAddressInputComponentStyleToViewStyleMapper
import com.checkout.frames.screen.billingaddress.usecase.BillingAddressInputComponentStateUseCase
import com.checkout.frames.screen.billingaddress.usecase.BillingAddressInputComponentStyleUseCase
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.style.view.BillingAddressInputComponentViewStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.style.view.billingformdetails.BillingAddressInputComponentsViewContainerStyle
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
            inputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>,
        ): Mapper<BillingAddressInputComponentStyle, BillingAddressInputComponentState> =
            BillingAddressInputComponentStyleToStateMapper(inputComponentStateMapper)

        @Provides
        fun provideBillingAddressInputComponentsStateUseCase(
            billingAddressInputComponentStateMapper:
            Mapper<BillingAddressInputComponentStyle, BillingAddressInputComponentState>,
        ): UseCase<BillingAddressDetailsStyle, BillingAddressInputComponentsContainerState> =
            BillingAddressInputComponentStateUseCase(billingAddressInputComponentStateMapper)

        @Provides
        fun provideBillingAddressInputComponentsStyleUseCase(
            billingAddressInputComponentStyleMapper:
            Mapper<BillingAddressInputComponentStyle, BillingAddressInputComponentViewStyle>,
        ): UseCase<BillingAddressDetailsStyle, BillingAddressInputComponentsViewContainerStyle> =
            BillingAddressInputComponentStyleUseCase(billingAddressInputComponentStyleMapper)
    }
}
