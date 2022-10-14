package com.checkout.frames.di.module

import androidx.compose.ui.Modifier
import com.checkout.base.mapper.Mapper
import com.checkout.frames.component.addresssummary.AddressSummaryComponentState
import com.checkout.frames.mapper.DividerStyleToViewStyleMapper
import com.checkout.frames.mapper.addresssummary.AddressSummaryComponentStyleToStateMapper
import com.checkout.frames.mapper.addresssummary.AddressSummaryComponentStyleToViewStyleMapper
import com.checkout.frames.mapper.addresssummary.AddressSummarySectionStyleToViewStyleMapper
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import com.checkout.frames.style.component.addresssummary.AddressSummarySectionStyle
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.DividerStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.view.DividerViewStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.style.view.addresssummary.AddressSummaryComponentViewStyle
import com.checkout.frames.style.view.addresssummary.AddressSummarySectionViewStyle
import com.checkout.frames.view.InternalButtonState
import com.checkout.frames.view.TextLabelState
import dagger.Module
import dagger.Provides

@Module
internal class AddressSummaryModule {

    companion object {
        @Provides
        fun provideDividerMapper(): Mapper<DividerStyle, DividerViewStyle> = DividerStyleToViewStyleMapper()

        @Provides
        fun provideAddressSummarySectionStyleMapper(
            textLabelMapper: Mapper<TextLabelStyle, TextLabelViewStyle>,
            dividerMapper: Mapper<DividerStyle, DividerViewStyle>,
            buttonMapper: Mapper<ButtonStyle, InternalButtonViewStyle>,
            containerMapper: Mapper<ContainerStyle, Modifier>
        ): Mapper<AddressSummarySectionStyle, AddressSummarySectionViewStyle> =
            AddressSummarySectionStyleToViewStyleMapper(
                textLabelMapper,
                dividerMapper,
                buttonMapper,
                containerMapper
            )

        @Provides
        fun provideAddressSummaryComponentStyleMapper(
            textLabelMapper: Mapper<TextLabelStyle, TextLabelViewStyle>,
            buttonMapper: Mapper<ButtonStyle, InternalButtonViewStyle>,
            summarySectionMapper: Mapper<AddressSummarySectionStyle, AddressSummarySectionViewStyle>,
            containerMapper: Mapper<ContainerStyle, Modifier>
        ): Mapper<AddressSummaryComponentStyle, AddressSummaryComponentViewStyle> =
            AddressSummaryComponentStyleToViewStyleMapper(
                textLabelMapper,
                buttonMapper,
                summarySectionMapper,
                containerMapper
            )

        @Provides
        fun provideAddressSummaryComponentStateMapper(
            textLabelStateMapper: Mapper<TextLabelStyle?, TextLabelState>,
            internalButtonStateMapper: Mapper<ButtonStyle, InternalButtonState>
        ): Mapper<AddressSummaryComponentStyle, AddressSummaryComponentState> =
            AddressSummaryComponentStyleToStateMapper(
                textLabelStateMapper,
                internalButtonStateMapper
            )
    }
}
