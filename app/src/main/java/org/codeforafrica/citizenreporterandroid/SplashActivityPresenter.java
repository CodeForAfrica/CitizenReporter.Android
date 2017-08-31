package org.codeforafrica.citizenreporterandroid;

/**
 * Created by Ahereza on 8/31/17.
 */

public class SplashActivityPresenter {
    private SplashActivityView view;

    public SplashActivityPresenter(SplashActivityView view) {
        this.view = view;
    }

    public void startNextActivity() {
        view.goToNextActivity();

    }
}
