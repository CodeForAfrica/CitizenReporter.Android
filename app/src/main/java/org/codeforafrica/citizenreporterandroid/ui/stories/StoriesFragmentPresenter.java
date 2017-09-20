package org.codeforafrica.citizenreporterandroid.ui.stories;

import android.util.Log;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Ahereza on 9/3/17.
 */

public class StoriesFragmentPresenter implements StoriesFragmentContract.Presenter {
  private StoriesFragmentContract.View view;

  @Inject public StoriesFragmentPresenter() {
  }

  @Override public void loadStories() {
    view.showLoading();
    ParseUser user = ParseUser.getCurrentUser();
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
    query.whereEqualTo("author", user.getObjectId());
    query.fromLocalDatastore();
    query.findInBackground(new FindCallback<ParseObject>() {
      public void done(List<ParseObject> storyList, ParseException e) {
        Log.d("Stories", "done: storyList " + storyList.size());
        checkNumberOfStories(storyList);
      }
    });
    view.hideLoading();
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
