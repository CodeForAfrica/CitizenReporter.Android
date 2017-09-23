package org.codeforafrica.citizenreporterandroid.ui.auth.signin;

import android.util.Log;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import org.codeforafrica.citizenreporterandroid.data.ParseHelper;
import org.codeforafrica.citizenreporterandroid.data.models.WordpressUser;
import org.codeforafrica.citizenreporterandroid.utils.APIClient;
import org.codeforafrica.citizenreporterandroid.utils.WordpressAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ahereza on 9/17/17.
 */

public class SignInPresenter implements SignInContract.Presenter {
  private static final String TAG = SignInPresenter.class.getSimpleName();
  WordpressAPI api;
  SignInContract.View view;

  @Override public void signin(final String email, final String password) {

    api = APIClient.getWordpressApiClient();
    view.showLoading();
    // check if the user was signed up on wordpress
    Call<WordpressUser> userVerifyCall = api.verifyWordpressUser(email, password);

    // first attempt to login
    try {
      Log.i(TAG, "signin: First login attempt");
      ParseUser.logIn(email, password);
      view.hideLoading();
      view.goToMainActivity();
    } catch (ParseException parseError) {
      // login failed so we check if user is a wordpress user
      Log.i(TAG, "signin: First login failed, not parse user");

      Log.i(TAG, "signin: Check if the user was signed on wordpress");
      userVerifyCall.enqueue(new Callback<WordpressUser>() {
        @Override
        public void onResponse(Call<WordpressUser> call, Response<WordpressUser> response) {
          WordpressUser user = response.body();
          ParseUser newUser = new ParseUser();
          newUser.setUsername(user.getEmail());
          newUser.setPassword(password);
          newUser.put("first_name", user.getFirstName());
          newUser.put("last_name", user.getLastName());

          newUser.signUpInBackground(new SignUpCallback() {
            @Override public void done(ParseException e) {
              if (e == null) {
                ParseUser.logInInBackground(email, password, new LogInCallback() {
                  @Override public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                      ParseHelper.getParseAssignments();
                      ParseHelper.getParseStories();
                      // TODO: 9/23/17 start saving old user stories in the background
                      view.hideLoading();
                      view.goToMainActivity();
                    } else {
                      Log.e(TAG, "done: ", e.fillInStackTrace());
                      view.hideLoading();
                      view.showValidationErrors(e.getLocalizedMessage());
                    }
                  }
                });
              }
            }
          });
          Log.i(TAG, "signin: sign up the new user successful");
          // if new user sign up is successful, login the user
        }

        @Override public void onFailure(Call<WordpressUser> call, Throwable t) {
          view.hideLoading();
          view.showValidationErrors("Invalid username/password");
        }
      });

      // since user is a wordpress user sign them up to pa

    }
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
