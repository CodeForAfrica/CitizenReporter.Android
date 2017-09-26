package org.codeforafrica.citizenreporterandroid.storyboard;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Ahereza on 9/12/17.
 */

public interface StoryboardContract {
  interface View {
    void showLoading();
    void hideLoading();
    void showUploadingProgress();
    void loadSavedReport(ParseObject story);
    void loadNewReport(String assignmentID);
    void showStoryNotFoundError(String message);
    void displayAttachments(List<ParseFile> files);
    void showImageAttachment(String name, String url);
    void showVideoAttachment(String name, String url);
    void showAudioAttachment(String name, String url);
    void showUnknownAttachment(String name, String url);
    void showLocationSearch();
    void updateStoryObject(ParseObject activeStory);
    void showDatePickerDialog();
    void showRecorder();
    void readyStoryForUpload();
    void showImagePicker();
    void finishUploading();
    void sendCameraIntent();
    void showUploadError();
    void showUploadSuccess();


  };
  interface Presenter {
    void openSavedStory(String storyID);
    void createNewStory(String assignmentID);
    void saveStory(ParseObject object);
    void uploadStory(ParseObject story);
    void loadAllAttachments(JSONArray attachments) throws JSONException;
    void getLocation();
    void getWhenItOccurred();
    void startRecorder();
    void attachVideo(String name, String url);
    void attachUnknown(String name, String url);
    void attachAudio(String name, String url);
    void attachImage(String name, String url);
    void getPicturesFromGallery();
    void startCameraCapture();

  };

}
