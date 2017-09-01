package org.codeforafrica.citizenreporterandroid.ui.onboarding;

/**
 * Created by Ahereza on 8/31/17.
 */

public class OnboardingActivityPresenter {
  private OnboardingActivityView view;

  public OnboardingActivityPresenter(OnboardingActivityView view) {
    this.view = view;
  }

  public void proceedToLogin() {
    view.startLoginActivity();
  }
}
