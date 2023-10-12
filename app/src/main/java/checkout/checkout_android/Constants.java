package checkout.checkout_android;

import com.checkout.base.model.Environment;

public class Constants {
	/**
	 * Target platform environment
	 */
	public static final Environment ENVIRONMENT = Environment.SANDBOX;
	/**
	 * Replace with public key from Hub in Sandbox Environment
	 */
	public static final String PUBLIC_KEY = "pk_test_b37b8b6b-fc9a-483f-a77e-3386b606f90e";

	/**
	 * Replace with public key from Hub in Sandbox Environment, testing key for CVV Tokenization
	 */
	public static final String PUBLIC_KEY_CVV_TOKENIZATION = "pk_6b30805a-1f3b-4c63-8b75-eb3030109173";
	/**
	 * Replace with Secret key from Hub in Sandbox Environment
	 */
	public static final String SECRET_KEY = "sk_test_568e6077-a08f-4692-9237-cc6c48dcf6aa";
	/**
	 * Replace with Success/Failure Urls from Hub in Sandbox Environment
	 */
	public static final String SUCCESS_URL = "https://httpstat.us/200?q=Success";
	public static final String FAILURE_URL = "https://httpstat.us/200?q=Failure";
	/**
	 * The payment amount to used when creating a payment for 3DS authentication.
	 * <p>
	 * Using specific amount values will trigger certain failure modes.
	 * </p>
	 */
	public static final Long PAYMENT_AMOUNT = 10000L;


	public static Long backgroundColor = 0XFFFFCDC2L;
}
