package org.codeforafrica.citizenreporterandroid.app;

import android.app.Application;
import android.util.Log;

import com.flurry.android.FlurryAgent;
import com.google.firebase.FirebaseApp;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.di.AppComponent;
import org.codeforafrica.citizenreporterandroid.di.AppModule;
import org.codeforafrica.citizenreporterandroid.di.DaggerAppComponent;

import static android.util.Log.VERBOSE;

/**
 * Created by Ahereza on 9/1/17.
 */

public class CitizenReporterApplication extends Application {
  private static final String TAG = CitizenReporterApplication.class.getSimpleName();
  private AppComponent appComponent;

  public AppComponent getAppComponent() {
    return appComponent;
  }


  @Override
  public void onCreate() {
    super.onCreate();
    FirebaseApp.initializeApp(getApplicationContext());
    Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId("11235813")
        .server("http://creporter-server.herokuapp.com/parse/")
        .enableLocalDataStore()
        .build()
    );
    ParseFacebookUtils.initialize(this);

    // Initialise flurry analytics
    new FlurryAgent.Builder()
            .withLogEnabled(true)
            .withCaptureUncaughtExceptions(true)
            .withContinueSessionMillis(10)
            .withLogEnabled(true)
            .withLogLevel(VERBOSE)
//            .withListener(flurryAgentListener)
            .build(this, "JH2WNJX9DQQTHPZGJYY8");

    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .build();
    appComponent.inject(this);
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Assignment");
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, ParseException e) {
        if (e == null) {
          try {
            Log.d(TAG, "Got all assignments: " + objects.size());
            ParseObject.pinAllInBackground(objects);
          } catch (NullPointerException e1) {
            e1.printStackTrace();
          }
        }

      }
    });

    ParseQuery<ParseObject> storiesQuery = ParseQuery.getQuery("Story");
    storiesQuery.findInBackground(new FindCallback<ParseObject>() {
      public void done(List<ParseObject> storyList, ParseException e) {
        if (e == null) {
          try {
            Log.d("Stories", "done: storyList " + storyList.size());
            ParseObject.pinAllInBackground(storyList);
          } catch (NullPointerException e1) {
            e1.printStackTrace();
          }
        }


      }
    });

  }
}
