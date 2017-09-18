package org.codeforafrica.citizenreporterandroid.ui.auth.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;
import org.codeforafrica.citizenreporterandroid.ui.auth.passwordRecovery.PasswordResetActivity;
import org.codeforafrica.citizenreporterandroid.utils.NetworkUtils;

public class SignInActivity extends AppCompatActivity implements SignInContract.View {
  private static final String TAG = SignInActivity.class.getSimpleName();
  @BindView(R.id.sign_in_progress) ProgressBar progressBar;
  @BindView(R.id.sign_in_email) AutoCompleteTextView emailField;
  @BindView(R.id.sign_in_password) EditText passwordField;
  @BindView(R.id.sign_in_button_done) Button signinButton;
  @BindView(R.id.sign_in_password_layout) TextInputLayout passwordLayout;
  @BindView(R.id.forgot_password) TextView forgotPassword;
  SignInContract.Presenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_siginin);
    ButterKnife.bind(this);
    presenter = new SignInPresenter();
    presenter.attachView(this);
  }

  @Override protected void onStart() {
    super.onStart();
  }

  @Override protected void onStop() {
    super.onStop();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.detachView();
  }

  @Override public void showLoading() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    progressBar.setVisibility(View.GONE);
  }

  @Override public void showValidationErrors(String error) {
    passwordLayout.setError(error);
  }

  @Override public void goToMainActivity() {
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
    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
    startActivity(intent);
    finish();
  }

  @Override public void goToPasswordReset() {
    Intent intent = new Intent(SignInActivity.this, PasswordResetActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.sign_in_button_done) public void signIn() {
    String password = passwordField.getText().toString();
    String email = emailField.getText().toString();
    if (NetworkUtils.isNetworkAvailable(this)) {
      presenter.signin(email, password);
    } else {
      Toast.makeText(this, R.string.no_active_internet, Toast.LENGTH_SHORT).show();
    }

  }

  @OnClick(R.id.forgot_password) public void clickForgotPassword() {
    Log.d(TAG, "forgotPassword: click");
    presenter.forgotPassword();
  }
}
