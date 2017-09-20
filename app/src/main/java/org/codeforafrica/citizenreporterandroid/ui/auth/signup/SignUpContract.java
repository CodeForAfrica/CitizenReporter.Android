package org.codeforafrica.citizenreporterandroid.ui.auth.signup;

/**
 * Created by Ahereza on 9/17/17.
 */

public interface SignUpContract {
  interface View {
    void hideLoading();
    void showLoading();
    void goToSignInActivity();
    void showValidationErrors(String error);
    void showSuccessMessage();
  };
  interface Presenter {
    void signup(String name, String email, String password);
    void attachView(SignUpContract.View view);
    void detachView();
  };
}
