package checkout.checkout_android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsCallback;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsSession;
import androidx.core.content.res.ResourcesCompat;

import checkout.checkout_android.R;

public class CustomTabManager {

    private final Context context;

    public CustomTabManager(Context context) {
        this.context = context;
    }

    private CustomTabsIntent.Builder newDefaultBuilder(Context context) {
        Resources resources = context.getResources();
        Resources.Theme theme = context.getTheme();

        CustomTabColorSchemeParams defaultColors = new CustomTabColorSchemeParams.Builder()
            .setToolbarColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, theme))
            .build();

        return new CustomTabsIntent.Builder()
                .setDefaultColorSchemeParams(defaultColors)
                .setColorScheme(CustomTabsIntent.COLOR_SCHEME_SYSTEM)
                .setShowTitle(false);
    }

    public void navigateToUrl(String url, @Nullable CustomTabsSession session) {
        CustomTabsIntent.Builder customTabIntentBuilder = newDefaultBuilder(context);
        if (session != null) customTabIntentBuilder.setSession(session);

        customTabIntentBuilder
                .build()
                .launchUrl(context, Uri.parse(url));
    }

    public static class MyCallbackListener extends CustomTabsCallback {

        @Override
        public void onNavigationEvent(int navigationEvent, @Nullable Bundle extras) {
            super.onNavigationEvent(navigationEvent, extras);
            Log.d("CustomTabs", String.format("NavEvent: %s, Bundle: %s", eventToString(navigationEvent), bundleToString(extras)));
        }

        @Override
        public void extraCallback(@NonNull String callbackName, @Nullable Bundle args) {
            super.extraCallback(callbackName, args);
            Log.d("CustomTabs", String.format("Extra: %s", bundleToString(args)));
        }

        private static String eventToString(int navigationEvent) {
            switch(navigationEvent) {
                case CustomTabsCallback.NAVIGATION_STARTED: return "Navigation Started";
                case CustomTabsCallback.NAVIGATION_FINISHED: return "Navigation Finished";
                case CustomTabsCallback.NAVIGATION_FAILED: return "Navigation Failed";
                case CustomTabsCallback.NAVIGATION_ABORTED: return "Navigation Aborted";
                case CustomTabsCallback.TAB_SHOWN: return "Tab Shown";
                case CustomTabsCallback.TAB_HIDDEN: return "Tab Hidden";
                default: return "Unknown Event";
            }
        }

        private static String bundleToString(Bundle bundle) {
            StringBuilder b = new StringBuilder();

            b.append("{");

            if (bundle != null) {
                boolean first = true;

                for (String key : bundle.keySet()) {
                    if (!first) {
                        b.append(", ");
                    }
                    first = false;

                    b.append(key);
                    b.append(": ");
                    b.append(bundle.get(key));
                }
            }

            b.append("}");

            return b.toString();
        }
    }
}