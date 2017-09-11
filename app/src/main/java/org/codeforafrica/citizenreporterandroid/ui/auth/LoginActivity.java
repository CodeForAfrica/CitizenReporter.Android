package org.codeforafrica.citizenreporterandroid.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.facebook.Profile;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.app.CitizenReporterApplication;
import org.codeforafrica.citizenreporterandroid.data.DataManager;
import org.codeforafrica.citizenreporterandroid.data.models.User;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;
import org.codeforafrica.citizenreporterandroid.utils.APIClient;
import org.codeforafrica.citizenreporterandroid.utils.CReporterAPI;

import static org.codeforafrica.citizenreporterandroid.utils.NetworkHelper.registerUserDetails;

public class LoginActivity extends AppCompatActivity
    implements GoogleApiClient.OnConnectionFailedListener {

  private static final String TAG = LoginActivity.class.getSimpleName();
  @BindView(R.id.login_button) LoginButton loginButton;
  @BindView(R.id.google_sign_in) SignInButton googleButton;
  @Inject DataManager manager;

  Profile profile;

  private List<String> permissions = Arrays.asList("public_profile", "email");

  private CReporterAPI apiClient;

  private GoogleApiClient googleApiClient;
  private static final int REQUEST_CODE = 1;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((CitizenReporterApplication) getApplication()).getAppComponent().inject(this);

    apiClient = APIClient.getApiClient();

    setContentView(R.layout.activity_login);
    loginButton = (LoginButton) findViewById(R.id.login_button);
    googleButton = (SignInButton) findViewById(R.id.google_sign_in);

    GoogleSignInOptions googleSignInOptions =
        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
    googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
        .build();

    googleButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Log.d(TAG, "onClick: google clicked");
        googleSignIn();
      }
    });

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, permissions,
            new LogInCallback() {
              @Override public void done(ParseUser user, ParseException err) {
                if (user == null) {
                  Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else {
                  Log.d("MyApp", "User signed up and logged in through Facebook!");
                  profile = Profile.getCurrentProfile();
                  if (profile != null) {
                    String first_name = profile.getFirstName();
                    String last_name = profile.getLastName();
                    Log.d("MyApp", "done: " + last_name);
                    user.put("first_name", first_name);
                    user.put("last_name", last_name);

                    user.saveEventually();
                  }
                  Log.d("MyApp", "done: intent");
                  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                  startActivity(intent);
                  Log.d("MyApp", "done: intent done");
                }
              }
            });
      }
    });
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    //callbackManager.onActivityResult(requestCode, resultCode, data);

    if (requestCode == REQUEST_CODE) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      signInResult(result);
    }
  }


  @Override protected void onResume() {
    super.onResume();
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }


  @OnClick(R.id.google_sign_in) public void clickGoogleSignIn() {
    Log.d(TAG, "clickGoogleSignIn: Google button clicked");

    googleSignIn();
  }

  @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }

  private void googleSignIn() {
    Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    startActivityForResult(intent, REQUEST_CODE);
  }

  private void signInResult(GoogleSignInResult result) {

    if (result.isSuccess()) {
      GoogleSignInAccount account = result.getSignInAccount();
      String name = account.getDisplayName();
      String uid = account.getId();
      String image_url = "http://www.freeiconspng.com/uploads/account-icon-21.png";
      try {
        image_url = account.getPhotoUrl().toString();
      } catch (Exception e) {
        e.printStackTrace();
      }

      manager.setCurrentUsername(name);
      manager.setCurrentUserProfilePicUrl(image_url);
      manager.setCurrentUserUID(uid);

      User user = new User(name, uid, image_url);
      Log.d(TAG, "signInResult: User saved");

      registerUserDetails(LoginActivity.this, apiClient, user);

      manager.setCurrentUserAsLoggedInMode();

      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
    }
  }
}