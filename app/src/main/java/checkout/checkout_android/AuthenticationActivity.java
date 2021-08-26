package checkout.checkout_android;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;

import checkout.checkout_android.utils.CustomTabManager;

@SuppressLint("SetTextI18n")
public class AuthenticationActivity extends AppCompatActivity {

    public static String EXTRA_AUTH_URL = "extra_auth_url";
    public static final int RESULT_AUTH_SUCCESS = 101;
    public static final int RESULT_AUTH_FAILED = 102;

    private CustomTabManager mCustomTabManager;
    private CustomTabsSession mSession;
    private CustomTabsServiceConnection mConnection;

    private String mAuthenticationUrl;
    private boolean mAuthenticationStarted = false;

    private TextView mFeedbackTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        Log.d("CustomTabs", "AuthenticationActivity: onCreate: Intent=" + getIntent().toString());

        mFeedbackTextView = findViewById(R.id.feedback_text);
        findViewById(R.id.button_ok).setOnClickListener(view -> finish());

        mCustomTabManager = new CustomTabManager(this);
        mAuthenticationUrl = getIntent().getStringExtra(EXTRA_AUTH_URL);

        checkAuthResult(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("CustomTabs", "AuthenticationActivity: onNewIntent");
        super.onNewIntent(intent);
        checkAuthResult(intent);
    }

    private void checkAuthResult(Intent intent) {
        Uri intentDataUri = intent.getData();
        Log.d("CustomTabs", "AuthenticationActivity: checkAuthResult: Data=" + intentDataUri);
        if (intentDataUri != null) {
            showAuthenticationFeedback(intentDataUri.toString());
        }
    }

    private boolean shouldStartAuthentication() {
        Log.d("CustomTabs", "AuthenticationActivity: shouldStartAuthentication");
        if (mAuthenticationStarted || mAuthenticationUrl == null) return false;
        if (mConnection != null) return false;
        if (getIntent().getData() != null) return false;

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (shouldStartAuthentication()) {
            initialiseForCustomTabs();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mConnection != null) {
            unbindService(mConnection);
            mConnection = null;
        }
    }

    private void showAuthenticationPage() {
        if (mAuthenticationUrl != null) {
            mAuthenticationStarted = true;
            mFeedbackTextView.setText("Launching authentication");

            mCustomTabManager.navigateToUrl(mAuthenticationUrl, mSession);
        } else {
            mFeedbackTextView.setText("Authentication URL not provided");
        }
    }

    private void showAuthenticationFeedback(@NonNull String redirectedUrl) {
        TextView textView = findViewById(R.id.feedback_text);
        if (redirectedUrl.startsWith(Constants.SUCCESS_URL)) {
            textView.setText("Authentication Success");
            setResult(RESULT_AUTH_SUCCESS);
        } else if (redirectedUrl.startsWith(Constants.FAILURE_URL)) {
            textView.setText("Authentication Failed");
            setResult(RESULT_AUTH_FAILED);
        } else {
            textView.setText("Unknown URL");
        }
        //textView.append("\nUrl: " + redirectedUrl);
    }


    private void initialiseForCustomTabs() {
        Log.d("CustomTabs", "AuthenticationActivity: initialiseForCustomTabs");
        mConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(
                    @NonNull ComponentName name,
                    @NonNull CustomTabsClient client
            ) {
                mSession = client.newSession(new CustomTabManager.MyCallbackListener());
                client.warmup(0);
                showAuthenticationPage();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };

        String packageName = CustomTabsClient.getPackageName(this, null);
        if (packageName == null) {
            Toast.makeText(this, "Can't find a Custom Tabs provider.", Toast.LENGTH_SHORT).show();
            return;
        }

        CustomTabsClient.bindCustomTabsService(this, packageName, mConnection);
    }

}