package org.codeforafrica.citizenreporterandroid.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import okhttp3.ResponseBody;
import org.codeforafrica.citizenreporterandroid.app.Constants;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.data.models.User;
import org.codeforafrica.citizenreporterandroid.data.sources.LocalDataHelper;
import org.codeforafrica.citizenreporterandroid.utils.CReporterAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.codeforafrica.citizenreporterandroid.app.Constants.FALLBACK_PROFILE_PIC_URl;
import static org.codeforafrica.citizenreporterandroid.app.Constants.PREF_KEY_CURRENT_USER_FB_ID;
import static org.codeforafrica.citizenreporterandroid.app.Constants.PREF_KEY_CURRENT_USER_NAME;
import static org.codeforafrica.citizenreporterandroid.app.Constants.PREF_KEY_CURRENT_USER_ONBOARDED;
import static org.codeforafrica.citizenreporterandroid.app.Constants.PREF_KEY_CURRENT_USER_PROFILE_PIC_URL;
import static org.codeforafrica.citizenreporterandroid.app.Constants.PREF_KEY_USER_LOGGED_IN_MODE;

/**
 * Created by Ahereza on 8/31/17.
 */

public class DataManagerImpl implements DataManager {
  private final CReporterAPI api;
  private final SharedPreferences mPrefs;
  private Context context;
  @Inject LocalDataHelper db;

  @Inject
  public DataManagerImpl(CReporterAPI api, SharedPreferences mPrefs, Context context) {
    this.api = api;
    this.mPrefs = mPrefs;
    this.context = context;
    this.db = new LocalDataHelper(context);
  }

  @Override public String getCurrentUsername() {
    return mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, "Unknown");
  }

  @Override public void setCurrentUsername(String username) {
    mPrefs.edit().putString(Constants.PREF_KEY_CURRENT_USER_NAME, username).apply();
  }

  @Override public String getCurrentUserProfilePicUrl() {
    return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, FALLBACK_PROFILE_PIC_URl);
  }

  @Override public void setCurrentUserProfilePicUrl(String profilePicUrl) {
    mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl).apply();
  }

  @Override public String getCurrentUserFbID() {
    return mPrefs.getString(PREF_KEY_CURRENT_USER_FB_ID, "Unknown");
  }

  @Override public void setCurrentUserFBID(String fbid) {
    mPrefs.edit().putString(PREF_KEY_CURRENT_USER_FB_ID, fbid).apply();
  }

  @Override public boolean isCurrentUserLoggedIn() {
    return mPrefs.getBoolean(PREF_KEY_USER_LOGGED_IN_MODE, false);
  }

  @Override public void setCurrentUserLoggedInMode() {
    mPrefs.edit().putBoolean(PREF_KEY_USER_LOGGED_IN_MODE, true).apply();
  }

  @Override public boolean hasCurrentUserBeenOnboarded() {
    return mPrefs.getBoolean(PREF_KEY_CURRENT_USER_ONBOARDED, false);
  }

  @Override public void setCurrentUserHasBeenOnboarded() {
    mPrefs.edit().putBoolean(PREF_KEY_CURRENT_USER_ONBOARDED, true).apply();

  }

  @Override public List<Assignment> loadAssignmentsFromDb() {
    return db.getAssignments();
  }

  @Override public List<Assignment> fetchAssignmentsAPI() {
    return Collections.EMPTY_LIST;
  }

  @Override public void saveAssignmentsIntoDb(List<Assignment> assignments) {

  }

  @Override public void clearAssignmentsTable() {
    db.deleteAllAssignments();
  }

  @Override public List<Story> fetchStoriesFromDb() {
    return db.getAllStories();
  }

  @Override public void registerUserDetails(User user) {
    Call<User> userCall = api.createUser(user);
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

  @Override public void uploadUserStory(Story story) {
    Call<Story> storyUploadCall = api.uploadStory(story);
    storyUploadCall.enqueue(new Callback<Story>() {
      @Override public void onResponse(Call<Story> call, Response<Story> response) {
        if (response.isSuccessful()) {
          switch (response.code()) {
            case 201:
              Toast.makeText(context, "Story Successfully Uploaded", Toast.LENGTH_SHORT).show();
              // TODO: 9/1/17 set story as uploaded and save it
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

  @Override public void getUserStories(String fb_id) {
    Call<List<Story>> storiesCall = api.getUserStories(fb_id);
    storiesCall.enqueue(new Callback<List<Story>>() {
      @Override public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
        if (response.isSuccessful()) {
          List<Story> stories = response.body();
          db.bulkSaveStories(stories);
        }
      }

      @Override public void onFailure(Call<List<Story>> call, Throwable t) {
        // TODO fail gracefully
      }
    });

  }

  @Override public void updateLocation(String fb_id, String location) {
    Call<ResponseBody> updateLocationCall = api.updateLocation(fb_id, location);
    updateLocationCall.enqueue(new Callback<ResponseBody>() {
      @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
          Toast.makeText(context, "Location successfully updated", Toast.LENGTH_SHORT).show();
        }
      }

      @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
        Toast.makeText(context, "Location not successfully updated", Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override public void updateFCM(String fb_id, String FCM_token) {
    Call<ResponseBody> updateFCMCall = api.updateFCM(fb_id, FCM_token);
    updateFCMCall.enqueue(new Callback<ResponseBody>() {
      @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
          Toast.makeText(context, "FCM updated successfully", Toast.LENGTH_SHORT).show();
        }
      }

      @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
        Toast.makeText(context, "FCM update failed", Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override public void getAssignments() {
    Call<List<Assignment>> assignmentsCall = api.getAssignments();
    assignmentsCall.enqueue(new Callback<List<Assignment>>() {
      @Override
      public void onResponse(Call<List<Assignment>> call, Response<List<Assignment>> response) {
        if (response.isSuccessful()) {
          List<Assignment> assignments = response.body();
          if (assignments != null && assignments.size() > 0) {
            db.deleteAllAssignments();
            db.bulkSaveAssignments(assignments);
          }
        }

      }

      @Override public void onFailure(Call<List<Assignment>> call, Throwable t) {
        // TODO fail gracefully
      }
    });
  }
}
