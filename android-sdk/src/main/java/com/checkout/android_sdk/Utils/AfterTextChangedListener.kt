package com.checkout.android_sdk.Utils

import android.text.TextWatcher


abstract class AfterTextChangedListener : TextWatcher {

    final override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Do Nothing
    }

    final override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Do Nothing
    }
}
