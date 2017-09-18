package org.codeforafrica.citizenreporterandroid.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.facebook.Profile;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.app.CitizenReporterApplication;
import org.codeforafrica.citizenreporterandroid.data.DataManager;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;
import org.codeforafrica.citizenreporterandroid.ui.auth.signin.SignInActivity;
import org.codeforafrica.citizenreporterandroid.ui.auth.signup.SignUpActivity;

public class LoginActivity extends AppCompatActivity {

  private static final String TAG = LoginActivity.class.getSimpleName();
  @BindView(R.id.facebook_login_button) Button facebookLoginButton;
  @Inject DataManager manager;

  Profile profile;

  private List<String> permissions = Arrays.asList("public_profile", "email");

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((CitizenReporterApplication) getApplication()).getAppComponent().inject(this);


    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);
    facebookLoginButton = (Button) findViewById(R.id.facebook_login_button);

    facebookLoginButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        v.setVisibility(View.INVISIBLE);
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
                  ParseQuery<ParseObject> query = ParseQuery.getQuery("Assignment");
                  query.findInBackground(new FindCallback<ParseObject>() {
                    @Override public void done(List<ParseObject> objects, ParseException e) {
                      if (e == null) {
                        try {
                          Log.d(TAG, "Got all assignments: " + objects.size());
                          ParseObject.pinAllInBackground(objects);
                        } catch (NullPointerException e1) {
                          e1.printStackTrace();
                        }
                      }

                    }
                  });

                  ParseQuery<ParseObject> storiesQuery = ParseQuery.getQuery("Story");
                  storiesQuery.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> storyList, ParseException e) {
                      if (e == null) {
                        try {
                          Log.d("Stories", "done: storyList " + storyList.size());
                          ParseObject.pinAllInBackground(storyList);
                        } catch (NullPointerException e1) {
                          e1.printStackTrace();
                        }
                      }


                    }
                  });
                  Log.d("MyApp", "done: intent");
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
    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.signin_with_password) public void goToSignInWithPassword() {
    Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
    startActivity(intent);
  }

}