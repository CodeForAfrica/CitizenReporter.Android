package org.codeforafrica.citizenreporterandroid.utils;

import org.codeforafrica.citizenreporterandroid.app.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ahereza on 7/25/17.
 */

public class APIClient {

  private static Retrofit retrofit;

  public static CReporterAPI getApiClient() {
    Retrofit.Builder builder =
        new Retrofit.Builder().baseUrl(Constants.BASE_URL)
            //                .baseUrl("https://882d0ed0.ngrok.io/api/")
            .addConverterFactory(GsonConverterFactory.create());
    retrofit = builder.build();
    CReporterAPI apiClient = retrofit.create(CReporterAPI.class);
    return apiClient;
  }

  public static <S> S createService(Class<S> serviceClass) {
    return retrofit.create(serviceClass);
  }
}
