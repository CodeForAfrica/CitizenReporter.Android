package org.codeforafrica.citizenreporterandroid.ui.auth.login;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.facebook.Profile;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import org.codeforafrica.citizenreporterandroid.data.ParseHelper;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;

/**
 * Created by Ahereza on 9/19/17.
 */

public class LoginActivityPresenter implements LoginContract.Presenter {
  LoginContract.View view;
  Profile profile;

  @Override public void attachView(LoginContract.View view) {
    this.view = view;
  }

  @Override public void detachView() {
    view = null;
  }

  @Override public void startFbLogin() {


  }

  @Override public void startMainActivity() {
    view.goToMainActivity();
  }

  @Override public void startSignUp() {
    view.gotToSignUpWithEmail();
  }

  @Override public void startSignIn() {
    view.goToSignInWithEmail();
  }
}
