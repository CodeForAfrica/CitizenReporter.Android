package org.codeforafrica.citizenreporterandroid.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.facebook.AccessToken;
import org.codeforafrica.citizenreporterandroid.ui.auth.LoginActivity;

public class BaseActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    checkUserLoggedIn();
  }

  /*
   * Check if the user is logged in when any activity is opened
   */
  public void checkUserLoggedIn() {
    Log.d("Check User Login", "Checking ......");
    AccessToken token = AccessToken.getCurrentAccessToken();
    if (token == null) {
      startActivity(new Intent(this, LoginActivity.class));
      finish();
    }
  }
}
