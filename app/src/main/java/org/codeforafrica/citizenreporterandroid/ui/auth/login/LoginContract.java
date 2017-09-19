package org.codeforafrica.citizenreporterandroid.ui.auth.login;

/**
 * Created by Ahereza on 9/19/17.
 */

public class LoginContract {
  interface View {
    void showLoading();
    void hideLoading();
    void goToMainActivity();
    void gotToSignUpWithEmail();
    void goToSignInWithEmail();
  };

  interface Presenter {
    void attachView(LoginContract.View view);
    void detachView();
    void startFbLogin();
    void startMainActivity();
    void startSignUp();
    void startSignIn();
  };
}
