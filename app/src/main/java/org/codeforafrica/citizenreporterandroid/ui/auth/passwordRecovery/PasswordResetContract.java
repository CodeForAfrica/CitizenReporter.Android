package org.codeforafrica.citizenreporterandroid.ui.auth.passwordRecovery;

/**
 * Created by Ahereza on 9/17/17.
 */

public interface PasswordResetContract {
  interface View {
    void goToSignInActivity();
    void showSuccessMessage();
    void showError(String error);
  };
  interface Presenter {
    void resetPassword(String email);
    void attachView(PasswordResetContract.View view);
    void detachView();
  };
}
