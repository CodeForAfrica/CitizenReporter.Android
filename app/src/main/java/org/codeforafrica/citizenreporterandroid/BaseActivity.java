package org.codeforafrica.citizenreporterandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;

import org.codeforafrica.citizenreporterandroid.auth.LoginActivity;
import org.codeforafrica.citizenreporterandroid.utils.APIInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUserLoggedIn();

    }

    /*
     * Check if the user is logged in when any activity is opened
     */
    public void checkUserLoggedIn(){
        Log.d("Check User Login", "Checking ......");
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

}
