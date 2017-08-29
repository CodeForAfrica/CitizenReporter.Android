package org.codeforafrica.citizenreporterandroid.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ahereza on 7/25/17.
 */

public class APIClient {

    private static Retrofit retrofit;

    public static APIInterface getApiClient(){
        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl("http://api.creporter.codeforafrica.org/api/")
                .baseUrl("http://24f488d3.ngrok.io/api/")
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        APIInterface apiClient = retrofit.create(APIInterface.class);
        return apiClient;
    }

    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
