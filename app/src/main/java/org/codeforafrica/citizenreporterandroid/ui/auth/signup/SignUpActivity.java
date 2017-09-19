package org.codeforafrica.citizenreporterandroid.ui.auth.signup;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.storyboard.StoryboardContract;
import org.codeforafrica.citizenreporterandroid.ui.auth.signin.SignInActivity;
import org.codeforafrica.citizenreporterandroid.utils.NetworkUtils;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {
  SignUpContract.Presenter presenter;
  @BindView(R.id.sign_up_progress) ProgressBar signUpProgress;
  @BindView(R.id.sign_up_email_address) AutoCompleteTextView emailField;
  @BindView(R.id.sign_up_name) AutoCompleteTextView nameField;
  @BindView(R.id.sign_up_password) EditText passwordField;
  @BindView(R.id.sign_up_password_layout) TextInputLayout passwordLayout;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
    presenter = new SignUpPresenter();
    presenter.attachView(this);
    ButterKnife.bind(this);
    this.hideLoading();
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

  @Override public void hideLoading() {
    signUpProgress.setVisibility(View.GONE);
  }

  @Override public void showLoading() {
    signUpProgress.setVisibility(View.VISIBLE);
  }

  @Override public void goToSignInActivity() {
    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
    startActivity(intent);
  }

  @Override public void showValidationErrors(String error) {
    passwordLayout.setError(error);
  }

  @Override public void showSuccessMessage() {
    Toast.makeText(this, "Your Account Successfully been Created", Toast.LENGTH_SHORT).show();
  }

  @OnClick(R.id.sign_up_done_button) public void signUpClicked() {
    String name = nameField.getText().toString();
    String email = emailField.getText().toString();
    String password = passwordField.getText().toString();
    if (NetworkUtils.isNetworkAvailable(this)) {
      presenter.signup(name, email, password);
    } else {
      Toast.makeText(this, "You have no active internet connection", Toast.LENGTH_SHORT).show();
    }

  }
}
