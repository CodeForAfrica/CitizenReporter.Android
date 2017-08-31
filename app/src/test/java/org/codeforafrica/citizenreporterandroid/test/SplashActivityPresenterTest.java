package org.codeforafrica.citizenreporterandroid.test;

import org.codeforafrica.citizenreporterandroid.SplashActivityPresenter;
import org.codeforafrica.citizenreporterandroid.SplashActivityView;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

/**
 * Created by Ahereza on 8/31/17.
 */

public class SplashActivityPresenterTest {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    SplashActivityView view;

    SplashActivityPresenter presenter;

    @Before public void setUp() {

        presenter = new SplashActivityPresenter(view);
    }

    @Test public void testPresenterStartsNextActivity() {

        presenter.startNextActivity();

        Mockito.verify(view).goToNextActivity();
    }

}