package org.codeforafrica.citizenreporterandroid.main.api;

import org.codeforafrica.citizenreporterandroid.main.models.Assignments;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Mugiwara_Munyi on 30/07/2017.
 */

public interface ApiInterface {
    @GET("/assignments")
    Call<List<Assignments>> getAssignments();

}
