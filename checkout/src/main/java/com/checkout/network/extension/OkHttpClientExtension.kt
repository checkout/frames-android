package com.checkout.network.extension

import com.checkout.base.error.CheckoutError
import com.checkout.network.error.NetworkError.Companion.RESPONSE_PARSING_ERROR
import com.checkout.network.response.ErrorResponse
import com.checkout.network.response.NetworkApiResponse
import com.squareup.moshi.JsonAdapter
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * Executing Http request
 *
 *  @param request: The request to be executed
 *  @param successAdapter: For parsing success response
 *  @param errorAdapter: For parsing error response
 *
 *  @return NetworkApiResponse<T>: Based on response of API
 */
@Suppress("TooGenericExceptionCaught")
internal inline fun <reified S : Any> OkHttpClient.executeHttpRequest(
    request: Request,
    successAdapter: JsonAdapter<S>,
    errorAdapter: JsonAdapter<ErrorResponse>
): NetworkApiResponse<S> {
    return try {
        newCall(request)
            .execute()
            .use {
                if (it.isSuccessful) provideSuccessResult(it, successAdapter)
                else provideErrorResult(it, errorAdapter)
            }
    } catch (e: Throwable) {
        NetworkApiResponse.NetworkError(e)
    }
}

@Throws(CheckoutError::class)
private inline fun <reified S : Any> provideSuccessResult(
    result: Response,
    adapter: JsonAdapter<S>
): NetworkApiResponse<S> = result.body?.source()?.let {
    adapter.fromJson(it)?.let { successResponse ->
        NetworkApiResponse.Success(successResponse)
    }
} ?: throw CheckoutError(
    errorCode = RESPONSE_PARSING_ERROR,
    message = "Success response is null, can not be parsed"
)

private fun provideErrorResult(
    result: Response,
    errorAdapter: JsonAdapter<ErrorResponse>
) = NetworkApiResponse.ServerError(
    result.body?.source()?.let { errorAdapter.fromJson(it) },
    result.code
)
