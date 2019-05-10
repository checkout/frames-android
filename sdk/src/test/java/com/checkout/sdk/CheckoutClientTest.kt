package com.checkout.sdk

import com.checkout.sdk.api.OfflineException
import com.checkout.sdk.api.TokenApi
import com.checkout.sdk.core.TokenResult
import com.checkout.sdk.executors.Coroutines
import com.checkout.sdk.models.CardModel
import com.checkout.sdk.request.CardTokenizationRequest
import com.checkout.sdk.request.GooglePayTokenizationRequest
import com.checkout.sdk.response.CardTokenizationFail
import com.checkout.sdk.response.CardTokenizationResponse
import com.checkout.sdk.response.GooglePayTokenisationFail
import com.checkout.sdk.response.GooglePayTokenizationResponse
import com.google.gson.Gson
import kotlinx.coroutines.CompletableDeferred
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import retrofit2.Response


@ExtendWith(MockitoExtension::class, ThreadsUnconfinedExtension::class)
class CheckoutClientTest : TestUsesCoroutines {

    @Mock
    private lateinit var tokenApi: TokenApi

    @Mock
    private lateinit var coroutines: Coroutines

    @Mock
    private lateinit var tokenCallback: CheckoutClient.TokenCallback

    @Mock
    private lateinit var progressCallback: CheckoutClient.ProgressCallback

    private val key: String = "pk-78904256897"

    private lateinit var checkoutClient: CheckoutClient

    override fun getCoroutines(): Coroutines = coroutines

    @BeforeEach
    fun onSetup() {
        checkoutClient = CheckoutClient(
            key,
            tokenApi,
            coroutines,
            tokenCallback,
            progressCallback
        )
    }

    @Test
    fun `when the request throws an exception we call that back to the token callback and we update the progress`() {
        val cardTokenizationRequest = CardTokenizationRequest("a", "b", "c", "d", "e")
        val completableDeferred = CompletableDeferred<Response<CardTokenizationResponse>>()
        completableDeferred.completeExceptionally(OfflineException())

        given(tokenApi.getTokenAsync(key, cardTokenizationRequest)).willReturn(completableDeferred)

        checkoutClient.requestToken(cardTokenizationRequest)

        then(progressCallback.onProgressChanged(true))
        then(tokenCallback.onTokenResult(
            TokenResult.TokenResultNetworkError(
                OfflineException()
            )
        ))
        then(progressCallback.onProgressChanged(false))
    }

    @Test
    fun `when the google pay request throws an exception we call that back to the token callback and we update the progress`() {
        val googlePayTokenizationRequest = GooglePayTokenizationRequest("ev1", "MEUCIQC+Fww32or3AUhtN9oHv2SpJ0cjLY", "{\"encryptedMessage\":\"am9U9jl5itJ1TUL8u1TuF\",\"ephemeralPublicKey\":\"BKlk7Ay1gcOVCuf8mKF3UKalpVdUN+4aJfuX5K8GzOe3OY4YU7Xlp5AOg/5yvX/qd0mkVHxecXBfBGZIUx+qKr4\\u003d\",\"tag\":\"y3YYEZXE/ZywREXUVCtJkk/auYhk5feQRAUubDa1mXo\\u003d\"}")
        val completableDeferred = CompletableDeferred<Response<GooglePayTokenizationResponse>>()
        completableDeferred.completeExceptionally(OfflineException())

        given(tokenApi.getGooglePayTokenAsync(key, googlePayTokenizationRequest)).willReturn(completableDeferred)

        checkoutClient.requestToken(googlePayTokenizationRequest)

        then(progressCallback.onProgressChanged(true))
        then(tokenCallback.onTokenResult(
            TokenResult.TokenResultNetworkError(
                OfflineException()
            )
        ))
        then(progressCallback.onProgressChanged(false))
    }

    @Test
    fun `when the request is successful we return the successful response and update the progress`() {
        val cardTokenizationRequest = CardTokenizationRequest("a", "b", "c", "d", "e")
        val cardTokenizationResponse = CardTokenizationResponse("1", "2", "3", "4", CardModel())
        val completableDeferred = CompletableDeferred<Response<CardTokenizationResponse>>()
        completableDeferred.complete(Response.success(cardTokenizationResponse))

        given(tokenApi.getTokenAsync(key, cardTokenizationRequest)).willReturn(completableDeferred)

        checkoutClient.requestToken(cardTokenizationRequest)

        then(progressCallback.onProgressChanged(true))
        then(tokenCallback.onTokenResult(
            TokenResult.TokenResultSuccess(
                cardTokenizationResponse
            )
        ))
        then(progressCallback.onProgressChanged(false))
    }

    @Test
    fun `when a google pay request is successful we return the successful response and update the progress`() {
        val googlePayTokenizationRequest = GooglePayTokenizationRequest("ev1", "MEUCIQC+Fww32or3AUhtN9oHv2SpJ0cjLY", "{\"encryptedMessage\":\"am9U9jl5itJ1TUL8u1TuF\",\"ephemeralPublicKey\":\"BKlk7Ay1gcOVCuf8mKF3UKalpVdUN+4aJfuX5K8GzOe3OY4YU7Xlp5AOg/5yvX/qd0mkVHxecXBfBGZIUx+qKr4\\u003d\",\"tag\":\"y3YYEZXE/ZywREXUVCtJkk/auYhk5feQRAUubDa1mXo\\u003d\"}")
        val cardTokenizationResponse = GooglePayTokenizationResponse("googlepay", "tok_3908qunrea", "2019-10-03")
        val completableDeferred = CompletableDeferred<Response<GooglePayTokenizationResponse>>()
        completableDeferred.complete(Response.success(cardTokenizationResponse))

        given(tokenApi.getGooglePayTokenAsync(key, googlePayTokenizationRequest)).willReturn(completableDeferred)

        checkoutClient.requestToken(googlePayTokenizationRequest)

        then(progressCallback.onProgressChanged(true))
        then(tokenCallback.onTokenResult(
            TokenResult.TokenResultSuccess(
                cardTokenizationResponse
            )
        ))
        then(progressCallback.onProgressChanged(false))
    }

    @Test
    fun `when the request is unsuccessful we return the unsuccessful response and update the progress`() {
        val googlePayTokenizationRequest = GooglePayTokenizationRequest("ev1", "MEUCIQC+Fww32or3AUhtN9oHv2SpJ0cjLY", "{\"encryptedMessage\":\"am9U9jl5itJ1TUL8u1TuF\",\"ephemeralPublicKey\":\"BKlk7Ay1gcOVCuf8mKF3UKalpVdUN+4aJfuX5K8GzOe3OY4YU7Xlp5AOg/5yvX/qd0mkVHxecXBfBGZIUx+qKr4\\u003d\",\"tag\":\"y3YYEZXE/ZywREXUVCtJkk/auYhk5feQRAUubDa1mXo\\u003d\"}")
        val cardTokenizationFail = GooglePayTokenisationFail("1", "2", listOf("301", "404"))

        val jsonMediaType = MediaType.parse("application/json;charset=UTF-8")
        val failureBody = Gson().toJson(cardTokenizationFail)
        val completableDeferred = CompletableDeferred<Response<GooglePayTokenizationResponse>>()
        val response = Response.error<GooglePayTokenizationResponse>(404, ResponseBody.create(jsonMediaType, failureBody))
        completableDeferred.complete(response)

        given(tokenApi.getGooglePayTokenAsync(key, googlePayTokenizationRequest)).willReturn(completableDeferred)

        checkoutClient.requestToken(googlePayTokenizationRequest)

        then(progressCallback.onProgressChanged(true))
        then(tokenCallback.onTokenResult(
            TokenResult.TokenResultTokenizationFail(
                cardTokenizationFail
            )
        ))
        then(progressCallback.onProgressChanged(false))
    }

    @Test
    fun `when the google payrequest is unsuccessful we return the unsuccessful response and update the progress`() {
        val cardTokenizationRequest = CardTokenizationRequest("a", "b", "c", "d", "e")
        val cardTokenizationFail = CardTokenizationFail("1", "2", "3", listOf("301", "404"), listOf("Redirect", "Unknown server"))

        val jsonMediaType = MediaType.parse("application/json;charset=UTF-8")
        val failureBody = Gson().toJson(cardTokenizationFail)
        val completableDeferred = CompletableDeferred<Response<CardTokenizationResponse>>()
        val response = Response.error<CardTokenizationResponse>(404, ResponseBody.create(jsonMediaType, failureBody))
        completableDeferred.complete(response)

        given(tokenApi.getTokenAsync(key, cardTokenizationRequest)).willReturn(completableDeferred)

        checkoutClient.requestToken(cardTokenizationRequest)

        then(progressCallback.onProgressChanged(true))
        then(tokenCallback.onTokenResult(
            TokenResult.TokenResultTokenizationFail(
                cardTokenizationFail
            )
        ))
        then(progressCallback.onProgressChanged(false))
    }
}

/**
 * Implementing this interface and adding the ThreadsUnconfinedExtension as an `ExtendWith`
 * allows us to put all coroutines onto Dispatchers.Unconfined
 */
interface TestUsesCoroutines {
    fun getCoroutines(): Coroutines
}
