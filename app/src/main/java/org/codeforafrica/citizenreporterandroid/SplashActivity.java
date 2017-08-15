package org.codeforafrica.citizenreporterandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;

import io.fabric.sdk.android.Fabric;
import org.codeforafrica.citizenreporterandroid.auth.LoginActivity;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        initialCheck();
    }

    private void initialCheck() {
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
