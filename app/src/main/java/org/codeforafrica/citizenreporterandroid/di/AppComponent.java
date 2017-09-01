package org.codeforafrica.citizenreporterandroid.di;

import dagger.Component;
import javax.inject.Singleton;
import org.codeforafrica.citizenreporterandroid.app.CitizenReporterApplication;
import org.codeforafrica.citizenreporterandroid.main.assignments.AssignmentsFragment;
import org.codeforafrica.citizenreporterandroid.main.assignments.AssignmentsFragmentPresenter;

/**
 * Created by Ahereza on 9/1/17.
 */
@Singleton @Component(modules = { AppModule.class, PresenterModule.class })
public interface AppComponent {
  void inject(CitizenReporterApplication app);

  void inject(AssignmentsFragment fragment);

  void inject(AssignmentsFragmentPresenter presenter);
}
