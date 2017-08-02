package org.codeforafrica.citizenreporterandroid.main.api;

import org.codeforafrica.citizenreporterandroid.main.models.Assignments;
import org.codeforafrica.citizenreporterandroid.main.models.AssignmentsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Mugiwara_Munyi on 30/07/2017.
 */

public interface ApiInterface {
    @GET("/assignments")
    Call<AssignmentsResponse> getAssignments();

}
