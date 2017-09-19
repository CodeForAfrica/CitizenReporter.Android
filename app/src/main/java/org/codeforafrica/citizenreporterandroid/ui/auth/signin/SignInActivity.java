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
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.wang.avi.AVLoadingIndicatorView;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;
import org.codeforafrica.citizenreporterandroid.ui.auth.passwordRecovery.PasswordResetActivity;
import org.codeforafrica.citizenreporterandroid.utils.NetworkUtils;

public class SignInActivity extends AppCompatActivity implements SignInContract.View {
  private static final String TAG = SignInActivity.class.getSimpleName();
  AVLoadingIndicatorView loadingIndicatorView;
  @BindView(R.id.sign_in_email) AutoCompleteTextView emailField;
  @BindView(R.id.sign_in_password) EditText passwordField;
  @BindView(R.id.sign_in_button_done) Button signinButton;
  @BindView(R.id.sign_in_password_layout) TextInputLayout passwordLayout;
  @BindView(R.id.forgot_password) TextView forgotPassword;
  SignInContract.Presenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_siginin);
    loadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.sign_in_progress);
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
    Log.d(TAG, "showLoading: ");
    loadingIndicatorView.setVisibility(View.VISIBLE);
    loadingIndicatorView.show();
  }

  @Override public void hideLoading() {
    Log.d(TAG, "hideLoading: ");
    loadingIndicatorView.setVisibility(View.GONE);
    loadingIndicatorView.hide();
  }

  @Override public void showValidationErrors(String error) {
    passwordLayout.setError(error);
  }

  @Override public void goToMainActivity() {
    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
    startActivity(intent);
    finish();
  }

  @Override public void goToPasswordReset() {
    Intent intent = new Intent(SignInActivity.this, PasswordResetActivity.class);
    startActivity(intent);
  }

  @Override public void enableDoneButton() {

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
