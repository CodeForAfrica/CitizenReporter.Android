package org.codeforafrica.citizenreporterandroid.ui.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.facebook.AccessToken;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.app.CitizenReporterApplication;
import org.codeforafrica.citizenreporterandroid.app.Constants;
import org.codeforafrica.citizenreporterandroid.data.DataManager;
import org.codeforafrica.citizenreporterandroid.ui.auth.LoginActivity;

public class BaseActivity extends AppCompatActivity {
  SharedPreferences sharedPreferences;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_FILENAME, MODE_PRIVATE);
    checkUserLoggedIn();
  }

  /*
   * Check if the user is logged in when any activity is opened
   */
  public void checkUserLoggedIn() {
    Log.d("Check User Login", "Checking ......");
    boolean isLoggedIn = sharedPreferences.getBoolean(Constants.PREF_KEY_USER_LOGGED_IN_MODE,
        false);
    if (!isLoggedIn) {
      startActivity(new Intent(this, LoginActivity.class));
      finish();
    }
  }
}
