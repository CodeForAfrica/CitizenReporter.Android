package org.codeforafrica.citizenreporterandroid.ui.auth.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;

public class SignInActivity extends AppCompatActivity implements SignInContract.View {
  @BindView(R.id.sign_in_progress) ProgressBar progressBar;
  @BindView(R.id.sign_in_email) AutoCompleteTextView emailField;
  @BindView(R.id.sign_in_password) EditText passwordField;
  @BindView(R.id.sign_in_button_done) Button signinButton;
  @BindView(R.id.sign_in_password_layout) TextInputLayout passwordLayout;
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
    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.sign_in_button_done) public void signIn() {
    String password = passwordField.getText().toString();
    String email = emailField.getText().toString();
    presenter.signin(email, password);
  }
}
