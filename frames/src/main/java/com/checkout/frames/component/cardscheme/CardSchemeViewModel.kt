package com.checkout.frames.component.cardscheme

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.mapper.Mapper
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.component.CardSchemeViewModelSubComponent
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.CardSchemeComponentStyle
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.view.CardSchemeComponentViewStyle
import javax.inject.Inject
import javax.inject.Provider

internal class CardSchemeViewModel @Inject constructor(
    private val paymentStateManager: PaymentStateManager,
    private val cardSchemeComponentStyleMapper: Mapper<CardSchemeComponentStyle, CardSchemeComponentViewStyle>,
    private val cardSchemeComponentStateMapper: Mapper<CardSchemeComponentStyle, CardSchemeComponentState>,
    private val imageMapper: ImageStyleToComposableImageMapper,
    private val style: CardSchemeComponentStyle
) : ViewModel() {

    val componentState = provideViewState(style)
    val componentStyle = provideViewStyle(style)
    val componentSupportedCardSchemeIcons = provideCardSchemes()

    private fun provideCardSchemes(): List<@Composable (() -> Unit)?> {
        return provideComposableCardSchemeImages(style.imageStyle)
    }

    private fun provideComposableCardSchemeImages(imageStyle: ImageStyle?): List<@Composable (() -> Unit)?> {
        val composableImagesList = ArrayList<@Composable (() -> Unit)?>()
        paymentStateManager.supportedCardSchemeList.forEach { cardScheme ->
            composableImagesList.add(imageMapper.map(imageStyle?.copy(image = cardScheme.imageId)))
        }
        return composableImagesList
    }

    private fun provideViewState(style: CardSchemeComponentStyle) = cardSchemeComponentStateMapper.map(style)

    private fun provideViewStyle(inputStyle: CardSchemeComponentStyle) = cardSchemeComponentStyleMapper.map(inputStyle)

    internal class Factory(
        private val injector: Injector,
        private val style: CardSchemeComponentStyle
    ) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var subComponentProvider: Provider<CardSchemeViewModelSubComponent.Builder>

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector.inject(this)
            return subComponentProvider.get().style(style).build().cardSchemeViewModel as T
        }
    }
}
