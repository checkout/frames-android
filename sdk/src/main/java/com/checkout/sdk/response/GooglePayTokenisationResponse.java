package com.checkout.sdk.response;

/**
 * The response model object for the Google Pay tokenisation response
 */
public class GooglePayTokenisationResponse implements TokenResponse {

    private String type;
    private String token;
    private String expires_on;

    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    public String getExpiration() {
        return expires_on;
    }
}
