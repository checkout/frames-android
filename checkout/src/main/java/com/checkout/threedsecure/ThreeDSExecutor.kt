package com.checkout.threedsecure

import android.webkit.WebView
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import com.checkout.base.usecase.UseCase
import com.checkout.threedsecure.error.ThreeDSError
import com.checkout.threedsecure.logging.ThreeDSLogger
import com.checkout.threedsecure.model.ProcessThreeDSRequest
import com.checkout.threedsecure.model.ThreeDSRequest
import com.checkout.threedsecure.model.ThreeDSResult
import com.checkout.threedsecure.model.ThreeDSResultHandler
import com.checkout.threedsecure.webview.ThreeDSWebView
import com.checkout.threedsecure.webview.ThreeDSWebViewClient

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class ThreeDSExecutor(
    private val processResultUseCase: UseCase<ProcessThreeDSRequest, ThreeDSResult?>,
    private val logger: ThreeDSLogger,
) : Executor<ThreeDSRequest> {

    override fun execute(request: ThreeDSRequest): Unit = request.container.addView(provideWebView(request))

    @VisibleForTesting
    public fun provideWebView(request: ThreeDSRequest): WebView = with(request) {
        ThreeDSWebView(container.context).apply {
            webViewClient = ThreeDSWebViewClient(
                onResult = { handleResult(it, successUrl, failureUrl, resultHandler) },
                onError = { handleError(it, resultHandler) },
                logger,
            )
            loadUrl(request.challengeUrl)
        }
    }

    @VisibleForTesting
    public fun handleResult(
        url: String?,
        successUrl: String,
        failureUrl: String,
        resultHandler: ThreeDSResultHandler,
    ): Boolean {
        val threeDSResult = processResultUseCase.execute(ProcessThreeDSRequest(url, successUrl, failureUrl))

        threeDSResult?.let {
            logger.logCompletedEvent(
                success = it is ThreeDSResult.Success,
                token = (it as? ThreeDSResult.Success)?.token,
                error = (it as? ThreeDSResult.Error)?.error,
            )
            resultHandler.invoke(it)
            return true
        }
        return false
    }

    @VisibleForTesting
    public fun handleError(error: ThreeDSError, resultHandler: ThreeDSResultHandler) {
        logger.logLoadedEvent(false, error)
        resultHandler.invoke(ThreeDSResult.Error(error))
    }
}
