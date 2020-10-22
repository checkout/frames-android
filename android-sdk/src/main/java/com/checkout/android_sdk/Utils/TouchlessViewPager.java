package com.checkout.android_sdk.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Custom ViewPager  that is not scrollable
 */
public class TouchlessViewPager extends ViewPager {

    public TouchlessViewPager(@NonNull Context context) {
        this(context, null);
    }

    public TouchlessViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Prevent swiping
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    /**
     * Prevent swiping
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }
}
