package org.codeforafrica.citizenreporterandroid.ui.auth.signin;

import android.util.Log;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by Ahereza on 9/17/17.
 */

public class SignInPresenter implements SignInContract.Presenter {
  private static final String TAG = SignInPresenter.class.getSimpleName();
  SignInContract.View view;
  @Override public void signin(String email, String password) {
    view.showLoading();
    ParseUser.logInInBackground(email, password, new LogInCallback() {
      @Override public void done(ParseUser user, ParseException e) {
        if (e == null) {
          view.hideLoading();
          view.goToMainActivity();
        } else {
          Log.e(TAG, "done: ", e.fillInStackTrace());
          view.showValidationErrors(e.getLocalizedMessage());
        }
      }
    });

  }

  @Override public void attachView(SignInContract.View view) {
    this.view = view;
  }

  @Override public void detachView() {
    view = null;
  }

  @Override public void forgotPassword() {
    Log.d(TAG, "forgotPassword: click");
    view.goToPasswordReset();
  }
}
