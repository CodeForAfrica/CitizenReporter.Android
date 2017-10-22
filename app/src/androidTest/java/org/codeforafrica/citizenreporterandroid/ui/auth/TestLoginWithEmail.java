package org.codeforafrica.citizenreporterandroid.ui.auth;

import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.ui.auth.signin.SignInActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by johnpaulseremba on 22/10/2017.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestLoginWithEmail {
	@Rule
	public IntentsTestRule<SignInActivity> loginWithEmailTestRule =
			new IntentsTestRule<SignInActivity>(SignInActivity.class);

	@Test
	public void testLoginWithEmail() {
		onView(withId(R.id.sign_in_email))
				.check(matches(isDisplayed()))
				.perform(typeText("test@test.com"), closeSoftKeyboard());
		onView(withId(R.id.sign_in_password))
				.check(matches(isDisplayed()))
				.perform(typeText("12345678"), closeSoftKeyboard());
		onView(withId(R.id.sign_in_button_done))
				.check(matches(isDisplayed()))
				.perform(click());
		onView(withText("Citizen Reporter")).check(matches(isDisplayed()));
	}
}
