package checkout.checkout_android;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CardDetails_Test {

    @Rule
    public ActivityScenarioRule<DemoActivity> rule = new ActivityScenarioRule<>(DemoActivity.class);

    @Before
    public void setUp() {
        ActivityScenario<DemoActivity> scenario = rule.getScenario();
    }

    @Test
    public void All_Inputs_Empty() {
        ViewInteraction button = onView(allOf(withId(R.id.pay_button), withText("Pay")));
        button.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(
                        withId(R.id.textinput_error),
                        withText("The card number is invalid"),
                        isDisplayed()
                )
        );
        textView.check(matches(withText("The card number is invalid")));

        ViewInteraction textView2 = onView(
                allOf(
                        withId(R.id.textinput_error),
                        withText("Enter a valid Cvv"),
                        isDisplayed()
                )
        );
        textView2.check(matches(withText("Enter a valid Cvv")));
    }

    @Test
    public void Card_Input_Empty() {
        ViewInteraction defaultInput = onView(withId(R.id.cvv_input));
        defaultInput.perform(scrollTo(), replaceText("100"), closeSoftKeyboard());

        ViewInteraction button = onView(allOf(withId(R.id.pay_button), withText("Pay")));
        button.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(
                        withId(R.id.textinput_error),
                        withText("The card number is invalid"),
                        isDisplayed()
                )
        );
        textView.check(matches(withText("The card number is invalid")));

        ViewInteraction textView2 = onView(
                allOf(
                        withId(R.id.textinput_error),
                        withText("Enter a valid Cvv"),
                        isDisplayed()
                )
        );
        textView2.check(matches(withText("Enter a valid Cvv")));

        ViewInteraction textView3 = onView(
                allOf(
                        withId(R.id.textinput_error),
                        withText("Enter a valid Cvv"),
                        isDisplayed()
                )
        );
        textView3.check(matches(withText("Enter a valid Cvv")));

    }

    @Test
    public void Cvv_Input_Empty() {
        ViewInteraction cardInput = onView(withId(R.id.card_input));
        cardInput.perform(scrollTo(), replaceText("4242 4242 4242 4242"), closeSoftKeyboard());

        ViewInteraction button = onView(allOf(withId(R.id.pay_button), withText("Pay")));
        button.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(
                        withId(R.id.textinput_error),
                        withText("Enter a valid Cvv"),
                        isDisplayed()
                )
        );
        textView.check(matches(withText("Enter a valid Cvv")));

        ViewInteraction textView2 = onView(
                allOf(
                        withId(R.id.textinput_error),
                        withText("Enter a valid Cvv"),
                        isDisplayed()
                )
        );
        textView2.check(matches(withText("Enter a valid Cvv")));

    }

    @Test
    public void All_Inputs_Correct() throws InterruptedException {
        ViewInteraction cardInput = onView(withId(R.id.card_input));
        cardInput.perform(scrollTo(), click());

        ViewInteraction cardInput2 = onView(withId(R.id.card_input));
        cardInput2.perform(scrollTo(), replaceText("4242 4242 4242 4242"), closeSoftKeyboard());

        onView(withId(R.id.month_input)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(3).perform(click());

        onView(withId(R.id.year_input)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(3).perform(click());

        ViewInteraction defaultInput = onView(withId(R.id.cvv_input));
        defaultInput.perform(scrollTo(), replaceText("100"), closeSoftKeyboard());


        ViewInteraction button = onView(allOf(withId(R.id.pay_button), withText("Pay")));
        button.perform(scrollTo(), click());

        Thread.sleep(2000);

        onView(withText("Token Created")).check(matches(isDisplayed()));
    }
}
