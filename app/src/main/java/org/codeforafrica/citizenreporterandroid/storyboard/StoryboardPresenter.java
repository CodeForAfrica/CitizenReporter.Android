package org.codeforafrica.citizenreporterandroid.storyboard;

import android.util.Log;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
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
          if (story.getString("title") != null){
            story.fetchInBackground();
            view.loadSavedReport(story);
          }

        } else {
          // something went wrong
          Log.d(TAG, "Error: " + e.getMessage());
          view.showStoryNotFoundError(e.getLocalizedMessage());
        }
      }
    });


    // Get saved media
    ParseQuery<ParseObject> mediaFileQuery = ParseQuery.getQuery("mediaFile");
    mediaFileQuery.fromLocalDatastore();
    mediaFileQuery.whereEqualTo("localStoryID", storyID);
    mediaFileQuery.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, ParseException e) {
        Log.d(TAG, "done: got");
        if (e == null && objects.size() > 0) {
          Log.d(TAG, "done: Loading saved report");

          for (ParseObject mediaFile:  objects){
            ParseFile file = (ParseFile)mediaFile.get("remoteFile");
            final String localURL = mediaFile.getString("localUrl");
            final String name = file.getName();
            final String url = file.getUrl();
            if (name.endsWith("mp4")) {
              ParseFile thumbnail = (ParseFile)mediaFile.get("thumbnail");
              loadAttachment(localURL, name, url, thumbnail.getUrl());
            } else {
              loadAttachment(localURL, name, url);
            }
            mediaFile.fetchInBackground(new GetCallback<ParseObject>() {
              public void done(ParseObject object, ParseException e) {
                if (e == null) {
                  String _localURL = object.getString("localUrl");
                  ParseFile file = (ParseFile)object.get("remoteFile");
                  if (file != null) {
                    String _url = file.getUrl();
                    String _name = file.getName();
                    if (!_name.equals(name) || !_url.equals(url) || !_localURL.equals(localURL)) {
                      if (name.endsWith("mp4")) {
                        ParseFile _thumbnail = (ParseFile)object.get("thumbnail");
                        loadAttachment(_localURL, _url, _name, _thumbnail.getUrl());
                      } else {
                        loadAttachment(_localURL, _url, _name);
                      }
                    }
                  }
                } else {
                  Log.e(TAG, "loadSavedAttachment", e.fillInStackTrace());
                }
              }
            });

          }
        } else {
          // something went wrong
          Log.d(TAG, "new Error: " + e);
        }
      }
    });

  }

  @Override public void createAndUploadParseMediaFile(ParseObject activeStory, String localURL, ParseFile... files) {
    final ParseObject parseMediaFile = new ParseObject("mediaFile");
    parseMediaFile.put("parent", activeStory);
    parseMediaFile.put("localStoryID", activeStory.getString("localID"));
    parseMediaFile.put("localUrl", localURL);
    parseMediaFile.put("createdBy", ParseUser.getCurrentUser());
    parseMediaFile.put("remoteFile", files[0]);

    // Save video thumbnail
    if (files.length > 1) {
      parseMediaFile.put("thumbnail", files[1]);
    }
    try {
      parseMediaFile.pin();
    } catch (ParseException e) {
      e.printStackTrace();
      Log.e(TAG, "Create and upload: Error pinning", e.fillInStackTrace());
    }
    parseMediaFile.saveInBackground();
  }

  @Override public void createNewStory(String assignmentID) {
    view.loadNewReport(assignmentID);
  }

  @Override public void saveStory(ParseObject story) {
      view.updateStoryObject(story);
      if (story.getString("title") != null){
      story.pinInBackground();
    } else{
      story.unpinInBackground();
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
            view.showUploadSuccess();
            Log.d(TAG, "done: finished uploading successfully");
            // go to stories activity
            view.finishUploading();
          } else {
            story.put("uploaded", false);
            view.showUploadError();
            Log.e(TAG, "done: upload failed", e.fillInStackTrace());
          }

        }
      });
    }
  }

  @Override public void loadAttachment(String localURL, String remoteName, String... remoteUrls){
    File file = new File(localURL);
    String url = (file.exists()) ? localURL : remoteUrls[0];
    if (remoteName.endsWith("wav")) {
      view.showAudioAttachment(remoteName, url);
    } else if (remoteName.endsWith("jpg") || remoteName.endsWith("jpeg") || remoteName.endsWith("png")) {
      view.showImageAttachment(remoteName, url);

    } else if (remoteName.endsWith("mp4")) {
      view.showVideoAttachment(remoteName, url, remoteUrls[1]);
    } else {
      view.showUnknownAttachment(remoteName);
    }
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

  @Override public void attachVideo(String name, String url) {
    view.addToVideoAttachments(name, url);
    view.showVideoAttachment(name, url);
  }

  @Override public void attachUnknown(String name, String url) {
    view.showUnknownAttachment(name, url);
  }

  @Override public void attachAudio(String name, String url) {
    view.addToAudioAttachments(name, url);
    view.showAudioAttachment(name, url);
  }

  @Override public void attachImage(String name, String url) {
    view.addToImageAttachments(name, url);
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
