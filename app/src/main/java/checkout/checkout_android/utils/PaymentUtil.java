package checkout.checkout_android.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import checkout.checkout_android.Constants;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class PaymentUtil {
    private PaymentUtil() {
    }

    private static final boolean LOGGING_ENABLED = true;

    public interface Callback {
        void onPaymentCreated(boolean success, @Nullable String redirectUrl);
    }

    public static OkHttpClient newClient() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        if (LOGGING_ENABLED) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.d("okhttp", message));
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            okHttpBuilder.addInterceptor(loggingInterceptor);
        }

        return okHttpBuilder.build();
    }

    public static void createPayment(String token, Callback callback) {

        Handler responseHandler = new Handler(Looper.getMainLooper());
        RequestBody requestBody = buildPaymentRequestBody(token);
        Request paymentRequest = new Request.Builder()
                .url("https://api.sandbox.checkout.com/payments")
                .addHeader("Authorization", Constants.SECRET_KEY)
                .post(requestBody)
                .build();

        newClient().newCall(paymentRequest)
                .enqueue(new okhttp3.Callback() {

                    private void postResponse(boolean success, @Nullable String redirectUrl) {
                        responseHandler.post(() -> callback.onPaymentCreated(success, redirectUrl));
                    }

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                        postResponse(false, null);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) {
                        boolean result = false;
                        String redirectUrl = null;
                        try {
                            ResponseBody body = response.body();
                            if (response.isSuccessful() && body != null) {
                                String jsonString = body.string();
                                JSONObject jsonObject = new JSONObject(jsonString);
                                String paymentStatus = jsonObject.optString("status");
                                switch (paymentStatus) {
                                    case "Pending":
                                    case "Declined":
                                        result = false;
                                        break;
                                    default:
                                        result = true;
                                        break;
                                }

                                JSONObject linksObject = jsonObject.optJSONObject("_links");
                                if (linksObject != null) {
                                    JSONObject redirectObject = linksObject.optJSONObject("redirect");
                                    if (redirectObject != null) {
                                        redirectUrl = redirectObject.getString("href");
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            postResponse(result, redirectUrl);
                        }
                    }
                });
    }

    private static RequestBody buildPaymentRequestBody(String token) {

        String requestBodyJson = "{\n" +
                "    \"source\": {\n" +
                "        \"type\": \"token\",\n" +
                "        \"token\": \"" + token + "\"\n" +
                "    },\n" +
                "    \"amount\": " + Constants.PAYMENT_AMOUNT + ",\n" +
                "    \"currency\": \"GBP\",\n" +
                "    \"reference\": \"ORD-5023-4E89\",\n" +
                "    \"success_url\": \"" + Constants.SUCCESS_URL + "\",\n" +
                "    \"failure_url\": \"" + Constants.FAILURE_URL + "\",\n" +
                "    \"3ds\": {\n" +
                "        \"enabled\": true,\n" +
                "        \"version\": \"1.0.2\"\n" +
                "    }\n" +
                "}";

        return RequestBody.create(
                requestBodyJson,
                MediaType.parse("application/json; charset=utf-8")
        );
    }
}
