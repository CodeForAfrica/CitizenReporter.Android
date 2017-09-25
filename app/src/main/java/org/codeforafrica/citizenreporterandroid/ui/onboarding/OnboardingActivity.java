package org.codeforafrica.citizenreporterandroid.ui.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.ui.auth.login.LoginActivity;

public class OnboardingActivity extends AppIntro implements OnboardingActivityView {
  private SharedPreferences sharedPreferences;
  private SharedPreferences.Editor editor;
  private OnboardingActivityPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    presenter = new OnboardingActivityPresenter(this);
    editor = sharedPreferences.edit();
    addSlide(
        AppIntroFragment.newInstance("View Current Assignments",
            getString(R.string.assignments_description),
            R.drawable.view_assignments,
            getResources().getColor(R.color.white_trans)));
    addSlide(
        AppIntroFragment.newInstance("Capture Media and Upload your report", getString(R.string
                .capture_media_desc), R.drawable
                .fill_questionaire,
            getResources().getColor(R.color.white_trans)));
    addSlide(
        AppIntroFragment.newInstance("Have your story published", "", R.drawable
                .published_stories,
            getResources().getColor(R.color.white_trans)));
  }

  @Override public void onSkipPressed(Fragment currentFragment) {
    super.onSkipPressed(currentFragment);
    onboardingComplete();
    presenter.proceedToLogin();
  }

  @Override public void onDonePressed(Fragment currentFragment) {
    super.onDonePressed(currentFragment);
    onboardingComplete();
    presenter.proceedToLogin();
  }

  public void onboardingComplete() {
    editor.putBoolean("OnboardingActivity.ONBOARDING_COMPLETE", true);
    editor.commit();
  }

  @Override public void startLoginActivity() {
    startActivity(new Intent(this, LoginActivity.class));
    finish();
  }
}
