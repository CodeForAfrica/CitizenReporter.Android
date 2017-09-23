package org.codeforafrica.citizenreporterandroid.ui.auth.signin;

/**
 * Created by Ahereza on 9/17/17.
 */

public interface SignInContract {
  interface View {
    void showLoading();
    void hideLoading();
    void showValidationErrors(String error);
    void goToMainActivity();
    void goToPasswordReset();
    void enableDoneButton();
  };

  interface Presenter {
    void signin(String email, String password);
    void attachView(SignInContract.View view);
    void detachView();
    void forgotPassword();
    void enableButton();
    //void getWordpressUserDetails();
  }
}
