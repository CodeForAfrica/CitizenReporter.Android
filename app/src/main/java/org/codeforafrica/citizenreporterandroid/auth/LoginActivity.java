package org.codeforafrica.citizenreporterandroid.auth;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.data.models.User;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;
import org.codeforafrica.citizenreporterandroid.utils.APIClient;
import org.codeforafrica.citizenreporterandroid.utils.APIInterface;

import butterknife.BindView;
import lolodev.permissionswrapper.callback.OnRequestPermissionsCallBack;
import lolodev.permissionswrapper.wrapper.PermissionWrapper;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.codeforafrica.citizenreporterandroid.utils.NetworkHelper.isNetworkAvailable;
import static org.codeforafrica.citizenreporterandroid.utils.NetworkHelper.registerUserDetails;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_button)
    LoginButton loginButton;


    private CallbackManager callbackManager;
    private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;
    private Profile profile;
    private String first_name, last_name, fb_id;
    private Uri profile_pic;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private APIInterface apiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                updateProfile(newProfile);
            }
        };
        mProfileTracker.startTracking();


        setContentView(R.layout.activity_login);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        new PermissionWrapper.Builder(this)
                .addPermissions(new String[]{Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE})
                //enable rationale message with a custom message
                .addPermissionRationale("You need internet to log in and also query ")
                //show settings dialog,in this case with default message base on requested permission/s
                .addPermissionsGoSettings(true)
                //enable callback to know what option was choose
                .addRequestPermissionsCallBack(new OnRequestPermissionsCallBack() {
                    @Override
                    public void onGrant() {
                        if (isNetworkAvailable(LoginActivity.this)) {
                            startLoginProcess();
                        } else {
                            Toast.makeText(
                                    LoginActivity.this,
                                    "You currently do not have internet access",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onDenied(String permission) {

                    }
                }).build().request();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    public void startLoginProcess() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.GONE);
                Log.d("Facebook Login", "AccessToken: " + loginResult.getAccessToken().getToken());
                Profile prof = Profile.getCurrentProfile();
                updateProfile(prof);

                mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                        updateProfile(newProfile);

                    }
                };


            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    private void updateProfile(Profile newProfile) {
        if (newProfile != null) {
            Log.i("UPDATE", "updateProfile: update profile worked");
            Intent intent = new Intent(this, MainActivity.class);
            profile = newProfile;
            first_name = profile.getFirstName();
            last_name = profile.getLastName();
            profile_pic = profile.getProfilePictureUri(400, 400);
            fb_id = profile.getId();

            String name = first_name + " " + last_name;

            editor.putString("fb_id", fb_id);
            editor.putString("profile_url", profile_pic.toString());
            editor.putString("username", name);
            editor.commit();


            User newUser = new User(name, fb_id, profile_pic.toString());

            apiClient = APIClient.getApiClient();

            registerUserDetails(LoginActivity.this, apiClient, newUser);

            Log.d("First name", first_name);
            Log.d("Last name", last_name);
            Log.d("FB_ID", fb_id);
            Log.d("Profile_pic", profile_pic.toString());
            startActivity(intent);
        }
    }

}
