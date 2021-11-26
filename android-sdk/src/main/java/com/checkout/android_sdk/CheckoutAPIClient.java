package com.checkout.android_sdk;

import android.content.Context;

import androidx.annotation.NonNull;

import com.checkout.android_sdk.Request.CardTokenisationRequest;
import com.checkout.android_sdk.Request.GooglePayTokenisationRequest;
import com.checkout.android_sdk.Request.TokenType;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Response.GooglePayTokenisationFail;
import com.checkout.android_sdk.Response.GooglePayTokenisationResponse;
import com.checkout.android_sdk.Utils.Environment;
import com.checkout.android_sdk.network.NetworkError;
import com.checkout.android_sdk.network.utils.OkHttpTokenRequestor;
import com.checkout.android_sdk.network.utils.TokenRequestor;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class CheckoutAPIClient {

    /**
     * This is interface used as a callback for when the card token is generated
     */
    public interface OnTokenGenerated {
        void onTokenGenerated(CardTokenisationResponse response);

        void onError(CardTokenisationFail error);

        void onNetworkError(NetworkError error);
    }

    /**
     * This is interface used as a callback for when the google pay token is generated
     */
    public interface OnGooglePayTokenGenerated {
        void onTokenGenerated(GooglePayTokenisationResponse response);

        void onError(GooglePayTokenisationFail error);

        void onNetworkError(NetworkError error);
    }

    @NonNull private final Context mContext;
    @NonNull private final Environment mEnvironment;
    @NonNull private final String mKey;
    @NonNull private final FramesLogger mLogger;
    private CheckoutAPIClient.OnTokenGenerated mTokenListener;
    private CheckoutAPIClient.OnGooglePayTokenGenerated mGooglePayTokenListener;

    private String mCorrelationID = null;

    /**
     * @deprecated explicitly define the environment to avoid using default environment value.
     * @see #CheckoutAPIClient(Context, String, Environment)
     */
    @Deprecated
    public CheckoutAPIClient(@NonNull Context context, @NonNull String key) {
        this(context, key, Environment.SANDBOX);
    }

    public CheckoutAPIClient(@NonNull Context context, @NonNull String key, @NonNull Environment environment) {
        this.mContext = context.getApplicationContext();
        this.mKey = key;
        this.mEnvironment = environment;

        this.mLogger = new FramesLogger();
        this.mLogger.initialise(this.mContext, environment);
    }

    /**
     * This method is used to generate a card token.
     * <p>
     * It takes a {@link CardTokenisationRequest} as the argument and it will perform a
     * HTTP Post request to generate the token. it is important to you select an environment and
     * provide your public key before calling tis method. Moreover it is important to set a callback
     * {@link CheckoutAPIClient.OnTokenGenerated} so you can receive the token back.
     * <p>
     * If you are using the UI of the SDK this method will be called automatically, but you still
     * need to provide the callback, key and environment when initialising this class
     *
     * @param request Custom request body to be used in the HTTP call.
     */
    public void generateToken(CardTokenisationRequest request) {
        try {
            String correlationID = initialiseLoggingSession();
            FramesLogger.log(() -> mLogger.sendTokenRequestedEvent(TokenType.CARD, mKey));

            Gson gson = new Gson();
            TokenRequestor requester = new OkHttpTokenRequestor(mEnvironment, mKey, gson, mLogger);

            requester.requestCardToken(
                    correlationID,
                    gson.toJson(request),
                    mTokenListener
            );
        } catch (Exception e) {
            mLogger.errorEvent(
                    "Error occurred in Card tokenisation request",
                    e
            );
            throw e;
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
        try {
            String correlationID = initialiseLoggingSession();
            FramesLogger.log(() -> mLogger.sendTokenRequestedEvent(TokenType.GOOGLEPAY, mKey));

            JSONObject googlePayToken = new JSONObject(payload);

            Gson gson = new Gson();
            TokenRequestor requester = new OkHttpTokenRequestor(mEnvironment, mKey, gson, mLogger);

            GooglePayTokenisationRequest gPay = new GooglePayTokenisationRequest();
            gPay
                    .setSignature(googlePayToken.getString("signature"))
                    .setProtocolVersion(googlePayToken.getString("protocolVersion"))
                    .setSignedMessage(googlePayToken.getString("signedMessage"));

            requester.requestGooglePayToken(
                    correlationID,
                    gson.toJson(gPay),
                    mGooglePayTokenListener
            );
        } catch (Exception e) {
            mLogger.errorEvent(
                    "Error occurred in GooglePay tokenisation request",
                    e
            );
            throw e;
        }
    }

    /**
     * This method used to set a callback for 3D Secure handling.
     *
     * @return CheckoutAPIClient to allow method chaining
     */
    public CheckoutAPIClient setTokenListener(OnTokenGenerated listener) {
        this.mTokenListener = listener;
        return this;
    }

    /**
     * This method used to set a callback for Google Pay handling.
     *
     * @return CheckoutAPIClient to allow method chaining
     */
    public CheckoutAPIClient setGooglePayListener(OnGooglePayTokenGenerated listener) {
        this.mGooglePayTokenListener = listener;
        return this;
    }

    public String getCorrelationID() {
        return mCorrelationID;
    }
    public void setCorrelationID(String correlationID) {
        this.mCorrelationID = correlationID;
    }

    private String initialiseLoggingSession() {
        String correlationID = getCorrelationID();
        if (correlationID == null) {
            correlationID = UUID.randomUUID().toString();
        }
        mLogger.initialiseLoggingSession(correlationID);

        return correlationID;
    }
}
