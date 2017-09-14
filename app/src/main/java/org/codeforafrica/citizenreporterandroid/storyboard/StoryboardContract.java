package org.codeforafrica.citizenreporterandroid.storyboard;

import com.parse.ParseFile;
import com.parse.ParseObject;
import java.util.List;
import org.json.JSONArray;

/**
 * Created by Ahereza on 9/12/17.
 */

public interface StoryboardContract {
  interface View {
    void showLoading();
    void hideLoading();
    void showUploadingProgress();
    void loadSavedReport(ParseObject story);
    void loadNewReport(ParseObject story);
    void showStoryNotFoundError(String message);
    void displayAttachments(List<ParseFile> files);
    void attachImage(ParseFile file);
    void attachVideo(ParseFile file);
    void attachAudio(ParseFile file);
    void showLocationSearch();
    void updateStoryObject(ParseObject activeStory);
    void showDatePickerDialog();
    void showRecorder();


  };
  interface Presenter {
    void openSavedStory(String storyID);
    void createNewStory(String assignmentID);
    void saveStory(ParseObject object);
    void uploadStory();
    void loadAttachments(JSONArray attachments);
    void getLocation();
    void getWhenItOccurred();
    void startRecorder();
  };

}
