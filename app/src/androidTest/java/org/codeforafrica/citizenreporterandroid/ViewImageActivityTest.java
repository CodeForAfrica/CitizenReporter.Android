package org.codeforafrica.citizenreporterandroid;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import java.util.ArrayList;
import junit.framework.Assert;
import org.codeforafrica.citizenreporterandroid.camera.CameraActivity;
import org.codeforafrica.citizenreporterandroid.camera.ViewImageActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by johnpaulseremba on 11/10/2017.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ViewImageActivityTest {
	private String uri;

	@Rule
	public ActivityTestRule<CameraActivity> mCameraActivity =
			new ActivityTestRule<>(CameraActivity.class);

	private ActivityTestRule<ViewImageActivity> mViewImageActivity =
			new ActivityTestRule<>(ViewImageActivity.class);

	@Before
	public void setupMockImage() {
		Resources resources = mCameraActivity.getActivity().getResources();
		uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
				+ resources.getResourcePackageName(R.mipmap.ic_launcher) + "/"
				+ resources.getResourceTypeName(R.mipmap.ic_launcher) + "/"
				+ resources.getResourceEntryName(R.mipmap.ic_launcher)
		).toString();
		Intent resultData = new Intent();
		resultData.putExtra(resources.getString(R.string.constant_file_location), uri);
		mViewImageActivity.launchActivity(resultData);
	}

	@Test
	public void testInterface() {
		ArrayList<Integer> visibleElements = new ArrayList<Integer>() {
			{
				add(R.id.btn_rotate_clockwise);
				add(R.id.btn_rotate_anti_clockwise);
				add(R.id.img_save_btn);
				add(R.id.img_close_btn);
				add(R.id.img_view);
				add(R.id.img_btn_bg_2);
			}
		};
		checkIsDisplayed(visibleElements);
	}

	@Test
	public void testRotateClockwise() {
		onView(withId(R.id.btn_rotate_clockwise)).check(matches(isDisplayed())).perform(click());
		Assert.assertTrue(mViewImageActivity.getActivity().checkRotation(90));
	}

	@Test
	public void testRotateAntiClockwise() {
		onView(withId(R.id.btn_rotate_anti_clockwise)).check(matches(isDisplayed())).perform(click());
		Assert.assertTrue(mViewImageActivity.getActivity().checkRotation(-90));
	}

	@Test
	public void testCloseImageView() {
		final Resources resources = mViewImageActivity.getActivity().getResources();
		onView(withId(R.id.img_close_btn)).check(matches(isDisplayed())).perform(click());
		onView(withText(resources.getString(R.string.msg_deletion_failure)))
				.inRoot(withDecorView(not(is(mViewImageActivity.getActivity().getWindow().getDecorView()))))
				.check(matches(isDisplayed()));
		pressBack();
		onView(withId(R.id.tv_camera)).check(matches(isDisplayed()));
	}

	@Test
	public void testSaveImage() {
		final Resources resources = mViewImageActivity.getActivity().getResources();
		onView(withId(R.id.img_save_btn)).check(matches(isDisplayed())).perform(click());
		Intent resultData = new Intent();
		resultData.putExtra(resources.getString(R.string.constant_image_path), uri);
		Instrumentation.ActivityResult result =
				new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
		Intents.init();
		mCameraActivity.launchActivity(new Intent());
		intending(hasComponent(CameraActivity.class.getName())).respondWith(result);
		intended(hasComponent(CameraActivity.class.getName()));
		Intents.release();
	}

	public void checkIsDisplayed(ArrayList<Integer> list) {
		for (Integer id : list) {
			onView(withId(id))
					.check(matches(isDisplayed()));
		}
	}
}
