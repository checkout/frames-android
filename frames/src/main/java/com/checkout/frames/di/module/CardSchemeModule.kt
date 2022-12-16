package com.checkout.frames.di.module

import androidx.compose.ui.Modifier
import com.checkout.base.mapper.Mapper
import com.checkout.frames.component.cardscheme.CardSchemeComponentState
import com.checkout.frames.mapper.CardSchemeComponentStyleToStateMapper
import com.checkout.frames.mapper.CardSchemeComponentStyleToViewStyleMapper
import com.checkout.frames.style.component.CardSchemeComponentStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.view.CardSchemeComponentViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.view.TextLabelState
import dagger.Module
import dagger.Provides

@Module
internal class CardSchemeModule {
    companion object {
        @Provides
        fun provideCardSchemeComponentStyleMapper(
            textLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>,
            containerMapper: Mapper<ContainerStyle, Modifier>
        ): Mapper<CardSchemeComponentStyle, CardSchemeComponentViewStyle> =
            CardSchemeComponentStyleToViewStyleMapper(textLabelStyleMapper, containerMapper)

        @Provides
        fun provideCardSchemeComponentStateMapper(
            textLabelMapper: Mapper<TextLabelStyle?, TextLabelState>
        ): Mapper<CardSchemeComponentStyle, CardSchemeComponentState> =
            CardSchemeComponentStyleToStateMapper(textLabelMapper)
    }
}
