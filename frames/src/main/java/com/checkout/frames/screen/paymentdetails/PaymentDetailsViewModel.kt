package com.checkout.frames.screen.paymentdetails

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.provider.ComponentProvider
import com.checkout.frames.component.provider.ComposableComponentProvider
import com.checkout.frames.di.CLOSE_PAYMENT_FLOW_DI
import com.checkout.frames.di.base.InjectionClient
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.screen.PaymentDetailsViewModelSubComponent
import com.checkout.frames.mapper.ImageStyleToClickableComposableImageMapper
import com.checkout.frames.model.request.ImageStyleToClickableImageRequest
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.screen.PaymentDetailsStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.view.TextLabelState
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

internal class PaymentDetailsViewModel @Inject constructor(
    val componentProvider: ComponentProvider,
    private val textLabelStyleMapper: Mapper<TextLabelStyle, TextLabelViewStyle>,
    private val textLabelStateMapper: Mapper<TextLabelStyle?, TextLabelState>,
    private val clickableImageStyleMapper: ImageStyleToClickableComposableImageMapper,
    @Named(CLOSE_PAYMENT_FLOW_DI)
    private val closePaymentFlowUseCase: UseCase<Unit, Unit>,
    private val paymentStateManager: PaymentStateManager,
    private val style: PaymentDetailsStyle
) : ViewModel() {

    val headerStyle: TextLabelViewStyle = provideHeaderViewStyle()
    val headerState: TextLabelState = provideHeaderState()

    init {
        paymentStateManager.resetPaymentState()
    }

    private fun provideHeaderViewStyle(): TextLabelViewStyle = with(style.paymentDetailsHeaderStyle) {
        textLabelStyleMapper.map(
            TextLabelStyle(text, textId, textStyle, containerStyle = containerStyle)
        ).apply { modifier = modifier.fillMaxWidth() }
    }

    private fun provideHeaderState(): TextLabelState = with(style.paymentDetailsHeaderStyle) {
        val imageRequest = ImageStyleToClickableImageRequest(backIconStyle) { closePaymentFlowUseCase.execute(Unit) }

        textLabelStateMapper.map(
            TextLabelStyle(text, textId, textStyle, containerStyle = containerStyle)
        ).apply { leadingIcon.value = clickableImageStyleMapper.map(imageRequest) }
    }

    internal class Factory(
        private val injector: Injector,
        private val style: PaymentDetailsStyle
    ) : ViewModelProvider.Factory, InjectionClient {

        @Inject
        lateinit var subComponentProvider: Provider<PaymentDetailsViewModelSubComponent.Builder>

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            injector.inject(this)
            return subComponentProvider.get()
                .style(style)
                .componentProvider(ComposableComponentProvider(injector))
                .build().paymentDetailsViewModel as T
        }
    }
}
