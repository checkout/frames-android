package checkout.checkout_android.config;

import android.net.Uri;

import com.checkout.android_sdk.Utils.Environment;

public class EnvironmentConfig {
    public final Environment environment;
    public final String publicKey;
    public final String secretKey;
    public final String paymentsEndpointUrl;

    public EnvironmentConfig(Environment environment, String publicKey, String secretKey) {
        this.environment = environment;
        this.publicKey = publicKey;
        this.secretKey = secretKey;

        this.paymentsEndpointUrl = Uri.parse(environment.token)
                .buildUpon()
                .path("/payments")
                .toString();
    }
}