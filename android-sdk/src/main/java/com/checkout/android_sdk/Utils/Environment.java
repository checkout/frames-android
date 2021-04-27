package com.checkout.android_sdk.Utils;

public enum Environment {
    SANDBOX(
            "https://api.sandbox.checkout.com/tokens",
            "https://api.sandbox.checkout.com/.well-known/content-encoding/jwks",
            "https://api.sandbox.checkout.com/tokens"
    ),
    LIVE(
            "https://api.checkout.com/tokens",
            "https://api.checkout.com/.well-known/content-encoding/jwks",
            "https://api.checkout.com/tokens"
    );

    public final String token;
    public final String jwks;
    public final String googlePay;

    Environment(String token, String jwks, String googlePay) {
        this.token = token;
        this.jwks = jwks;
        this.googlePay = googlePay;
    }
}