package com.checkout.frames.di.module

import androidx.compose.ui.Modifier
import com.checkout.base.mapper.Mapper
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.di.component.AddressSummaryViewModelSubComponent
import com.checkout.frames.di.component.CardNumberViewModelSubComponent
import com.checkout.frames.di.component.CountryPickerViewModelSubComponent
import com.checkout.frames.di.component.CountryViewModelSubComponent
import com.checkout.frames.di.component.CvvViewModelSubComponent
import com.checkout.frames.di.component.ExpiryDateViewModelSubComponent
import com.checkout.frames.di.component.PayButtonViewModelSubComponent
import com.checkout.frames.di.screen.PaymentDetailsViewModelSubComponent
import com.checkout.frames.mapper.ButtonStyleToInternalStateMapper
import com.checkout.frames.mapper.ButtonStyleToInternalViewStyleMapper
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.ImageStyleToClickableComposableImageMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.ImageStyleToDynamicComposableImageMapper
import com.checkout.frames.mapper.InputComponentStyleToStateMapper
import com.checkout.frames.mapper.InputComponentStyleToViewStyleMapper
import com.checkout.frames.mapper.InputFieldStyleToInputFieldStateMapper
import com.checkout.frames.mapper.InputFieldStyleToViewStyleMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.view.InputFieldState
import com.checkout.frames.view.InternalButtonState
import com.checkout.frames.view.TextLabelState
import dagger.Module
import dagger.Provides

@Module(
    subcomponents = [
        CardNumberViewModelSubComponent::class,
        ExpiryDateViewModelSubComponent::class,
        CvvViewModelSubComponent::class,
        PaymentDetailsViewModelSubComponent::class,
        CountryViewModelSubComponent::class,
        CountryPickerViewModelSubComponent::class,
        AddressSummaryViewModelSubComponent::class,
        PayButtonViewModelSubComponent::class
    ]
)
internal abstract class StylesModule {

    @SuppressWarnings("TooManyFunctions")
    companion object {
        @Provides
        fun provideContainerStyleToModifierMapper(): Mapper<ContainerStyle, Modifier> =
            ContainerStyleToModifierMapper()

        @Provides
        fun provideImageStyleToComposableImageMapper(): ImageStyleToComposableImageMapper =
            ImageStyleToComposableImageMapper()

        @Provides
        fun provideImageStyleToClickableComposableImageMapper(): ImageStyleToClickableComposableImageMapper =
            ImageStyleToClickableComposableImageMapper()

        @Provides
        fun provideImageStyleToDynamicComposableImageMapper(): ImageStyleToDynamicComposableImageMapper =
            ImageStyleToDynamicComposableImageMapper()

        @Provides
        fun provideTextLabelStyleToStateMapper(
            imageMapper: ImageStyleToComposableImageMapper
        ): Mapper<TextLabelStyle?, TextLabelState> = TextLabelStyleToStateMapper(imageMapper)

        @Provides
        fun provideInputFieldStyleToStateMapper(
            imageMapper: ImageStyleToComposableImageMapper
        ): Mapper<InputFieldStyle, InputFieldState> = InputFieldStyleToInputFieldStateMapper(imageMapper)

        @Provides
        fun provideInputComponentStyleToStateMapper(
            textLabelMapper: Mapper<TextLabelStyle?, TextLabelState>,
            inputFieldStateMapper: Mapper<InputFieldStyle, InputFieldState>
        ): Mapper<InputComponentStyle, InputComponentState> =
            InputComponentStyleToStateMapper(textLabelMapper, inputFieldStateMapper)

        @Provides
        fun provideInputFieldStyleToViewStyleMapper(
            textLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>
        ): Mapper<InputFieldStyle, InputFieldViewStyle> = InputFieldStyleToViewStyleMapper(textLabelStyleMapper)

        @Provides
        fun provideTextLabelStyleToViewStyleMapper(
            containerMapper: Mapper<ContainerStyle, Modifier>
        ): Mapper<TextLabelStyle, TextLabelViewStyle> = TextLabelStyleToViewStyleMapper(containerMapper)

        @Provides
        fun provideInputComponentStyleMapper(
            textLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>,
            inputFieldStyleMapper: Mapper<InputFieldStyle, InputFieldViewStyle>,
            containerMapper: Mapper<ContainerStyle, Modifier>
        ): Mapper<InputComponentStyle, InputComponentViewStyle> =
            InputComponentStyleToViewStyleMapper(textLabelStyleMapper, inputFieldStyleMapper, containerMapper)

        @Provides
        fun provideButtonStyleMapper(
            textLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>,
            containerMapper: Mapper<ContainerStyle, Modifier>
        ): Mapper<ButtonStyle, InternalButtonViewStyle> = ButtonStyleToInternalViewStyleMapper(
            containerMapper, textLabelStyleMapper
        )

        @Provides
        fun provideButtonStateMapper(
            textLabelStateMapper: Mapper<TextLabelStyle?, TextLabelState>
        ): Mapper<ButtonStyle, InternalButtonState> = ButtonStyleToInternalStateMapper(textLabelStateMapper)
    }
}
