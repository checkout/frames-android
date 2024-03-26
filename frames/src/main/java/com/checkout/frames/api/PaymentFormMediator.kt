package com.checkout.frames.api

import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.checkout.frames.screen.paymentform.PaymentFormScreen
import com.checkout.frames.screen.paymentform.model.PaymentFormConfig
import com.checkout.logging.EventLoggerProvider
import com.checkout.threedsecure.Executor
import com.checkout.threedsecure.ThreeDSExecutor
import com.checkout.threedsecure.logging.ThreeDSEventLogger
import com.checkout.threedsecure.model.ThreeDSRequest
import com.checkout.threedsecure.usecase.ProcessThreeDSUseCase

public class PaymentFormMediator(
    private val config: PaymentFormConfig,
) {

    /**
     * 3DS executor which can be used to execute and handle 3DS flow with WebView.
     */
    private val threeDSExecutor: Executor<ThreeDSRequest> by lazy(LazyThreadSafetyMode.NONE) {
        val logger = EventLoggerProvider.provide().apply {
            setup(config.context, config.environment)
        }
        ThreeDSExecutor(ProcessThreeDSUseCase(), ThreeDSEventLogger(logger))
    }

    @Composable
    public fun PaymentForm() {
        PaymentFormScreen(config)
    }

    /**
     * Set content for a [ComponentActivity].
     * Should be used in ComponentActivity.onCreate{} instead of [setContent].
     *
     * @param activity [ComponentActivity] in which PaymentFormScreen will be used.
     */
    public fun setActivityContent(activity: ComponentActivity) {
        activity.setContent { PaymentForm() }
    }

    /**
     * Provides view for a [Fragment].
     * Should be used in onCreateView.onCreateView{}.
     *
     * @param fragment [Fragment] in which PaymentFormScreen will be used.
     * @param strategy [ViewCompositionStrategy] strategy for managing disposal of this View's internal composition.
     * Defaults to [ViewCompositionStrategy.Default].
     *
     * @return [View]
     */
    @JvmOverloads
    public fun provideFragmentContent(
        fragment: Fragment,
        strategy: ViewCompositionStrategy = ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed,
    ): View = ComposeView(fragment.requireContext()).apply {
        // Dispose of the Composition when the view's LifecycleOwner is destroyed
        setViewCompositionStrategy(strategy)
        setContent { PaymentForm() }
    }

    /**
     * Execute and handle 3DS flow with WebView.
     *
     * @param request [ThreeDSRequest]
     */
    public fun handleThreeDS(request: ThreeDSRequest) {
        threeDSExecutor.execute(request)
    }
}
