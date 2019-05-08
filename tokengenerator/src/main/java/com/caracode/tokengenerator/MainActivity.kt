package com.caracode.tokengenerator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import com.checkout.sdk.CheckoutClient
import com.checkout.sdk.core.RequestMaker
import com.checkout.sdk.core.TokenResult
import com.checkout.sdk.request.CardTokenizationRequest
import com.checkout.sdk.utils.Environment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tokenCallback = createTokenCallback()
        val checkoutClient = createCheckoutClient(tokenCallback)
        val requestMaker = createRequestMaker(checkoutClient)
        generate_token_button.setOnClickListener {
            progress_bar.visibility = VISIBLE
            requestMaker.makeTokenRequest(createCardTokenizationRequest())
        }
    }

    private fun createTokenCallback(): CheckoutClient.TokenCallback {
       return CheckoutClient.TokenCallback { tokenResult ->
            when (tokenResult) {
                is TokenResult.TokenResultSuccess -> setSuccessText(tokenResult)
                is TokenResult.TokenResultTokenisationFail -> setTokenizationFail(tokenResult)
                is TokenResult.TokenResultNetworkError -> setNetworkFail(tokenResult)
            }
            progress_bar.visibility = GONE
        }
    }

    private fun createRequestMaker(checkoutClient: CheckoutClient): RequestMaker {
        return RequestMaker.create(this, checkoutClient)
    }

    private fun createCheckoutClient(tokenCallback: CheckoutClient.TokenCallback): CheckoutClient {
        return CheckoutClient(this, KEY, Environment.SANDBOX, tokenCallback)
    }

    fun createCardTokenizationRequest(): CardTokenizationRequest {
        return CardTokenizationRequest(
            "4242424242424242",
            "Jim Stynes",
            "06",
            "2020",
            "100",
            null
        )
    }

    private fun setSuccessText(tokenSuccess: TokenResult.TokenResultSuccess) {
        token_text_view.text = getString(R.string.token_success_format, tokenSuccess.response.id)
    }

    private fun setTokenizationFail(tokenizationFail: TokenResult.TokenResultTokenisationFail) {
        token_text_view.text =
            getString(R.string.token_failed_tokenization_format, tokenizationFail.error.errorCode)
    }

    private fun setNetworkFail(networkFail: TokenResult.TokenResultNetworkError) {
        token_text_view.text =
            getString(R.string.token_failed_tokenization_format, networkFail.exception)
    }

    companion object {
        const val KEY = "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73"
    }
}
