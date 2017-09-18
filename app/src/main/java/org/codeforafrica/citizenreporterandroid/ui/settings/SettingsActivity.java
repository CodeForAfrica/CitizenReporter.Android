package org.codeforafrica.citizenreporterandroid.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.facebook.login.LoginManager;
import com.parse.ParseUser;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.SupportChannelActivity;
import org.codeforafrica.citizenreporterandroid.ui.auth.LoginActivity;
import org.codeforafrica.citizenreporterandroid.ui.about.AboutActivity;
import org.codeforafrica.citizenreporterandroid.ui.base.BaseActivity;
import org.codeforafrica.citizenreporterandroid.ui.feedback.FeedbackActivity;

public class SettingsActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_settings);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.support_menu_item) public void startSupportChannelActivity() {
    startActivity(new Intent(this, SupportChannelActivity.class));
  }

  @OnClick(R.id.about_menu_item) public void startAboutActivity() {
    startActivity(new Intent(this, AboutActivity.class));
  }

  @OnClick(R.id.feedback_menu_item) public void startFeedbackActivity() {
    startActivity(new Intent(this, FeedbackActivity.class));
  }

  @OnClick(R.id.button_logout) public void logout() {
    LoginManager.getInstance().logOut();
    ParseUser.logOut();
    startActivity(new Intent(this, LoginActivity.class));
    finish();
  }
}