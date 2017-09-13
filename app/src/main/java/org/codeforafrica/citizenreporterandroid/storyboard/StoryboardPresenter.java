package org.codeforafrica.citizenreporterandroid.storyboard;

import android.util.Log;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;
import org.codeforafrica.citizenreporterandroid.storyboard.StoryboardContract.Presenter;
import org.json.JSONArray;
import org.json.JSONException;

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
    query.getInBackground(storyID, new GetCallback<ParseObject>() {
      public void done(ParseObject object, ParseException e) {
        Log.d(TAG, "done: got");
        if (e == null) {
          Log.d(TAG, "done: Loading saved report");
          view.loadSavedReport(object);
        } else {
          // something went wrong
          Log.d(TAG, "Error: " + e.getMessage());
          view.showStoryNotFoundError(e.getLocalizedMessage());
        }
      }
    });

  }

  @Override public void createNewStory(String assignmentID) {
    ParseObject newStory = new ParseObject("Story");
    newStory.put("uploaded", false);
    newStory.put("assignment", assignmentID);
    newStory.pinInBackground();
    view.loadNewReport(newStory);
  }

  @Override public void saveStory(ParseObject story) {
    if (story != null) {
      view.updateStoryObject(story);
      story.pinInBackground();
    }
  }

  @Override public void uploadStory() {

  }

  @Override public void loadAttachments(JSONArray attachments) {
    // get parse files

    // if file name ends in jpg or png or gif
    // view.attachImage(file);

    // if file is video
    // view.attachVideo(file);

    // if file is audio
    // view.attachAudio(file)
  }

  @Override public void getLocation() {
    view.showLocationSearch();
  }

  @Override public void getWhenItOccurred() {
    view.showDatePickerDialog();
  }

  public void setView(StoryboardContract.View view) {
    this.view = view;
  }
}
