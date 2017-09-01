package org.codeforafrica.citizenreporterandroid.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.codeforafrica.citizenreporterandroid.main.assignments.AssignmentFragmentContract;
import org.codeforafrica.citizenreporterandroid.main.assignments.AssignmentsFragmentPresenter;

/**
 * Created by Ahereza on 9/1/17.
 */

@Module
public class PresenterModule {
  @Singleton @Provides AssignmentFragmentContract.Presenter providesAssignmentFragmentPresenter
      (Context context) {
    return new AssignmentsFragmentPresenter(context);
  }
}
