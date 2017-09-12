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

  StoryboardContract.View view;

  public StoryboardPresenter(StoryboardContract.View view) {
    this.view = view;
  }

  @Override public void openSavedStory(String storyID) {
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
    query.fromLocalDatastore();
    query.getInBackground(storyID, new GetCallback<ParseObject>() {
      public void done(ParseObject object, ParseException e) {
        if (e == null) {
          view.loadSavedReport(object);
        } else {
          // something went wrong
          Log.d("score", "Error: " + e.getMessage());
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
    story.pinInBackground();
  }

  @Override public void uploadStory() {

  }

  @Override public void loadAttachments(JSONArray attachments) {
    ArrayList<ParseFile> files = new ArrayList<ParseFile>();

    if (attachments != null) {

    }

  }

  public void setView(StoryboardContract.View view) {
    this.view = view;
  }
}
