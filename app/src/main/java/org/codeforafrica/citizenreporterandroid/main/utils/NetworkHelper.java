package org.codeforafrica.citizenreporterandroid.main.utils;

import android.content.Context;
import android.util.Log;

import org.codeforafrica.citizenreporterandroid.main.adapter.AssignmentsAdapter;
import org.codeforafrica.citizenreporterandroid.main.api.ApiInterface;
import org.codeforafrica.citizenreporterandroid.main.models.Assignments;
import org.codeforafrica.citizenreporterandroid.main.sources.LocalDataHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mugiwara_Munyi on 10/08/2017.
 */

public class NetworkHelper {
    public static void getAssignments(final Context context, ApiInterface apiClient,
                                      final AssignmentsAdapter adapter) {

        Call<List<Assignments>> assignmentsCall = apiClient.getAssignments();
        assignmentsCall.enqueue(new Callback<List<Assignments>>() {
            @Override
            public void onResponse(Call<List<Assignments>> call, Response<List<Assignments>> response) {
                if (response.isSuccessful()) {
                    List<Assignments> assignments = response.body();
                    LocalDataHelper dataHelper = new LocalDataHelper(context);
                    if (assignments.size() > 0) {
                        // only save to the database if the API call returned any stories
                        dataHelper.bulkSaveStories(assignments);
                        Log.d("API", "Assignments after api call "
                                + String.valueOf(assignments.size()));
                        // update the adapter to display the new stories
                        adapter.setAssignmentList(dataHelper.getAssignments());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Assignments>> call, Throwable t) {
                // TODO fail gracefully
            }
        });

    }
}
