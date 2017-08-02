package org.codeforafrica.citizenreporterandroid.main.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mugiwara_Munyi on 30/07/2017.
 */

public class ApiClient {
    private static final String BASE_URL = "http://1d89f66b.ngrok.io";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
