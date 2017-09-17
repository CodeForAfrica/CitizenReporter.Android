package org.codeforafrica.citizenreporterandroid.main.assignments;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Jeffkungu on 08/09/2017.
 */

/**
 * This class generates the registration token once the app is strted.
 */
public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String REGISTRATION_TOKEN = "REGISTRATION_TOKEN";
    @Override
    public void onTokenRefresh() {
        
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(REGISTRATION_TOKEN, refreshedToken);
    }
}
