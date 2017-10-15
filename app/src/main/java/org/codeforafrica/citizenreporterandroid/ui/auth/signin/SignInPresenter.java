package org.codeforafrica.citizenreporterandroid.ui.auth.signin;

import android.util.Log;

import com.flurry.android.FlurryAgent;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import org.codeforafrica.citizenreporterandroid.data.ParseHelper;
import org.codeforafrica.citizenreporterandroid.utils.AnalyticsHelper;

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
          ParseHelper.getParseAssignments();
          ParseHelper.getParseStories();
          // Track login
          FlurryAgent.logEvent(AnalyticsHelper.EVENT_LOGIN);
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

  @Override public void enableButton() {
    view.enableDoneButton();
  }
}
