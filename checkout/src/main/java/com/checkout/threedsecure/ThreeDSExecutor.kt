package com.checkout.threedsecure

import android.webkit.WebView
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

internal class ThreeDSExecutor(
    private val processResultUseCase: UseCase<ProcessThreeDSRequest, ThreeDSResult>,
    private val logger: ThreeDSLogger
) : Executor<ThreeDSRequest> {

    override fun execute(request: ThreeDSRequest) = request.container.addView(provideWebView(request))

    @VisibleForTesting
    fun provideWebView(request: ThreeDSRequest): WebView = with(request) {
        ThreeDSWebView(container.context).apply {
            webViewClient = ThreeDSWebViewClient(
                onResult = { handleResult(it, successUrl, failureUrl, resultHandler) },
                onError = { handleError(it, resultHandler) },
                logger
            )
            loadUrl(request.challengeUrl)
        }
    }

    @VisibleForTesting
    fun handleResult(
        url: String?,
        successUrl: String,
        failureUrl: String,
        resultHandler: ThreeDSResultHandler
    ) {
        val threeDSResult = processResultUseCase.execute(ProcessThreeDSRequest(url, successUrl, failureUrl))
        logger.logCompletedEvent(
            success = threeDSResult is ThreeDSResult.Success,
            token = (threeDSResult as? ThreeDSResult.Success)?.token,
            error = (threeDSResult as? ThreeDSResult.Error)?.error
        )
        resultHandler.invoke(threeDSResult)
    }

    @VisibleForTesting
    fun handleError(error: ThreeDSError, resultHandler: ThreeDSResultHandler) {
        logger.logLoadedEvent(false, error)
        resultHandler.invoke(ThreeDSResult.Error(error))
    }
}
