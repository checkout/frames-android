package com.checkout.android_sdk.Request;

import com.checkout.android_sdk.Models.GooglePayModel;
import com.google.gson.annotations.SerializedName;

/**
 * The request model object for the Google Pay tokenisation request
 */
public class GooglePayTokenisationRequest {

    private final String type = TokenType.GOOGLEPAY.getValue();
    @SerializedName("token_data")
    private GooglePayModel token_data = new GooglePayModel();

    public GooglePayTokenisationRequest setSignature(String signature) {
        this.token_data.setSignature(signature);
        return this;
    }

    public GooglePayTokenisationRequest setProtocolVersion(String protocolVersion) {
        this.token_data.setProtocolVersion(protocolVersion);
        return this;
    }

    public GooglePayTokenisationRequest setSignedMessage(String signedMessage) {
        this.token_data.setSignedMessage(signedMessage);
        return this;
    }
}
