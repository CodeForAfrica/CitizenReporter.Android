package org.codeforafrica.citizenreporterandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.login.LoginManager;

import org.codeforafrica.citizenreporterandroid.auth.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.support_menu_item)
    public void startSupportChannelActivity(){
        startActivity(new Intent(this, SupportChannelActivity.class));
    }

    @OnClick(R.id.about_menu_item)
    public void startAboutActivity(){
        startActivity(new Intent(this, AboutActivity.class));
    }

    @OnClick(R.id.feedback_menu_item)
    public void startFeedbackActivity(){
        startActivity(new Intent(this, FeedbackActivity.class));
    }

    @OnClick(R.id.facebook_button_logout)
    public void facebookLogout(){
        LoginManager.getInstance().logOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


}