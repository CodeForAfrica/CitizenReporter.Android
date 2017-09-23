package org.codeforafrica.citizenreporterandroid.utils;

import java.util.List;
import okhttp3.ResponseBody;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.data.models.User;
import org.codeforafrica.citizenreporterandroid.data.models.WordpressStory;
import org.codeforafrica.citizenreporterandroid.data.models.WordpressUser;
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

public interface WordpressAPI {
  @FormUrlEncoded
  @POST("api/cr/get_user_info/") Call<WordpressUser> verifyWordpressUser(
      @Field("username") String username, @Field("password") String password);

  @GET("") Call<List<WordpressStory>> getOldUserStories();
  // TODO: 9/21/17
}

