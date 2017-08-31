package org.codeforafrica.citizenreporterandroid.test;

import org.codeforafrica.citizenreporterandroid.OnboardingActivity;
import org.codeforafrica.citizenreporterandroid.OnboardingActivityPresenter;
import org.codeforafrica.citizenreporterandroid.OnboardingActivityView;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

/**
 * Created by Ahereza on 8/31/17.
 */
public class OnboardingActivityPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    OnboardingActivityView view;

    OnboardingActivityPresenter presenter;

    @Before public void setUp(){
        presenter = new OnboardingActivityPresenter(view);
    }
    @Test
    public void testProceedToLogin() {
        presenter.proceedToLogin();
        Mockito.verify(view).startLoginActivity();
    }

}