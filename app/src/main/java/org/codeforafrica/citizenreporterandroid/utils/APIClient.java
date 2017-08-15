package org.codeforafrica.citizenreporterandroid.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ahereza on 7/25/17.
 */

public class APIClient {

    public static APIInterface getApiClient(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://a3ca48e8.ngrok.io/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        APIInterface apiClient = retrofit.create(APIInterface.class);
        return apiClient;
    }

}
