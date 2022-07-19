package checkout.checkout_android;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
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
public class BillingDetails_Test {

    @Rule
    public ActivityScenarioRule<DemoActivity> rule = new ActivityScenarioRule<>(DemoActivity.class);

    @Before
    public void setUp() {
        ActivityScenario<DemoActivity> scenario = rule.getScenario();
    }

    @Test
    public void Save_Values_To_Billing_Input_When_Completed() {
        onView(withId(R.id.go_to_billing)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        ViewInteraction touchlessViewPager = onView(allOf(withId(R.id.view_pager), isDisplayed()));
        touchlessViewPager.perform(swipeLeft());

        ViewInteraction defaultInput = onView(withId(R.id.name_input));
        defaultInput.perform(scrollTo(), replaceText("test name"), closeSoftKeyboard());

        ViewInteraction countryInput = onView(withId(R.id.country_input));
        countryInput.perform(scrollTo(), click());

        DataInteraction checkedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(234);
        checkedTextView2.perform(click());

        ViewInteraction addressOneInput = onView(withId(R.id.address_one_input));
        addressOneInput.perform(scrollTo(), click());

        ViewInteraction addressOneInput2 = onView(withId(R.id.address_one_input));
        addressOneInput2.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction defaultInput2 = onView(withId(R.id.address_two_input));
        defaultInput2.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction defaultInput3 = onView(withId(R.id.city_input));
        defaultInput3.perform(scrollTo(), replaceText("w1w w1w"), closeSoftKeyboard());

        ViewInteraction defaultInput4 = onView(withId(R.id.state_input));
        defaultInput4.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction defaultInput5 = onView(withId(R.id.zipcode_input));
        defaultInput5.perform(scrollTo(), replaceText("w1ww1w"), closeSoftKeyboard());

        ViewInteraction phoneInput = onView(allOf(withId(R.id.phone_input)));
        phoneInput.perform(scrollTo(), replaceText("+44 12345667"));

        ViewInteraction phoneInput2 = onView(
                allOf(withId(R.id.phone_input), withText("+44 12345667"), isDisplayed()));
        phoneInput2.perform(closeSoftKeyboard());

        ViewInteraction button = onView(allOf(withId(R.id.done_button), withText("Done")));
        button.perform(scrollTo(), click());

        ViewInteraction touchlessViewPager2 = onView(allOf(withId(R.id.view_pager), isDisplayed()));
        touchlessViewPager2.perform(swipeRight());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("test, test, w1w w1w, test"), isDisplayed()));
        textView.check(matches(withText("test, test, w1w w1w, test")));

    }

    @Test
    public void Clear_Billing_Values_On_Clear() throws InterruptedException {
        onView(withId(R.id.go_to_billing)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        ViewInteraction touchlessViewPager = onView(allOf(withId(R.id.view_pager), isDisplayed()));
        touchlessViewPager.perform(swipeLeft());

        ViewInteraction defaultInput = onView(withId(R.id.name_input));
        defaultInput.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction button = onView(allOf(withId(R.id.clear_button), withText("Clear")));
        button.perform(scrollTo(), click());

        ViewInteraction touchlessViewPager2 = onView(allOf(withId(R.id.view_pager), isDisplayed()));
        touchlessViewPager2.perform(swipeRight());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("SELECT"), isDisplayed()));
        textView.check(matches(withText("SELECT")));

        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        ViewInteraction touchlessViewPager3 = onView(allOf(withId(R.id.view_pager), isDisplayed()));
        touchlessViewPager3.perform(swipeLeft());

        ViewInteraction editText = onView(allOf(withId(R.id.name_input), isDisplayed()));
        editText.check(matches(withText("")));

    }

    @Test
    public void Retain_Value_Unless_Cleared() throws InterruptedException {
        onView(withId(R.id.go_to_billing)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        ViewInteraction touchlessViewPager = onView(allOf(withId(R.id.view_pager), isDisplayed()));
        touchlessViewPager.perform(swipeLeft());

        ViewInteraction defaultInput = onView(withId(R.id.name_input));
        defaultInput.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.my_toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction touchlessViewPager2 = onView(allOf(withId(R.id.view_pager), isDisplayed()));
        touchlessViewPager2.perform(swipeRight());

        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        ViewInteraction touchlessViewPager3 = onView(allOf(withId(R.id.view_pager), isDisplayed()));
        touchlessViewPager3.perform(swipeLeft());

        ViewInteraction editText = onView(
                allOf(withId(R.id.name_input), withText("test"), isDisplayed()));
        editText.check(matches(withText("test")));

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
