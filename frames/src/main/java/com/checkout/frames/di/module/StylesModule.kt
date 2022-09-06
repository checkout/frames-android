package com.checkout.frames.di.module

import androidx.compose.ui.Modifier
import com.checkout.base.mapper.Mapper
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.di.component.CardNumberViewModelSubComponent
import com.checkout.frames.di.component.CvvViewModelSubComponent
import com.checkout.frames.di.screen.PaymentDetailsViewModelSubComponent
import com.checkout.frames.di.component.ExpiryDateViewModelSubComponent
import com.checkout.frames.mapper.InputFieldStyleToViewStyleMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.ImageStyleToDynamicComposableImageMapper
import com.checkout.frames.mapper.InputComponentStyleToStateMapper
import com.checkout.frames.mapper.InputComponentStyleToViewStyleMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.view.TextLabelState
import dagger.Module
import dagger.Provides

@Module(
    subcomponents = [
        CardNumberViewModelSubComponent::class,
        CvvViewModelSubComponent::class,
        ExpiryDateViewModelSubComponent::class,
        CvvViewModelSubComponent::class,
        PaymentDetailsViewModelSubComponent::class
    ]
)
internal abstract class StylesModule {

    companion object {
        @Provides
        fun provideContainerStyleToModifierMapper(): Mapper<ContainerStyle, Modifier> =
            ContainerStyleToModifierMapper()

        @Provides
        fun provideImageStyleToComposableImageMapper(): ImageStyleToComposableImageMapper =
            ImageStyleToComposableImageMapper()

        @Provides
        fun provideImageStyleToDynamicComposableImageMapper(): ImageStyleToDynamicComposableImageMapper =
            ImageStyleToDynamicComposableImageMapper()

        @Provides
        fun provideTextLabelStyleToStateMapper(
            imageMapper: ImageStyleToComposableImageMapper
        ): Mapper<TextLabelStyle?, TextLabelState> = TextLabelStyleToStateMapper(imageMapper)

        @Provides
        fun provideInputComponentStyleToStateMapper(
            imageMapper: ImageStyleToComposableImageMapper,
            textLabelMapper: Mapper<TextLabelStyle?, TextLabelState>
        ): Mapper<InputComponentStyle, InputComponentState> =
            InputComponentStyleToStateMapper(imageMapper, textLabelMapper)

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
    }
}
