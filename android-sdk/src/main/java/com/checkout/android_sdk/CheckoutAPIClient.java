package com.checkout.android_sdk;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.checkout.android_sdk.Request.CardTokenisationRequest;
import com.checkout.android_sdk.Request.GooglePayTokenisationRequest;
import com.checkout.android_sdk.Request.TokenType;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Response.GooglePayTokenisationFail;
import com.checkout.android_sdk.Response.GooglePayTokenisationResponse;
import com.checkout.android_sdk.Response.JWK;
import com.checkout.android_sdk.Response.JWKSResponse;
import com.checkout.android_sdk.Utils.Environment;
import com.checkout.android_sdk.Utils.JWEEncrypt;
import com.checkout.android_sdk.network.NetworkError;
import com.checkout.android_sdk.network.utils.OkHttpTokenRequestor;
import com.checkout.android_sdk.network.utils.TokenRequestor;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
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

    public interface OnJWKSFetched {
        void onJWKSFetched(JWKSResponse response);

        void onError(Throwable throwable);
    }

    @NonNull
    private final Context mContext;
    @NonNull
    private final Environment mEnvironment;
    @NonNull
    private final String mKey;
    @NonNull
    private final FramesLogger mLogger;
    private CheckoutAPIClient.OnTokenGenerated mTokenListener;
    private CheckoutAPIClient.OnGooglePayTokenGenerated mGooglePayTokenListener;

    /**
     * @see #CheckoutAPIClient(Context, String, Environment)
     * @deprecated explicitly define the environment to avoid using default environment value.
     */
    @Deprecated
    public CheckoutAPIClient(@NonNull Context context, @NonNull String key) {
        this(context, key, Environment.SANDBOX);
    }

    public CheckoutAPIClient(@NonNull Context context, @NonNull String key, @NonNull Environment environment) {
        this(context, key, environment, null);
    }

    CheckoutAPIClient(@NonNull Context context, @NonNull String key, @NonNull Environment environment, @Nullable FramesLogger logger) {
        this.mContext = context.getApplicationContext();
        this.mKey = key;
        this.mEnvironment = environment;

        if (logger == null) {
            mLogger = new FramesLogger();
            mLogger.initialise(this.mContext, environment);
        } else {
            mLogger = logger;
        }
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
            UUID correlationID = mLogger.initialiseForTransaction();
            FramesLogger.log(() -> mLogger.sendTokenRequestedEvent(TokenType.CARD));

            Gson gson = new Gson();
            TokenRequestor requester = new OkHttpTokenRequestor(mEnvironment, mKey, gson, mLogger);

            requester.fetchJWKS(new OnJWKSFetched() {
                @Override
                public void onJWKSFetched(JWKSResponse response) {
                    List<JWK> jwks = response.getKeys();
                    if (jwks.isEmpty()) {
                        mLogger.errorEvent(
                                "No JWKS found. Key set is empty."
                        );
                        mTokenListener.onError(new CardTokenisationFail());
                    } else {
                        JWK jwk = jwks.get(0);
                        String token = generateTokenUsingJOSE(gson, jwk, request);
                        if (!TextUtils.isEmpty(token)) {
                            mLogger.debugEvent("Processing  card tokenisation request using JWE " + jwk);
                            requester.requestCardToken(
                                    correlationID.toString(),
                                    token,
                                    true,
                                    mTokenListener
                            );
                        } else {
                            mLogger.debugEvent("Processing plain text card tokenisation request");
                            requester.requestCardToken(
                                    correlationID.toString(),
                                    gson.toJson(request),
                                    false,
                                    mTokenListener
                            );
                        }
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    mLogger.errorEvent(
                            "Error occurred while fetching JWKS",
                            throwable
                    );
                    mTokenListener.onError(new CardTokenisationFail());
                }
            });
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
            UUID correlationID = mLogger.initialiseForTransaction();
            FramesLogger.log(() -> mLogger.sendTokenRequestedEvent(TokenType.GOOGLEPAY));

            JSONObject googlePayToken = new JSONObject(payload);

            Gson gson = new Gson();
            TokenRequestor requester = new OkHttpTokenRequestor(mEnvironment, mKey, gson, mLogger);

            GooglePayTokenisationRequest gPay = new GooglePayTokenisationRequest();
            gPay
                    .setSignature(googlePayToken.getString("signature"))
                    .setProtocolVersion(googlePayToken.getString("protocolVersion"))
                    .setSignedMessage(googlePayToken.getString("signedMessage"));

            requester.requestGooglePayToken(
                    correlationID.toString(),
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

    private String generateTokenUsingJOSE(Gson gson, JWK jwk, CardTokenisationRequest request) {
        String token = "";
        try {
            byte[] payloadBytes = gson.toJson(request).getBytes();
            token = JWEEncrypt.encrypt(
                    jwk.getN(),
                    jwk.getE(),
                    jwk.getKid(),
                    payloadBytes
            );
            return token;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return "";
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
}
