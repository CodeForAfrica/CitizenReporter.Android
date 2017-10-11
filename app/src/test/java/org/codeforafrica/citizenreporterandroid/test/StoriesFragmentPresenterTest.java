package org.codeforafrica.citizenreporterandroid.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.ui.stories.StoriesFragmentContract;
import org.codeforafrica.citizenreporterandroid.ui.stories.StoriesFragmentPresenter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;

/**
 * Created by Ahereza on 9/3/17.
 */
public class StoriesFragmentPresenterTest {
  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
  StoriesFragmentPresenter presenter;
  @Mock StoriesFragmentContract.View view;
  @Mock DataManager manager;
  List<Story> MANY_STORIES = Arrays.asList(new Story(), new Story(), new Story());
  String fb_id = "FbID";

  @Before public void setUp() {
    presenter = new StoriesFragmentPresenter(manager);
    presenter.attachView(view);
  }


  @Test public void getNoStoriesFromDb() throws Exception {
    Mockito.when(manager.fetchStoriesFromDb()).thenReturn(Collections.EMPTY_LIST);
    verify(view).showLoading();
    verify(manager).fetchStoriesFromDb();
    verify(view).hideLoading();
    verify(view).displayNoStories();

  }

  @Test public void getManyStoriesFromDb() throws Exception {
    Mockito.when(manager.fetchStoriesFromDb()).thenReturn(MANY_STORIES);

    verify(view).showLoading();
    verify(manager).fetchStoriesFromDb();
    verify(view).hideLoading();

  }

  @Test public void getStoriesFromNetwork() throws Exception {

    view.showLoading();
    Mockito.when(manager.getCurrentUserUID()).thenReturn(fb_id);
    verify(manager).getCurrentUserUID();
    //verify(manager).getUserStories(fb_id);
    verify(manager).fetchStoriesFromDb();
    verify(view).hideLoading();

  }

  @Test public void deleteStory() throws Exception {
    presenter.deleteStory(1);
    verify(view).swipeToDelete();
  }
}