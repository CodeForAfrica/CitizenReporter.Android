package org.codeforafrica.citizenreporterandroid.storyboard;

import android.util.Log;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import java.util.List;
import org.codeforafrica.citizenreporterandroid.storyboard.StoryboardContract.Presenter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ahereza on 9/12/17.
 */

public class StoryboardPresenter implements Presenter {
  private static final String TAG = StoryboardPresenter.class.getSimpleName();

  StoryboardContract.View view;

  public StoryboardPresenter(StoryboardContract.View view) {
    this.view = view;
  }

  @Override public void openSavedStory(String storyID) {
    Log.d(TAG, "done: Loading saved report");
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
    query.fromLocalDatastore();
    Log.d(TAG, "done: Query time");
    query.whereEqualTo("localID", storyID);
    query.setLimit(1);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, ParseException e) {
        Log.d(TAG, "done: got");
        if (e == null && objects.size() > 0) {
          Log.d(TAG, "done: Loading saved report");
          ParseObject story = objects.get(0);
          story.fetchInBackground();
          view.loadSavedReport(story);
        } else {
          // something went wrong
          Log.d(TAG, "Error: " + e.getMessage());
          view.showStoryNotFoundError(e.getLocalizedMessage());
        }
      }
    });
  }

  @Override public void createNewStory(String assignmentID) {
    view.loadNewReport(assignmentID);
  }

  @Override public void saveStory(ParseObject story) {
    if (story != null) {
      view.updateStoryObject(story);
      story.pinInBackground();
    }
  }

  @Override public void uploadStory(final ParseObject story) {
    view.showLoading();
    Log.d(TAG, "uploadStory: Start uploading");
    view.readyStoryForUpload();
    Log.d(TAG, "uploadStory: " + story.getBoolean("uploaded"));
    if (!story.getBoolean("uploaded")) {
      story.put("uploaded", true);
      story.saveEventually(new SaveCallback() {
        @Override public void done(ParseException e) {
          if (e == null) {

            view.hideLoading();
            Log.d(TAG, "done: finished uploading successfully");
            // go to stories activity
            view.finishUploading();
          } else {
            story.put("uploaded", false);
            Log.e(TAG, "done: upload failed", e.fillInStackTrace());
          }

        }
      });
    }
  }

  @Override public void loadAllAttachments(JSONArray attachments) throws JSONException {
    for (int i = 0; i < attachments.length(); i++) {
      JSONObject object = attachments.getJSONObject(i);
      String name = object.getString("name");
      Log.i(TAG, "loadAllAttachments: " + object.getString("name") + " " + object.getString("url"));
      if (name.endsWith("wav")) {
        view.showAudioAttachment(name);
      } else if (name.endsWith("jpg") || name.endsWith("jpeg") || name.endsWith("png")) {
        view.showImageAttachment(name, object.getString("url"));

      } else if (name.endsWith("mp4")) {
        view.showVideoAttachment(name);
      } else {
        view.showUnknownAttachment(name);
      }

    }
    // get parse files

    // if file name ends in jpg or png or gif
    // view.showImageAttachment(file);

    // if file is video
    // view.showVideoAttachment(file);

    // if file is audio
    // view.showAudioAttachment(file)
  }

  @Override public void getLocation() {
    view.showLocationSearch();
  }

  @Override public void getWhenItOccurred() {
    view.showDatePickerDialog();
  }

  @Override public void startRecorder() {
    view.showRecorder();
  }

  @Override public void attachVideo(String name) {
    view.showVideoAttachment(name);
  }

  @Override public void attachUnknown(String name) {
    view.showUnknownAttachment(name);
  }

  @Override public void attachAudio(String name) {
    view.showAudioAttachment(name);
  }

  @Override public void attachImage(String name, String url) {
    view.showImageAttachment(name, url);
  }

  @Override public void getPicturesFromGallery() {
    view.showImagePicker();
  }

  @Override public void startCameraCapture() {
    Log.d(TAG, "startCameraCapture: Start camera capture");
    view.sendCameraIntent();
  }

  public void setView(StoryboardContract.View view) {
    this.view = view;
  }
}
