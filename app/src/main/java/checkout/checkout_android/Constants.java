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
	public static final String PUBLIC_KEY = "pk_sbox_dheqoi7tqn2jcvbn55sa2czwu42";

	/**
	 * Replace with public key from Hub in Sandbox Environment, testing key for CVV Tokenization
	 */
	public static final String PUBLIC_KEY_CVV_TOKENIZATION = "pk_sbox_dheqoi7tqn2jcvbn55sa2czwu42";
	/**
	 * Replace with Secret key from Hub in Sandbox Environment
	 */
	public static final String SECRET_KEY = "sk_sbox_cqc26o3haljkwuns6xcfcmf2vmu";
    /**
     * Replace with subdomain value, testing key for base url regional subdomain prefix
     */
    public static final  String REGIONAL_SUBDOMAIN = "global";
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
