package org.codeforafrica.citizenreporterandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.data.models.User;
import org.codeforafrica.citizenreporterandroid.data.sources.LocalDataHelper;
import org.codeforafrica.citizenreporterandroid.adapter.AssignmentsAdapter;
import org.codeforafrica.citizenreporterandroid.adapter.StoriesRecyclerViewAdapter;
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
    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    return isConnected;
  }

  public static boolean checkNetworkPermission(Context context) {
    // TODO check network permission
    return true;
  }

  public static void registerUserDetails(final Context context, CReporterAPI apiClient, User user) {
    Log.d("Username", user.getName());
    Call<User> userCall = apiClient.createUser(user);
    userCall.enqueue(new Callback<User>() {
      @Override public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {
          switch (response.code()) {
            case 200:
              Toast.makeText(context, "User Successfully Created", Toast.LENGTH_SHORT).show();
              break;
            default:
              Toast.makeText(context, "Server Error" + response.code(), Toast.LENGTH_SHORT).show();
          }
        }
      }

      @Override public void onFailure(Call<User> call, Throwable t) {
        // TODO fail gracefully

      }
    });
  }

  public static void uploadUserStory(final Context context, CReporterAPI apiClient,
      final Story story) {
    Call<Story> storyUploadCall = apiClient.uploadStory(story);
    storyUploadCall.enqueue(new Callback<Story>() {
      @Override public void onResponse(Call<Story> call, Response<Story> response) {
        if (response.isSuccessful()) {
          switch (response.code()) {
            case 201:
              Toast.makeText(context, "Story Successfully Uploaded", Toast.LENGTH_SHORT).show();
              Log.d(TAG, "onResponse: " + response.body().toString());
              story.setUploaded(true);
              LocalDataHelper dataHelper = new LocalDataHelper(context);
              dataHelper.updateStory(story);
              break;
            case 202:
              // server returns a 202 if the user already exists
              break;
            default:
              Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
          }
        }
      }

      @Override public void onFailure(Call<Story> call, Throwable t) {
        // TODO fail gracefully
      }
    });
  }

  /**
   * method gets stories associated to this current user from the server, saves them to the
   * database and then updates the data in the adapter
   *
   * @param context the Activity or Fragment
   * @param fb_id user facebook ID
   * @param adapter the adapter responsible for updating the recyclerview
   */

  public static void getUserStories(final Context context, CReporterAPI apiClient, String fb_id,
      final StoriesRecyclerViewAdapter adapter) {

    Call<List<Story>> storiesCall = apiClient.getUserStories(fb_id);
    storiesCall.enqueue(new Callback<List<Story>>() {
      @Override public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
        if (response.isSuccessful()) {
          List<Story> stories = response.body();
          LocalDataHelper dataHelper = new LocalDataHelper(context);
          if (stories.size() > 0) {
            // only save to the database if the API call returned any stories
            dataHelper.bulkSaveStories(stories);
            Log.d("API", "Stories count after api call " + String.valueOf(stories.size()));
            // update the adapter to display the new stories
            adapter.setStoryList(dataHelper.getAllStories());
            adapter.notifyDataSetChanged();
          }
        }
      }

      @Override public void onFailure(Call<List<Story>> call, Throwable t) {
        // TODO fail gracefully
      }
    });
  }

  public static void updateLocation(final Context context, CReporterAPI apiClient, String location,
      String fb_id) {
    Call<ResponseBody> updateLocationCall = apiClient.updateLocation(fb_id, location);
    updateLocationCall.enqueue(new Callback<ResponseBody>() {
      @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
          Log.d(TAG, "onResponse: " + response.code());
        }
      }

      @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
        Toast.makeText(context, "Location not successfully updated", Toast.LENGTH_SHORT).show();
      }
    });
  }

  public static void updateFCM(final Context context, CReporterAPI apiClient, String fcm_token,
      String fb_id) {
    Call<ResponseBody> updateFCMCall = apiClient.updateFCM(fb_id, fcm_token);
    updateFCMCall.enqueue(new Callback<ResponseBody>() {
      @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
          Log.d(TAG, "onResponse: " + response.code());
        }
      }

      @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
        Toast.makeText(context, "FCM update failed", Toast.LENGTH_SHORT).show();
      }
    });
  }

  public static void getAssignments(final Context context, CReporterAPI apiClient,
      final AssignmentsAdapter adapter, final SwipeRefreshLayout refreshLayout) {
    Log.d("API", "getAndDisplayAssignments: method called");

    Call<List<Assignment>> assignmentsCall = apiClient.getAssignments();
    assignmentsCall.enqueue(new Callback<List<Assignment>>() {
      @Override
      public void onResponse(Call<List<Assignment>> call, Response<List<Assignment>> response) {
        if (response.isSuccessful()) {
          List<Assignment> assignments = response.body();
          LocalDataHelper dataHelper = new LocalDataHelper(context);
          if (assignments.size() > 0) {
            // only save to the database if the API call returned any stories
            dataHelper.bulkSaveAssignments(assignments);
            Log.d("API", "Assignment after api call " + String.valueOf(assignments.size()));
            // update the adapter to display the new stories
            adapter.setAssignmentList(dataHelper.getAssignments());
            adapter.notifyDataSetChanged();
            if (refreshLayout != null) {
              refreshLayout.setRefreshing(false);
            }
          }
        }

        Log.d(TAG, "Assignment response code: " + response.code());
      }

      @Override public void onFailure(Call<List<Assignment>> call, Throwable t) {
        // TODO fail gracefully
      }
    });
  }

  private void uploadFile(String filePath, int storyID) {
    // create upload service client
    FileUploadService service = APIClient.createService(FileUploadService.class);

    // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
    // use the FileUtils to get the actual file by uri
    File file = new File(filePath);

    // create RequestBody instance from file
    RequestBody requestFile = RequestBody.create(MediaType.parse(filePath), file);

    // MultipartBody.Part is used to send also the actual file name
    MultipartBody.Part body =
        MultipartBody.Part.createFormData("file", file.getName(), requestFile);

    RequestBody id = RequestBody.create(okhttp3.MultipartBody.FORM, String.valueOf(storyID));

    // finally, execute the request
    Call<ResponseBody> call = service.upload(id, body);
    call.enqueue(new Callback<ResponseBody>() {
      @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.v("Upload", "success");
      }

      @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e("Upload error:", t.getMessage());
      }
    });
  }
}
