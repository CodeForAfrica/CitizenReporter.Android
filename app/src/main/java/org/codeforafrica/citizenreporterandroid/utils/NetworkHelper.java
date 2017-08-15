package org.codeforafrica.citizenreporterandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import org.codeforafrica.citizenreporterandroid.data.models.Assignments;
import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.data.models.User;
import org.codeforafrica.citizenreporterandroid.data.sources.LocalDataHelper;
import org.codeforafrica.citizenreporterandroid.main.adapter.AssignmentsAdapter;
import org.codeforafrica.citizenreporterandroid.main.stories.StoriesRecyclerViewAdapter;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ahereza on 8/1/17.
 */

public class NetworkHelper {
    private static final String TAG = "NETWORK HELPER CLASS";
    private List<Story> stories;


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }


    public static boolean checkNetworkPermission(Context context) {
        // TODO check network permission
        return true;
    }

    public static void registerUserDetails(
            final Context context, APIInterface apiClient, User user) {
        Log.d("Username", user.getName());
        Call<User> userCall = apiClient.createUser(user);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    switch (response.code()) {
                        case 200:
                            Toast.makeText(context, "User Successfully Created",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(context, "Server Error",
                                    Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // TODO fail gracefully
            }
        });
    }

    public static void uploadUserStory(
            final Context context, APIInterface apiClient, Story story) {
        Call<Story> storyUploadCall = apiClient.uploadStory(story);
        storyUploadCall.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                if (response.isSuccessful()) {
                    switch (response.code()) {
                        case 201:
                            Toast.makeText(context, "Story Successfully Uploaded",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case 202:
                            // server returns a 202 if the user already exists
                            break;
                        default:
                            Toast.makeText(context, "Server Error",
                                    Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {
                // TODO fail gracefully
            }
        });

    }

    /**
     * method gets stories associated to this current user from the server, saves them to the
     * database and then updates the data in the adapter
     *
     * @param context   the Activity or Fragment
     * @param apiClient
     * @param fb_id     user facebook ID
     * @param adapter   the adapter responsible for updating the recyclerview
     */

    public static void getUserStories(final Context context, APIInterface apiClient, String fb_id,
                                      final StoriesRecyclerViewAdapter adapter) {

        Call<List<Story>> storiesCall = apiClient.getUserStories(fb_id);
        storiesCall.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    List<Story> stories = response.body();
                    LocalDataHelper dataHelper = new LocalDataHelper(context);
                    if (stories.size() > 0) {
                        // only save to the database if the API call returned any stories
                        dataHelper.bulkSaveStories(stories);
                        Log.d("API", "Stories count after api call "
                                + String.valueOf(stories.size()));
                        // update the adapter to display the new stories
                        adapter.setStoryList(dataHelper.getAllStories());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                // TODO fail gracefully
            }
        });

    }

    public static void updateLocation(
            final Context context, APIInterface apiClient, String location, String fb_id) {
        Call<ResponseBody> updateLocationCall = apiClient.updateLocation(fb_id, location);
        updateLocationCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,
                        "Location not successfully updated",
                        Toast.LENGTH_SHORT).show();

            }
        });

    }

    public static void updateFCM(
            final Context context, APIInterface apiClient, String fcm_token, String fb_id) {
        Call<ResponseBody> updateFCMCall = apiClient.updateFCM(fb_id, fcm_token);
        updateFCMCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,
                        "FCM update failed",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void getAssignments(final Context context, APIInterface apiClient,
                                      final AssignmentsAdapter adapter) {
        Log.d("API", "getAssignments: method called");

        Call<List<Assignments>> assignmentsCall = apiClient.getAssignments();
        assignmentsCall.enqueue(new Callback<List<Assignments>>() {
            @Override
            public void onResponse(Call<List<Assignments>> call, Response<List<Assignments>> response) {
                if (response.isSuccessful()) {
                    List<Assignments> assignments = response.body();
                    LocalDataHelper dataHelper = new LocalDataHelper(context);
                    if (assignments.size() > 0) {
                        // only save to the database if the API call returned any stories
                        dataHelper.bulkSaveAssignments(assignments);
                        Log.d("API", "Assignments after api call "
                                + String.valueOf(assignments.size()));
                        // update the adapter to display the new stories
                        adapter.setAssignmentList(dataHelper.getAssignments());
                        adapter.notifyDataSetChanged();
                    }
                }

                Log.d(TAG, "Assignments response code: " + response.code());
            }

            @Override
            public void onFailure(Call<List<Assignments>> call, Throwable t) {
                // TODO fail gracefully
            }
        });

    }


//    public static void uploadMediaFiles(
//            final Context context, APIInterface apiClient, String remoteStoryId, Uri fileUri) {
//        ResponseBody id = ResponseBody.create(MultipartBody.FORM, remoteStoryId);
//
//        RequestBody filePart = RequestBody.create(MediaType.parse());
//        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
//        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");
//
//    }

}
