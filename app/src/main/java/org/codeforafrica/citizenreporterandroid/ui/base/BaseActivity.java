package org.codeforafrica.citizenreporterandroid.ui.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.facebook.AccessToken;
import com.parse.ParseUser;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.app.CitizenReporterApplication;
import org.codeforafrica.citizenreporterandroid.app.Constants;
import org.codeforafrica.citizenreporterandroid.data.DataManager;
import org.codeforafrica.citizenreporterandroid.ui.auth.LoginActivity;

public class BaseActivity extends AppCompatActivity {
  private static final String TAG = BaseActivity.class.getSimpleName();
  ParseUser user;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    checkUserLoggedIn();
  }

  /*
   * Check if the user is logged in when any activity is opened
   */
  public void checkUserLoggedIn() {
    user = ParseUser.getCurrentUser();
    if (user ==null || !user.isAuthenticated()) {
      Log.d(TAG, "checkUserLoggedIn: not logged in");
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
      finish();
    }
  }
}
