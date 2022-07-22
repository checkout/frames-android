package com.checkout.network.response

/**
 * Generic call response class.
 */
internal sealed class NetworkApiResponse<out S> {
    /**
     * A request that resulted in a response with a 2xx status code that has a body.
     */
    data class Success<T>(val body: T) : NetworkApiResponse<T>()

    /**
     * A request that resulted in a response with a non-2xx status code.
     */
    data class ServerError(val body: ErrorResponse?, val code: Int) : NetworkApiResponse<Nothing>()

    /**
     * A request that didn't result in a response due to network failure.
     */
    data class NetworkError(val throwable: Throwable) : NetworkApiResponse<Nothing>()

    /**
     * A request that didn't result in a response due to unexpected sdk error.
     * For instance, parsing json object from string for googlePay
     */
    data class InternalError(val throwable: Throwable) : NetworkApiResponse<Nothing>()
}
