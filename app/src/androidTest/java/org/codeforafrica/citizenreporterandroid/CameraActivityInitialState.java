package org.codeforafrica.citizenreporterandroid;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import org.codeforafrica.citizenreporterandroid.camera.CameraActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CameraActivityInitialState {

	@Rule
	public ActivityTestRule<CameraActivity> mActivityTestRule =
			new ActivityTestRule<>(CameraActivity.class);

	@Test
	public void cameraActivityInitialState() {
		onView(withId(R.id.img_overlay_toggle))
				.check(matches(isDisplayed()));

		onView(withId(R.id.tv_camera))
				.check(matches(isDisplayed()));

		onView(withId(R.id.img_overlay))
				.check(matches(isDisplayed()));
	}

	@Test
	public void toggleOverlayVisibility() {
		onView(withId(R.id.img_overlay_toggle))
				.perform(click());

		onView(withId(R.id.img_overlay))
				.check(matches(not(isDisplayed())));
	}

	@Test
	public void absenceOfOtherIcons() {
		onView(withId(R.id.img_capture))
				.check(matches(not(isDisplayed())));

		onView(withId(R.id.img_flash_btn))
				.check(matches(not(isDisplayed())));

		onView(withId(R.id.img_switch_camera))
				.check(matches(not(isDisplayed())));

		onView(withId(R.id.img_gallery))
				.check(matches(not(isDisplayed())));

		onView(withId(R.id.img_effects_btn))
				.check(matches(not(isDisplayed())));

		onView(withId(R.id.img_wb_btn))
				.check(matches(not(isDisplayed())));

		onView(withId(R.id.seekbar_light))
				.check(matches(not(isDisplayed())));

		onView(withId(R.id.sw_swipe_1))
				.check(matches(not(isDisplayed())));
		onView(withId(R.id.sw_swipe_2))
				.check(matches(not(isDisplayed())));
		onView(withId(R.id.sw_swipe_3))
				.check(matches(not(isDisplayed())));
		onView(withId(R.id.sw_swipe_4))
				.check(matches(not(isDisplayed())));
		onView(withId(R.id.sw_swipe_5))
				.check(matches(not(isDisplayed())));

		onView(withId(R.id.txt_swipe_caption))
				.check(matches(not(isDisplayed())));
	}

}
