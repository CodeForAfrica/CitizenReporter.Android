package org.codeforafrica.citizenreporterandroid.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import org.codeforafrica.citizenreporterandroid.data.DataManager;
import org.codeforafrica.citizenreporterandroid.data.DataManagerImpl;
import org.codeforafrica.citizenreporterandroid.utils.CReporterAPI;
import org.codeforafrica.citizenreporterandroid.app.Constants;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ahereza on 9/1/17.
 */

@Module
public class AppModule {
  private static final String NAME_BASE_URL = "NAME_BASE_URL";
  private static final String NAME_PREF_FILE = "NAME_PREF_FILE";
  private Application application;

  public AppModule(Application application) {
    this.application = application;
  }

  @Singleton @Provides Context providesContext() {
    return application;
  }

  @Provides
  @Named(NAME_BASE_URL)
  String provideBaseUrlString() {
    return Constants.BASE_URL;
  }

  @Provides
  @Singleton
  Converter.Factory provideGsonConverter() {
    return GsonConverterFactory.create();
  }

  @Provides
  @Singleton Retrofit provideRetrofit(Converter.Factory converter, @Named(NAME_BASE_URL) String baseUrl) {
    return new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converter)
        .build();
  }

  @Provides
  @Singleton CReporterAPI provideUsdaApi(Retrofit retrofit) {
    return retrofit.create(CReporterAPI.class);
  }

  @Provides
  @Named(NAME_PREF_FILE)
  String provideSharedPrefFileName() {
    return Constants.SHARED_PREF_FILENAME;
  }

  @Singleton @Provides SharedPreferences providesSharedPreferences(Context context) {
    return context.getSharedPreferences(Constants.SHARED_PREF_FILENAME, context.MODE_PRIVATE);
  }

  @Singleton @Provides DataManager providesDataManager(CReporterAPI api, SharedPreferences
      prefs, Context context) {
    return new DataManagerImpl(api, prefs, context);
  }
}
