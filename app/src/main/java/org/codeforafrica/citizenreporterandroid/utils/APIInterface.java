package org.codeforafrica.citizenreporterandroid.utils;

import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.data.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Ahereza on 8/1/17.
 */

public interface APIInterface {
    @GET()
    Call<List<Story>> getUserStories(String user_fb_id);

    @POST("users/register")
    Call<User> createUser(User user);

    @PATCH("users/update/{fb_id}")
    Call<User> registerFCM(@Path("fb_id") String fb_id);

}
