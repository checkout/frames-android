package com.checkout.frames.cvvinputfield.api

import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.checkout.base.usecase.UseCase
import com.checkout.frames.cvvinputfield.CVVInputField
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig
import com.checkout.frames.cvvinputfield.models.InternalCVVTokenRequest
import com.checkout.tokenization.model.CVVTokenizationResultHandler

internal class InternalCVVComponentMediator(
    private val cvvComponentConfig: CVVComponentConfig,
    private val cvvTokenizationUseCase: UseCase<InternalCVVTokenRequest, Unit>,
) : CVVComponentMediator {

    private val isCVVComponentCalled: MutableState<Boolean> = mutableStateOf(false)
    private val cvvInputFieldTextValue: MutableState<String> = mutableStateOf("")

    @Composable
    override fun CVVComponent() {
        InternalCVVComponent()
    }

    @Composable
    private fun InternalCVVComponent() {
        val isCVVComponentAlreadyLoaded = remember { isCVVComponentCalled.value }
        if (!isCVVComponentAlreadyLoaded) {
            CVVInputField(cvvComponentConfig) { onValueChange ->
                cvvInputFieldTextValue.value = onValueChange
            }
            isCVVComponentCalled.value = true
        }
    }

    override fun createToken(resultHandler: (CVVTokenizationResultHandler) -> Unit) {
        val internalCVVTokenRequest = InternalCVVTokenRequest(
            cvv = cvvInputFieldTextValue.value,
            cardScheme = cvvComponentConfig.cardScheme,
            resultHandler = resultHandler,
        )

        cvvTokenizationUseCase.execute(internalCVVTokenRequest)
    }

    override fun provideCvvComponentContent(
        container: View,
        strategy: ViewCompositionStrategy,
    ): View = ComposeView(container.context).apply {
        // Dispose of the Composition when the view's LifecycleOwner is destroyed
        setViewCompositionStrategy(strategy)
        setContent { InternalCVVComponent() }
    }

    override fun provideCvvComponentContent(
        container: View,
    ): View = provideCvvComponentContent(
        container,
        ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed,
    )

    @VisibleForTesting
    internal fun getIsCVVComponentCalled() = isCVVComponentCalled

    @VisibleForTesting
    internal fun setIsCVVComponentCalled(shouldCVVComponentCall: Boolean) {
        isCVVComponentCalled.value = shouldCVVComponentCall
    }

    @VisibleForTesting
    internal fun setCVVInputFieldTextValue(onValueChange: String) {
        cvvInputFieldTextValue.value = onValueChange
    }
}
