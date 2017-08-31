package org.codeforafrica.citizenreporterandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

import org.codeforafrica.citizenreporterandroid.main.MainActivity;

public class SplashActivity extends AppCompatActivity implements SplashActivityView {
    private SharedPreferences preferences;
    private SplashActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        presenter = new SplashActivityPresenter(this);
        presenter.startNextActivity();
    }


    @Override
    public void goToNextActivity() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!preferences.getBoolean("OnboardingActivity.ONBOARDING_COMPLETE", false)) {
            startActivity(new Intent(this, OnboardingActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
