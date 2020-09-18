package com.checkout.android_sdk.Response;

import com.google.gson.annotations.SerializedName;

/**
 * The response model object for the card tokenisation error
 */
public class CardTokenisationFail {

    @SerializedName("request_id")
    private String request_id;
    @SerializedName("error_type")
    private String error_type;
    @SerializedName("error_codes")
    private String[] error_codes;

    public String getRequestId() {
        return request_id;
    }

    public String getErrorType() {
        return error_type;
    }

    public String[] getErrorCodes() {
        return error_codes;
    }
}
