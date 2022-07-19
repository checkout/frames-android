package checkout.checkout_android;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Flow_Test {

    @Rule
    public ActivityScenarioRule<DemoActivity> rule = new ActivityScenarioRule<>(DemoActivity.class);

    @Before
    public void setUp() {
        ActivityScenario<DemoActivity> scenario = rule.getScenario();
    }

    @Test
    public void Card_And_Billing() {
        ViewInteraction cardInput = onView(withId(R.id.card_input));
        cardInput.perform(scrollTo(), click());

        ViewInteraction cardInput2 = onView(withId(R.id.card_input));
        cardInput2.perform(scrollTo(), replaceText("4242 4242 4242 4242"), closeSoftKeyboard());

        ViewInteraction monthInput = onView(withId(R.id.month_input));
        monthInput.perform(scrollTo(), click());

        DataInteraction checkedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(5);
        checkedTextView.perform(click());

        onView(withId(R.id.year_input)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(3).perform(click());

        ViewInteraction defaultInput = onView(withId(R.id.cvv_input));
        defaultInput.perform(scrollTo(), replaceText("100"), closeSoftKeyboard());

        onView(withId(R.id.go_to_billing)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        ViewInteraction touchlessViewPager = onView(allOf(withId(R.id.view_pager), isDisplayed()));
        touchlessViewPager.perform(swipeLeft());

        ViewInteraction defaultInput2 = onView(withId(R.id.name_input));
        defaultInput2.perform(scrollTo(), replaceText("john smith"), closeSoftKeyboard());

        ViewInteraction countryInput = onView(withId(R.id.country_input));
        countryInput.perform(scrollTo(), click());

        DataInteraction checkedTextView4 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(234);
        checkedTextView4.perform(click());

        ViewInteraction addressOneInput = onView(withId(R.id.address_one_input));
        addressOneInput.perform(scrollTo(), replaceText("address1"), closeSoftKeyboard());

        ViewInteraction defaultInput3 = onView(withId(R.id.address_two_input));
        defaultInput3.perform(scrollTo(), replaceText("address2"), closeSoftKeyboard());

        ViewInteraction defaultInput4 = onView(withId(R.id.city_input));
        defaultInput4.perform(scrollTo(), longClick());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction defaultInput5 = onView(withId(R.id.city_input));
        defaultInput5.perform(scrollTo(), replaceText("town"), closeSoftKeyboard());

        ViewInteraction defaultInput6 = onView(withId(R.id.state_input));
        defaultInput6.perform(scrollTo(), replaceText("state"), closeSoftKeyboard());

        ViewInteraction defaultInput7 = onView(withId(R.id.zipcode_input));
        defaultInput7.perform(scrollTo(), replaceText("w1w w1w"), closeSoftKeyboard());

        ViewInteraction phoneInput = onView(withId(R.id.phone_input));
        phoneInput.perform(scrollTo(), replaceText("+44 7123456789"));

        ViewInteraction phoneInput2 = onView(
                allOf(withId(R.id.phone_input), withText("+44 7123456789"), isDisplayed()));
        phoneInput2.perform(closeSoftKeyboard());

        ViewInteraction button = onView(allOf(withId(R.id.done_button), withText("Done")));
        button.perform(scrollTo(), click());

        ViewInteraction touchlessViewPager2 = onView(allOf(withId(R.id.view_pager), isDisplayed()));
        touchlessViewPager2.perform(swipeRight());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("address1, address2, town, state"), isDisplayed()));
        textView.check(matches(withText("address1, address2, town, state")));

        pressBack();

        ViewInteraction button2 = onView(
                allOf(withId(R.id.pay_button), withText("Pay")));
        button2.perform(scrollTo(), click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("Token Created")).check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
