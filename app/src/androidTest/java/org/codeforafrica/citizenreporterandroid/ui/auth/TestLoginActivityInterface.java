package org.codeforafrica.citizenreporterandroid.ui.auth;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.ui.auth.login.LoginActivity;
import org.junit.Before;
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
import static junit.framework.TestCase.assertTrue;

/**
 * Created by johnpaulseremba on 18/10/2017.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestLoginActivityInterface {
	@Rule
	public ActivityTestRule<LoginActivity> loginActivityActivityTestRule =
			new ActivityTestRule<>(LoginActivity.class);

	private UiDevice mDevice;

	@Before
	public void setUp() throws Exception {
		mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
	}

	@Test
	public void testInterfaceElements() {
		onView(withId(R.id.facebook_login_button)).check(matches(isDisplayed()));
		onView(withId(R.id.google_login_button)).check(matches(isDisplayed()));
		onView(withId(R.id.email_login_button)).check(matches(isDisplayed()));
		onView(withId(R.id.signin_with_password)).check(matches(isDisplayed()));
	}

	@Test
	public void testLoginWithFacebook() {
		onView(withId(R.id.facebook_login_button))
				.check(matches(isDisplayed()))
				.perform(click());
		UiObject facebookApp = mDevice.findObject(new UiSelector().textContains("Login"));
		assertTrue(facebookApp.exists());
	}

	@Test
	public void testSignUp() {
		onView(withId(R.id.email_login_button))
				.check(matches(isDisplayed()))
				.perform(click());
		onView(withText("Sign Up")).check(matches(isDisplayed()));
		onView(withId(R.id.sign_up_name))
				.check(matches(isDisplayed()))
				.perform(typeText("Test Name"), closeSoftKeyboard());
		onView(withId(R.id.sign_up_email_address))
				.check(matches(isDisplayed()))
				.perform(typeText("test@test.com"), closeSoftKeyboard());
		onView(withId(R.id.sign_up_password))
				.check(matches(isDisplayed()))
				.perform(typeText("12345678"), closeSoftKeyboard());
	}

}
