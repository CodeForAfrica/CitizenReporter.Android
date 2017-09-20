package org.codeforafrica.citizenreporterandroid.ui.stories;

import com.parse.ParseObject;
import java.util.List;
import org.codeforafrica.citizenreporterandroid.data.models.Story;

/**
 * Created by Ahereza on 8/31/17.
 */

public interface StoriesFragmentContract {
  interface View{
    void showLoading();
    void hideLoading();
    void displayStories(List<ParseObject> stories);
    void displayNoStories();
    void swipeToDelete();
  };
  interface Presenter{
    void loadStories();
    void attachView(View view);
    void detachView();
  };
}
