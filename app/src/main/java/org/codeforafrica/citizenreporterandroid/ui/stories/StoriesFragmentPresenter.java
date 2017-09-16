package org.codeforafrica.citizenreporterandroid.ui.stories;

import android.util.Log;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.List;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.data.DataManager;
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



  @Override public void loadStories() {
    view.showLoading();
    ParseUser user = ParseUser.getCurrentUser();
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
    query.fromLocalDatastore();
    query.findInBackground(new FindCallback<ParseObject>() {
      public void done(List<ParseObject> storyList, ParseException e) {
        Log.d("Stories", "done: storyList " + storyList.size());
        checkNumberOfStories(storyList);
      }
    });
    view.hideLoading();
  }

  @Override public void deleteStory(int storyID) {
    view.swipeToDelete();
    dataManager.deleteStory(storyID);
  }

  @Override public void attachView(StoriesFragmentContract.View view) {
    this.view = view;
  }

  @Override public void detachView() {
    this.view = null;
  }

  private void checkNumberOfStories(List<ParseObject> storyList) {
    if (storyList != null && storyList.size() > 0) {
      view.displayStories(storyList);
    } else {
      view.displayNoStories();
    }
  }
}
