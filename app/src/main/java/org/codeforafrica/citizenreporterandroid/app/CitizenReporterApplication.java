package org.codeforafrica.citizenreporterandroid.app;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.data.DataManager;
import org.codeforafrica.citizenreporterandroid.di.AppComponent;
import org.codeforafrica.citizenreporterandroid.di.AppModule;
import org.codeforafrica.citizenreporterandroid.di.DaggerAppComponent;

/**
 * Created by Ahereza on 9/1/17.
 */

public class CitizenReporterApplication extends Application {
  private AppComponent appComponent;
  @Inject DataManager manager;

  public AppComponent getAppComponent() {
    return appComponent;
  }


  @Override
  public void onCreate() {
    super.onCreate();
    Parse.enableLocalDatastore(this);
    Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId("11235813")
        .server("http://creporter-server.herokuapp.com/parse/")
        .build()
    );
    ParseFacebookUtils.initialize(this);

    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .build();
    appComponent.inject(this);
    manager.getAssignments();
  }
}
