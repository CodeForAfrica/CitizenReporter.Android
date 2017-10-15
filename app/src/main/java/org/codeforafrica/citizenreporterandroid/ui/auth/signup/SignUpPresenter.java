package org.codeforafrica.citizenreporterandroid.ui.auth.signup;

import android.util.Log;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.codeforafrica.citizenreporterandroid.utils.AnalyticsHelper;

import java.util.HashMap;

/**
 * Created by Ahereza on 9/17/17.
 */

public class SignUpPresenter implements SignUpContract.Presenter{

  SignUpContract.View view;

  @Override public void signup(String name, String email, String password) {
    view.showLoading();
    ParseUser user = new ParseUser();
    user.setEmail(email);
    user.setUsername(email);
    user.setPassword(password);
    user.put("name", name);
    user.signUpInBackground(new SignUpCallback() {
      @Override public void done(ParseException e) {
        if (e == null) {
          FlurryAgent.logEvent(AnalyticsHelper.EVENT_PASSWORD_SIGN_UP);
          view.showSuccessMessage();
          view.goToSignInActivity();
        } else {
          view.showValidationErrors(e.getLocalizedMessage());
        }
      }
    });

  }

  @Override public void attachView(SignUpContract.View view) {
    this.view = view;
  }

  @Override public void detachView() {
    view = null;
  }
}
