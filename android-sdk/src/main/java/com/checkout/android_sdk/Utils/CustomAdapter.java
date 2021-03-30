package com.checkout.android_sdk.Utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.checkout.android_sdk.View.BillingDetailsView;
import com.checkout.android_sdk.View.CardDetailsView;

/**
 * The adapter of the viewpager used to have the 2 pages {@link CardDetailsView}
 * and {@link BillingDetailsView}
 * <p>
 * This class handles interaction initialisation and interaction between the to pages
 */
public class CustomAdapter extends PagerAdapter {
    // Indexes for the pages
    public static final int CARD_DETAILS_PAGE_INDEX = 0;
    public static final int BILLING_DETAILS_PAGE_INDEX = 1;
    private static final int PAGE_COUNT = 2;

    private @Nullable CardDetailsView mCardDetailsView;
    private @Nullable CardDetailsView.GoToBillingListener mCardDetailsListener;
    private @Nullable CardDetailsView.DetailsCompleted mDetailsCompletedListener;
    private @Nullable BillingDetailsView mBillingDetailsView;
    private @Nullable BillingDetailsView.Listener mBillingListener;

    /**
     * Pass the callback to go to the billing page
     */
    public void setCardDetailsListener(CardDetailsView.GoToBillingListener listener) {
        mCardDetailsListener = listener;
    }

    /**
     * Pass the callback to go to the card details page
     */
    public void setBillingListener(BillingDetailsView.Listener listener) {
        mBillingListener = listener;
    }

    /**
     * Pass the callback for when the card toke is generated
     */
    public void setTokenDetailsCompletedListener(CardDetailsView.DetailsCompleted listener) {
        mDetailsCompletedListener = listener;
    }

    /**
     * Indicate the {@link CardDetailsView} need to update the billing spinner
     */
    public void updateBillingSpinner() {
        if (mCardDetailsView != null) {
            mCardDetailsView.updateBillingSpinner();
        }
    }

    /**
     * Indicate the {@link CardDetailsView} need to clear the billing spinner
     */
    public void clearBillingSpinner() {
        if (mCardDetailsView != null) {
            mCardDetailsView.clearBillingSpinner();
        }
    }

    /**
     * Clean form data and state
     */
    public void clearFields() {
        if (mCardDetailsView != null) {
            mCardDetailsView.resetFields();
        }
        if (mBillingDetailsView != null) {
            mBillingDetailsView.resetFields();
        }
    }

    /**
     * Instantiation function
     */
    @NonNull
    @Override
    public View instantiateItem(@NonNull ViewGroup container, int position) {
        maybeInstantiateViews(container);
        View childView = null;
        switch (position) {
            case CARD_DETAILS_PAGE_INDEX:
                childView = mCardDetailsView;
                break;
            case BILLING_DETAILS_PAGE_INDEX:
                childView = mBillingDetailsView;
                break;
        }
        assert childView != null; // Child view should not be null.
        return childView;
    }

    /**
     * Indicate there viewpager position
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "page " + position;
    }

    /**
     * Indicate there is only a 2 level depth in the viewpager
     */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * Instantiates teh viewpager and adds the 2 pages: {@link CardDetailsView}
     * and {@link BillingDetailsView}
     */
    private void maybeInstantiateViews(ViewGroup container) {

        if (mCardDetailsView == null) {
            mCardDetailsView = new CardDetailsView(container.getContext());
            mCardDetailsView.setGoToBillingListener(mCardDetailsListener);
            mCardDetailsView.setDetailsCompletedListener(mDetailsCompletedListener);

            container.addView(mCardDetailsView, CARD_DETAILS_PAGE_INDEX);
        }
        if (mBillingDetailsView == null) {
            mBillingDetailsView = new BillingDetailsView(container.getContext());
            mBillingDetailsView.setGoToCardDetailsListener(mBillingListener);

            container.addView(mBillingDetailsView, BILLING_DETAILS_PAGE_INDEX);
        }
    }
}