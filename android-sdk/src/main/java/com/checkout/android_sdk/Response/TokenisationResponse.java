package com.checkout.android_sdk.Response;

public interface TokenisationResponse {

    String getType();

    String getToken();

    String getTokenExpiry();

    String getScheme();
}
