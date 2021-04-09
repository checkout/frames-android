package com.checkout.android_sdk.Input;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.checkout.android_sdk.Utils.AfterTextChangedListener;
import com.checkout.android_sdk.Utils.KeyboardUtils;

/**
 * A custom EdiText with validation and handling of address input
 */
public class AddressOneInput extends AppCompatEditText {

    public interface AddressOneListener {
        void onAddressOneInputFinish(String number);
        void clearAddressOneError();
    }

    @Nullable
    private AddressOneInput.AddressOneListener mAddressOneListener;

    public AddressOneInput(Context context) {
        this(context, null);
    }

    public AddressOneInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    private void initialise() {
        addTextChangedListener(new AfterTextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                // Save state
                if (mAddressOneListener != null) {
                    mAddressOneListener.onAddressOneInputFinish(s.toString());
                    mAddressOneListener.clearAddressOneError();
                }
            }
        });

        setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                performClick();
                // Clear error if the user starts typing
                if (mAddressOneListener != null) {
                    mAddressOneListener.clearAddressOneError();
                }
                KeyboardUtils.showSoftKeyboard(this, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    public void setAddressOneListener(AddressOneInput.AddressOneListener listener) {
        this.mAddressOneListener = listener;
    }
}
