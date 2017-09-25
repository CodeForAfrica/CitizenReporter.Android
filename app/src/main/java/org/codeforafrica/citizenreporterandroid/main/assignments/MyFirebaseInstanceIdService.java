package org.codeforafrica.citizenreporterandroid.main.assignments;

import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.parse.ParseUser;

/**
 * Created by Ahereza on 9/18/17.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
  private static final String REGISTRATION_TOKEN = "REGISTRATION_TOKEN";
  @Override
  public void onTokenRefresh() {

    super.onTokenRefresh();
    String token = FirebaseInstanceId.getInstance().getToken();
    Log.d("FIREBASE TOKEN: ", " " + token);
    ParseUser user = ParseUser.getCurrentUser();
    if (token != null && user != null) {
      user.put("fcm_token", token);
      user.saveEventually();
    }


  }
}
