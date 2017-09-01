package org.codeforafrica.citizenreporterandroid.utils;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Ahereza on 8/29/17.
 */

public interface FileUploadService {

  @Multipart @POST() Call<ResponseBody> upload(@Part RequestBody story_id,
      @Part MultipartBody.Part file);
}
