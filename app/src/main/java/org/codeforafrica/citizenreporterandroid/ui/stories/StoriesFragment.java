package org.codeforafrica.citizenreporterandroid.ui.stories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.parse.ParseObject;
import java.util.List;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.adapter.StoriesRecyclerViewAdapter;
import org.codeforafrica.citizenreporterandroid.app.CitizenReporterApplication;

public class StoriesFragment extends Fragment implements StoriesFragmentContract.View {
  @BindView(R.id.stories_recyclerview) RecyclerView storiesRecyclerView;
  @BindView(R.id.stories_loading_progressbar) ProgressBar storiesProgressBar;
  @BindView(R.id.stories_error_layout) LinearLayout errorLayout;
  private StoriesRecyclerViewAdapter adapter;

  @Inject StoriesFragmentPresenter presenter;

  public StoriesFragment() {
    // Required empty public constructor
  }

  // TODO: Rename and change types and number of parameters
  public static StoriesFragment newInstance() {
    StoriesFragment fragment = new StoriesFragment();
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    ((CitizenReporterApplication) getActivity().getApplication()).getAppComponent().inject(this);
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_stories, container, false);
    presenter.attachView(this);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    storiesRecyclerView.setHasFixedSize(true);
    storiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    presenter.loadStories();
  }

  @Override public void onResume() {
    super.onResume();
    presenter.loadStories();
  }

  @Override public void showLoading() {
    storiesProgressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    storiesProgressBar.setVisibility(View.GONE);
  }

  @Override public void displayStories(List<ParseObject> stories) {
    adapter = new StoriesRecyclerViewAdapter(stories, getContext());
    storiesRecyclerView.setAdapter(adapter);
  }

  @Override public void displayNoStories() {
    errorLayout.setVisibility(View.VISIBLE);
  }

  @Override public void swipeToDelete() {

  }

  @Override public void onDestroy() {
    super.onDestroy();
    presenter.detachView();
  }
}
