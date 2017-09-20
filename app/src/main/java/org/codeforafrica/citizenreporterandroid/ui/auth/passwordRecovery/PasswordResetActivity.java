package org.codeforafrica.citizenreporterandroid.ui.auth.passwordRecovery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.ui.auth.signin.SignInActivity;

public class PasswordResetActivity extends AppCompatActivity implements PasswordResetContract.View {
  PasswordResetContract.Presenter presenter;

  @BindView(R.id.email_rest_tv) AutoCompleteTextView emailField;
  @BindView(R.id.password_reset_success) TextView successMessage;
  @BindView(R.id.password_reset_error) TextView errorMessage;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_password_reset);
    presenter = new PasswordResetPresenter();
    presenter.attachView(this);
    ButterKnife.bind(this);
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

  @Override public void goToSignInActivity() {
    Intent intent = new Intent(PasswordResetActivity.this, SignInActivity.class);
    startActivity(intent);
  }

  @Override public void showSuccessMessage() {
    successMessage.setVisibility(View.VISIBLE);
  }

  @Override public void showError(String error) {
    errorMessage.setText(error);
    errorMessage.setVisibility(View.VISIBLE);

  }

  @OnClick(R.id.reset_password_button) public void resetPassword() {
    presenter.resetPassword(emailField.getText().toString());
  }
}
