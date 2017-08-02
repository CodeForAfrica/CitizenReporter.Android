package org.codeforafrica.citizenreporterandroid.utils;

import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.data.models.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Ahereza on 8/1/17.
 */

public interface APIInterface {
    @GET("stories/user/{fb_id}/")
    Call<List<Story>> getUserStories(@Path("fb_id") String fb_id);

    @POST("users/register")
    Call<User> createUser(@Body User user);

    @PATCH("users/update/{fb_id}")
    Call<User> registerFCM(@Path("fb_id") String fb_id);

    @POST()
    Call<Story> uploadStory(@Body Story story);

    @Multipart
    @POST()
    Call<ResponseBody> uploadFile(
            @Part ResponseBody story_id,
            @Part MultipartBody.Part file
            );

}

