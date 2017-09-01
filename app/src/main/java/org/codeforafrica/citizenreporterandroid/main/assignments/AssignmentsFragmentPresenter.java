package org.codeforafrica.citizenreporterandroid.main.assignments;

import android.content.Context;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.app.CitizenReporterApplication;
import org.codeforafrica.citizenreporterandroid.data.DataManager;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.utils.CReporterAPI;

/**
 * Created by Ahereza on 7/28/17.
 */

public class AssignmentsFragmentPresenter implements AssignmentFragmentContract.Presenter {
  private AssignmentFragmentContract.View view;
  @Inject DataManager manager;
  private List<Assignment> assignmentsList;

  @Inject
  public AssignmentsFragmentPresenter(Context context) {
    ((CitizenReporterApplication) context).getAppComponent().inject(this);

  }

  @Override public void setView(AssignmentFragmentContract.View view) {
    this.view = view;
  }

  @Override public void getAndDisplayAssignments() {
    view.showLoading();
    //List<Assignment> assignmentList = manager.loadAssignmentsFromDb();
    //List<Assignment> assignmentList = Collections.EMPTY_LIST;
    List<Assignment> assignmentList = Arrays.asList(new Assignment(), new Assignment());
    checkNumberOfAssignments(assignmentList);
    view.hideLoading();
  }

  @Override public void pullToRefreshAssignments() {
    // query the data manager to get assignments from the server
    view.showLoading();
    manager.clearAssignmentsTable();
    List<Assignment> assignmentList = manager.fetchAssignmentsAPI();
    manager.saveAssignmentsIntoDb(assignmentList);
    List<Assignment> assignments = manager.loadAssignmentsFromDb();
    checkNumberOfAssignments(assignments);
    view.hideLoading();

  }

  @Override public void goToAssignmentDetail(Assignment assignment) {
    // go the assignment detail view
    view.showAssignmentDetails();
  }

  @Override public void checkForInternet() {
    // check if there is internet
    view.showNoInternetError();

  }

  private void checkNumberOfAssignments(List<Assignment> assignmentsList) {
    if (assignmentsList.size() > 0) {
      view.displayAssignments(assignmentsList);
    } else {
      view.displayNoAssignments();
    }
  }
}
