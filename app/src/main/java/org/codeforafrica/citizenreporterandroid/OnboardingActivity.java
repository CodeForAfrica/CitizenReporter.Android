package org.codeforafrica.citizenreporterandroid;

import android.content.Intent;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import org.codeforafrica.citizenreporterandroid.auth.LoginActivity;

public class OnboardingActivity extends AppIntro implements OnboardingActivityView{
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private OnboardingActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        presenter = new OnboardingActivityPresenter(this);
        editor = sharedPreferences.edit();
        addSlide(AppIntroFragment.newInstance("Welcome",
                "Description", R.drawable.cfa_transparent_blue_back,
                getResources().getColor(R.color.cfAfrica_blue)));
        addSlide(AppIntroFragment.newInstance("Welcome",
                "Description", R.drawable.cfa_transparent_blue_back,
                getResources().getColor(R.color.cfAfrica_blue)));
        addSlide(AppIntroFragment.newInstance("Welcome",
                "Description", R.drawable.cfa_transparent_blue_back,
                getResources().getColor(R.color.cfAfrica_blue)));

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        onboardingComplete();
        presenter.proceedToLogin();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        onboardingComplete();
    }

    public void onboardingComplete(){
        editor.putBoolean("OnboardingActivity.ONBOARDING_COMPLETE", true);
        editor.commit();
    }


    @Override
    public void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
        presenter.proceedToLogin();
    }
}
