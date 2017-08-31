package org.codeforafrica.citizenreporterandroid.test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.codeforafrica.citizenreporterandroid.ui.onboarding.OnboardingActivityPresenter;
import org.codeforafrica.citizenreporterandroid.ui.onboarding.OnboardingActivityView;

/**
 * Created by Ahereza on 8/31/17.
 */
public class OnboardingActivityPresenterTest {
  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock OnboardingActivityView view;

  OnboardingActivityPresenter presenter;

  @Before public void setUp() {
    presenter = new OnboardingActivityPresenter(view);
  }

  @Test public void testProceedToLogin() {
    presenter.proceedToLogin();
    Mockito.verify(view).startLoginActivity();
  }
}