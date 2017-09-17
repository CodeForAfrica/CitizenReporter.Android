package org.codeforafrica.citizenreporterandroid.ui.auth.passwordRecovery;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

/**
 * Created by Ahereza on 9/17/17.
 */

public class PasswordResetPresenter implements PasswordResetContract.Presenter {
  PasswordResetContract.View view;

  @Override public void resetPassword(String email) {
    ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
      @Override public void done(ParseException e) {
        if (e == null) {
          view.showSuccessMessage();
        } else {
          view.showError(e.getLocalizedMessage());
        }
      }
    });
  }

  @Override public void attachView(PasswordResetContract.View view) {
    this.view = view;
  }

  @Override public void detachView() {
    view = null;
  }
}
