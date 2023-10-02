package com.checkout.frames.cvvinputfield.api

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.checkout.tokenization.model.CVVTokenRequest

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
    ): View?

    /**
     * Creates token for CVV
     *
     * @param request - [CVVTokenRequest] contains result handlers
     */
    public fun createToken(request: CVVTokenRequest)
}
