package com.checkout.sdk;

import android.content.Context;

import com.android.volley.VolleyError;
import com.checkout.sdk.core.TokenResult;
import com.checkout.sdk.request.CardTokenizationRequest;
import com.checkout.sdk.request.GooglePayTokenisationRequest;
import com.checkout.sdk.response.CardTokenizationFail;
import com.checkout.sdk.response.CardTokenizationResponse;
import com.checkout.sdk.response.GooglePayTokenisationFail;
import com.checkout.sdk.response.GooglePayTokenisationResponse;
import com.checkout.sdk.utils.Environment;
import com.checkout.sdk.utils.HttpUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckoutClient {

    public String key;

    public interface TokenCallback {
        void onTokenResult(TokenResult tokenResult);
    }

    /**
     * This is interface used as a callback for when the card token is generated
     */
    public interface OnTokenGenerated {
        void onTokenGenerated(CardTokenizationResponse response);

        void onError(CardTokenizationFail error);

        void onNetworkError(VolleyError error);
    }

    /**
     * This is interface used as a callback for when the google pay token is generated
     */
    public interface OnGooglePayTokenGenerated {
        void onTokenGenerated(GooglePayTokenisationResponse response);

        void onError(GooglePayTokenisationFail error);

        void onNetworkError(VolleyError error);
    }

    private Context mContext;
    private final Environment mEnvironment;
    private final CheckoutClient.TokenCallback tokenCallback;
    private CheckoutClient.OnGooglePayTokenGenerated mGooglePayTokenListener;


    public CheckoutClient(Context context, String key, Environment environment, CheckoutClient.TokenCallback tokenCallback) {
        this.mContext = context;
        this.key = key;
        this.mEnvironment = environment;
        this.tokenCallback = tokenCallback;
    }

    public CheckoutClient.TokenCallback getTokenCallback() {
        return tokenCallback;
    }

    public Environment getEnvironment() {
        return mEnvironment;
    }

    /**
     * This method is used to generate a card token.
     * <p>
     * It takes a {@link CardTokenizationRequest} as the argument and it will perform a
     * HTTP Post request to generate the token. it is important to you select an environment and
     * provide your public key before calling tis method. Moreover it is important to set a callback
     * {@link CheckoutClient.OnTokenGenerated} so you can receive the token back.
     * <p>
     * If you are using the UI of the SDK this method will be called automatically, but you still
     * need to provide the callback, key and environment when initialising this class
     *
     * @param request Custom request body to be used in the HTTP call.
     */
    public void generateToken(CardTokenizationRequest request, OnTokenGenerated tokenListener) {

        // Initialise the HTTP utility class
        HttpUtils http = new HttpUtils(mContext);

        // Provide a callback for when the token request is completed
        http.setTokenListener(tokenListener);

        // Using Gson to convert the custom request object into a JSON string for use in the HTTP call
        Gson gson = new Gson();
        String jsonBody = gson.toJson(request);

        try {
            http.generateToken(key, mEnvironment.getTokenHost() + "/" + Environment.TOKEN_PATH, jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method used to create a token that can be used in a server environment to create a
     * charge. It will take a GooglePay payload in JSON string format. The payload is usually generated in
     * the handlePaymentSuccess method shown in the Google Pay example from Google (token.getToken())
     *
     * @param payload Google Pay Payload
     */
    public void generateGooglePayToken(String payload) throws JSONException {

        JSONObject googlePayToken = new JSONObject(payload);

        // Initialise the HTTP utility class
        HttpUtils http = new HttpUtils(mContext);

        // Provide a callback for when the token request is completed
        http.setGooglePayTokenListener(mGooglePayTokenListener);

        GooglePayTokenisationRequest gPay = new GooglePayTokenisationRequest();

        gPay
                .setSignature(googlePayToken.getString("signature"))
                .setProtocolVersion(googlePayToken.getString("protocolVersion"))
                .setSignedMessage(googlePayToken.getString("signedMessage"));

        // Using Gson to convert the custom request object into a JSON string for use in the HTTP call
        Gson gson = new Gson();
        String jsonBody = gson.toJson(gPay);

        try {
            String url = mEnvironment.getGooglePayHost() + "/" + Environment.GOOGLE_PAY_PATH;
            http.generateGooglePayToken(key, url, jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method used to set a callback for Google Pay handling.
     *
     * @return CheckoutClient to allow method chaining
     */
    public CheckoutClient setGooglePayListener(OnGooglePayTokenGenerated listener) {
        this.mGooglePayTokenListener = listener;
        return this;
    }
}
