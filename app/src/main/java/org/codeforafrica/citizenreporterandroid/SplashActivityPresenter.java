package org.codeforafrica.citizenreporterandroid;

/**
 * Created by Ahereza on 8/31/17.
 */

public class SplashActivityPresenter {
    // TODO: 8/31/17 inject preferences so that we can test start mainactivity or onboarding
    // activity independently
    private SplashActivityView view;

    public SplashActivityPresenter(SplashActivityView view) {
        this.view = view;
    }

    public void startNextActivity() {
        view.goToNextActivity();

    }
}
