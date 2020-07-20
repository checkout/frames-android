package com.caracode.uicustomizing

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.checkout.sdk.CheckoutClient
import com.checkout.sdk.core.TokenResult
import com.checkout.sdk.paymentform.PaymentForm
import com.checkout.sdk.utils.Environment

/**
 * Here we customize the wording and coloring used on the CardDetails and BillingDetails form by
 * simply creating new values to override those from `cko_strings.xml` in the sdk with the new values
 * that you want to use (in cko_strings.xml and colors.xml respectively)
 * This gives usa more Shakespearean version of the text, a blue accent colour and an orange error colour.
 *
 * We also change the styling of the pay button by adding a new `cko_card_details.xml` in the project
 * and adding a background to the `Button` (all the rest of the `cko_card_details.xml` file is the same)
 *
 * The same idea can be applied to change color and size:
 *
 * `cko_colors.xml`
 * `cko_dimens.xml`
 *
 * to change layout:
 *
 * `cko_billing_details.xml`
 * `cko_card_details.xml`
 * `cko_payment_form.xml`
 * `cko_view_card_input.xml`
 * `cko_view_credit_card_icon.xml`
 * `cko_view_text_input.xml`
 *
 * and to change entry / exit animations between the Card Details screen and the Billing Details screen:
 *
 * `cko_in_back.xml`
 * `cko_in_forward.xml`
 * `cko_out_back.xml`
 * `cko_out_forward.xml`
 */
class UiCustomizingActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        val callback = object : CheckoutClient.TokenCallback {
            override fun onTokenResult(tokenResult: TokenResult) {
                Toast.makeText(this@UiCustomizingActivity, "Token result: $tokenResult", Toast.LENGTH_LONG).show()
            }
        }

        val checkoutClient = CheckoutClient.create(
            this,
            "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73",
            Environment.SANDBOX,
            callback
        )

        val paymentForm: PaymentForm = findViewById(R.id.checkout_card_form)
        paymentForm.initialize(checkoutClient)
    }
}
