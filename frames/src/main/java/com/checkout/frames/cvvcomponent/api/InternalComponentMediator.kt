package com.checkout.frames.cvvcomponent.api

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.checkout.frames.cvvcomponent.models.CVVComponentConfig
import com.checkout.frames.cvvcomponent.CvvInputComponent
import com.checkout.tokenization.model.CVVTokenRequest

internal class InternalComponentMediator(private val cvvComponentConfig: CVVComponentConfig) : ComponentMediator {

    @Composable
    override fun CVVComponent() {
        CvvInputComponent(cvvComponentConfig)
    }

    override fun createToken(request: CVVTokenRequest) {
        // TODO; work in progress
    }

    override fun provideCvvComponentContent(
        container: View,
        strategy: ViewCompositionStrategy
    ): View = ComposeView(container.context).apply {
        // Dispose of the Composition when the view's LifecycleOwner is destroyed
        setViewCompositionStrategy(strategy)
        setContent { CvvInputComponent(cvvComponentConfig) }
    }
}
