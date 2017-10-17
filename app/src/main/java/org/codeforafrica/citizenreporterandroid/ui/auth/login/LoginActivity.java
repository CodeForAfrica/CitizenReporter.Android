package org.codeforafrica.citizenreporterandroid.ui.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.facebook.Profile;

import com.flurry.android.FlurryAgent;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;

import com.parse.ParseUser;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.app.CitizenReporterApplication;
import org.codeforafrica.citizenreporterandroid.data.ParseHelper;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;
import org.codeforafrica.citizenreporterandroid.ui.auth.signin.SignInActivity;
import org.codeforafrica.citizenreporterandroid.ui.auth.signup.SignUpActivity;
import org.codeforafrica.citizenreporterandroid.utils.AnalyticsHelper;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

  private static final String TAG = LoginActivity.class.getSimpleName();
  @BindView(R.id.facebook_login_button) Button facebookLoginButton;
  @BindView(R.id.aviLoader) AVLoadingIndicatorView loadingIndicatorView;

  Profile profile;
  LoginContract.Presenter presenter;

  private List<String> permissions = Arrays.asList("public_profile", "email");

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((CitizenReporterApplication) getApplication()).getAppComponent().inject(this);
    presenter = new LoginActivityPresenter();
    presenter.attachView(this);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);
    facebookLoginButton = (Button) findViewById(R.id.facebook_login_button);


    facebookLoginButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        loadingIndicatorView.setVisibility(View.GONE);
        loadingIndicatorView.hide();
        v.setVisibility(View.INVISIBLE);
        ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, permissions,
            new LogInCallback() {
              @Override public void done(ParseUser user, ParseException err) {
                if (user == null) {
                  Toast.makeText(LoginActivity.this, "You cancelled facebook login",
                      Toast.LENGTH_SHORT).show();
                  Log.d(TAG, "Uh oh. The user cancelled the Facebook login.");
                } else {

                  if (user.isNew()) {
                    // Track Facebook sign-up
                    FlurryAgent.logEvent(AnalyticsHelper.EVENT_FACEBOOK_SIGN_UP);
                  }
                  
                  Log.d(TAG, "User signed up and logged in through Facebook!");
                  profile = Profile.getCurrentProfile();
                  if (profile != null) {
                    String first_name = profile.getFirstName();
                    String last_name = profile.getLastName();
                    Log.d("MyApp", "done: " + last_name);
                    user.put("first_name", first_name);
                    user.put("last_name", last_name);

                    user.saveEventually();
                  }
                  ParseHelper.getParseAssignments();
                  ParseHelper.getParseStories();
                  Log.d("MyApp", "done: intent");
                  // Track login
                  FlurryAgent.logEvent(AnalyticsHelper.EVENT_LOGIN);
                  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                  startActivity(intent);
                  finish();
                  Log.d("MyApp", "done: intent done");
                }
              }
            });
        v.setVisibility(View.VISIBLE);
      }
    });
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
  }

  @Override protected void onResume() {
    super.onResume();
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @OnClick(R.id.email_login_button)
  public void goToSignUp() {
    presenter.startSignUp();
  }

  @OnClick(R.id.signin_with_password) public void goToSignInWithPassword() {
    presenter.startSignIn();
  }

  @Override public void showLoading() {
    loadingIndicatorView.setVisibility(View.VISIBLE);
    loadingIndicatorView.show();
  }

  @Override public void hideLoading() {
    loadingIndicatorView.setVisibility(View.GONE);
    loadingIndicatorView.hide();
  }

  @Override public void goToMainActivity() {
    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
    startActivity(intent);
    finish();
  }

  @Override public void gotToSignUpWithEmail() {
    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
    startActivity(intent);
  }

  @Override public void goToSignInWithEmail() {
    Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
    startActivity(intent);
  }
}