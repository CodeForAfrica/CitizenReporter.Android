package org.codeforafrica.citizenreporterandroid.ui.stories;

import android.content.SharedPreferences;
import java.util.List;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.data.DataManager;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.data.models.Story;

/**
 * Created by Ahereza on 9/3/17.
 */

public class StoriesFragmentPresenter implements StoriesFragmentContract.Presenter {
  private DataManager dataManager;
  private StoriesFragmentContract.View view;

  @Inject public StoriesFragmentPresenter(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Override public void getStoriesFromDb() {
    view.showLoading();
    List<Story> storyList = dataManager.fetchStoriesFromDb();
    checkNumberOfStories(storyList);
    view.hideLoading();

  }

  @Override public void getStoriesFromNetwork() {
    view.showLoading();
    String fb_id = dataManager.getCurrentUserFbID();
    dataManager.getUserStories(fb_id);
    List<Story> storyList = dataManager.fetchStoriesFromDb();
    checkNumberOfStories(storyList);
    view.hideLoading();
  }

  @Override public void deleteStory() {

  }

  @Override public void setView(StoriesFragmentContract.View view) {
    this.view = view;
  }

  private void checkNumberOfStories(List<Story> storyList) {
    if (storyList.size() > 0) {
      view.displayStories(storyList);
    } else {
      view.displayNoStories();
    }
  }
}
