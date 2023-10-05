package com.checkout.frames.cvvinputfield.api

import android.content.Context
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.checkout.base.model.Environment
import com.checkout.frames.cvvinputfield.CVVInputField
import com.checkout.frames.cvvinputfield.models.CVVComponentConfig
import com.checkout.tokenization.model.CVVTokenRequest

@Suppress("UnusedPrivateMember")
internal class InternalCVVComponentMediator(
    private val cvvComponentConfig: CVVComponentConfig,
    private val publicKey: String,
    private val environment: Environment,
    private val context: Context
) : CVVComponentMediator {

    private val isCVVComponentCalled: MutableState<Boolean> = mutableStateOf(false)

    @Composable
    override fun CVVComponent() {
        InternalCVVComponent()
    }

    @Composable
    private fun InternalCVVComponent() {
        val isCVVComponentAlreadyLoaded = remember { isCVVComponentCalled.value }
        if (!isCVVComponentAlreadyLoaded) {
            CVVInputField(cvvComponentConfig)
            isCVVComponentCalled.value = true
        }
    }

    override fun createToken(request: CVVTokenRequest) {
        // TODO; work in progress
    }

    override fun provideCvvComponentContent(
        container: View,
        strategy: ViewCompositionStrategy,
    ): View = ComposeView(container.context).apply {
        // Dispose of the Composition when the view's LifecycleOwner is destroyed
        setViewCompositionStrategy(strategy)
        setContent { InternalCVVComponent() }
    }

    @VisibleForTesting
    internal fun getIsCVVComponentCalled() = isCVVComponentCalled

    @VisibleForTesting
    internal fun setIsCVVComponentCalled(shouldCVVComponentCall: Boolean) {
        isCVVComponentCalled.value = shouldCVVComponentCall
    }
}
