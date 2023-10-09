package com.checkout.frames.cvvinputfield.api

import android.view.View
import androidx.annotation.UiThread
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.checkout.tokenization.model.CVVTokenizationResultHandler

/**
 * CVVComponent Mediator provides capabilities to load CVV component along with tokenization
 */
public interface CVVComponentMediator {

    /**
     * Load component in compose UI
     */
    @Composable
    public fun CVVComponent()

    /**
     *  To Add CVV Component in XML/Dynamic layouts
     *
     * @param container - provide a view container to add cvvComponent
     * @param strategy - A strategy for managing the underlying Composition of Compose UI Views
     */
    public fun provideCvvComponentContent(
        container: View,
        strategy: ViewCompositionStrategy = ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed,
    ): View

    /**
     * Creates a CVV token and invokes the provided [resultHandler] with the tokenization result.
     *
     * @param resultHandler - A lambda function that takes a [CVVTokenizationResultHandler] parameter.
     */
    @UiThread
    public fun createToken(resultHandler: (CVVTokenizationResultHandler) -> Unit)
}
