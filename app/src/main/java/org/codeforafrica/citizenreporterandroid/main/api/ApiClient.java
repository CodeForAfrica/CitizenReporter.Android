package org.codeforafrica.citizenreporterandroid.main.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mugiwara_Munyi on 30/07/2017.
 */

public class ApiClient {
    private static final String BASE_URL = "https://0cc385e4.ngrok.io";
    private static Retrofit retrofit = null;


    public static ApiInterface getApiClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiClient= retrofit.create(ApiInterface.class);

        return apiClient;
    }
}
