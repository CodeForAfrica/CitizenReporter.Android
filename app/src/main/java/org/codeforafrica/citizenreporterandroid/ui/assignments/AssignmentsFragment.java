package org.codeforafrica.citizenreporterandroid.ui.assignments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import butterknife.ButterKnife;
import java.util.List;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.adapter.AssignmentsAdapter;
import org.codeforafrica.citizenreporterandroid.app.CitizenReporterApplication;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;

public class AssignmentsFragment extends Fragment implements AssignmentFragmentContract.View {

  private RecyclerView recyclerView;

  private SwipeRefreshLayout refreshLayout;

  private ProgressBar progressBar;

  private LinearLayout error_layout;

  @Inject AssignmentsFragmentPresenter presenter;

  private AssignmentsAdapter adapter;

  public AssignmentsFragment() {
    // Required empty public constructor
  }

  public static AssignmentsFragment newInstance() {
    AssignmentsFragment fragment = new AssignmentsFragment();
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    ((CitizenReporterApplication) getActivity().getApplication()).getAppComponent().inject(this);
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_assignments, container, false);
    presenter.setView(this);
    ButterKnife.bind(view);

    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    recyclerView = (RecyclerView) view.findViewById(R.id.assignment_recycler);
    progressBar = (ProgressBar) view.findViewById(R.id.assignmentsLoadingProgressBar);
    error_layout = (LinearLayout) view.findViewById(R.id.assignments_error_layout);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    presenter.getAndDisplayAssignments();

    refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        Log.d("Assignments", "onRefresh: Swipe to refresh");
        refreshLayout.setRefreshing(false);
        refreshLayout.setEnabled(false);
        presenter.pullToRefreshAssignments();
      }
    });
  }

  @Override public void showLoading() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    progressBar.setVisibility(View.GONE);
  }

  @Override public void displayAssignments(List<Assignment> assignmentList) {
    recyclerView.setVisibility(View.VISIBLE);
    adapter = new AssignmentsAdapter(assignmentList, getContext());
    recyclerView.setAdapter(adapter);
  }

  @Override public void displayNoAssignments() {
    error_layout.setVisibility(View.VISIBLE);
  }

  @Override public void showError() {
    error_layout.setVisibility(View.VISIBLE);
  }
}
