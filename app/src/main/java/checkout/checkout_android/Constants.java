package checkout.checkout_android;

import com.checkout.android_sdk.Utils.Environment;

import checkout.checkout_android.config.EnvironmentConfig;

public class Constants {
    private Constants() { }

    /**
     * Frames Test configuration on Sandbox Environment
     */
    private static final EnvironmentConfig SANDBOX = new EnvironmentConfig(
            Environment.SANDBOX,
            "pk_test_b37b8b6b-fc9a-483f-a77e-3386b606f90e",
            "sk_test_568e6077-a08f-4692-9237-cc6c48dcf6aa"
    );
    /**
     * Configuration for a live environment
     */
    private static final EnvironmentConfig PRODUCTION = new EnvironmentConfig(
            Environment.LIVE,
            "",
            ""
    );

    private static final EnvironmentConfig ACTIVE_ENVIRONMENT_CONFIG = SANDBOX;

    public static final Environment ENVIRONMENT = ACTIVE_ENVIRONMENT_CONFIG.environment;
    public static final String PUBLIC_KEY = ACTIVE_ENVIRONMENT_CONFIG.publicKey;
    public static final String SECRET_KEY = ACTIVE_ENVIRONMENT_CONFIG.secretKey;
    /*
     * Replace with Success/Failure Urls from Hub in Sandbox Environment
     */
    public static final String SUCCESS_URL = "cko://www.frames-test.com/success";
    public static final String FAILURE_URL = "cko://www.frames-test.com/failure";
    /**
     * The payment amount to used when creating a payment for 3DS authentication.
     * <p>
     *     Using specific amount values will trigger certain failure modes.
     * </p>
     */
    public static final Long PAYMENT_AMOUNT = 1L;
    /**
     * SANDBOX_PAYMENT_URL
     */
    public static final String SANDBOX_PAYMENT_URL = ACTIVE_ENVIRONMENT_CONFIG.paymentsEndpointUrl;
}
