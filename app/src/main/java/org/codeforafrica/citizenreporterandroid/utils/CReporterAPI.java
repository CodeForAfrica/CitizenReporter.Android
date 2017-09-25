package org.codeforafrica.citizenreporterandroid.utils;

import java.util.List;
import okhttp3.ResponseBody;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.data.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Ahereza on 8/1/17.
 */

public interface CReporterAPI {
  @GET("stories/user/{uid}/") Call<List<Story>> getUserStories(@Path("uid") String uid);

  @POST("users/register") Call<User> createUser(@Body User user);

  @FormUrlEncoded @PATCH("users/update/{fb_id}/") Call<ResponseBody> updateFCM(
      @Path("uid") String fb_id, @Field("location") String fcm_token);

  @FormUrlEncoded @PATCH("users/update/{uid}/") Call<ResponseBody> updateLocation(
      @Path("uid") String uid, @Field("location") String locationString);

  @POST("stories/") Call<Story> uploadStory(@Body Story story);

  @GET("assignments/") Call<List<Assignment>> getAssignments();
}

