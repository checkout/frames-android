package com.checkout.threedsecure.usecase

import android.net.Uri
import androidx.annotation.RestrictTo
import com.checkout.base.usecase.UseCase
import com.checkout.threedsecure.error.ThreeDSError
import com.checkout.threedsecure.model.ProcessThreeDSRequest
import com.checkout.threedsecure.model.ThreeDSResult
import com.checkout.threedsecure.utils.matches

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class ProcessThreeDSUseCase : UseCase<ProcessThreeDSRequest, ThreeDSResult?> {

    private companion object {
        const val KEY_PAYMENT_TOKEN = "cko-payment-token"
        const val KEY_SESSION_ID = "cko-session-id"
    }

    override fun execute(data: ProcessThreeDSRequest): ThreeDSResult? = with(data) {
        val redirectUri = Uri.parse(redirectUrl)

        return when {
            Uri.parse(successUrl).matches(redirectUri) -> {
                val token = provideToken(redirectUrl)

                if (token == null) {
                    provideError(ThreeDSError.COULD_NOT_EXTRACT_TOKEN, "Url can't be null.")
                } else {
                    ThreeDSResult.Success(token)
                }
            }
            Uri.parse(failureUrl).matches(redirectUri) -> ThreeDSResult.Failure
            else -> null
        }
    }

    private fun provideError(errorCode: String, message: String?) =
        ThreeDSResult.Error(ThreeDSError(errorCode, message))

    private fun provideToken(redirectUrl: String?): String? = redirectUrl?.let {
        val uri: Uri = Uri.parse(it)
        uri.getQueryParameter(KEY_PAYMENT_TOKEN) ?: uri.getQueryParameter(KEY_SESSION_ID)
    }
}
