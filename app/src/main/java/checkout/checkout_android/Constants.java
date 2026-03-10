package checkout.checkout_android;

import com.checkout.base.model.Environment;

public class Constants {
	/**
	 * Target platform environment
	 */
	public static final Environment ENVIRONMENT = Environment.SANDBOX;

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
