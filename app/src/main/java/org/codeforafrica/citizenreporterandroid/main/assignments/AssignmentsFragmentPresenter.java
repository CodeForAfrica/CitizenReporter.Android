package org.codeforafrica.citizenreporterandroid.main.assignments;

import java.util.List;
import org.codeforafrica.citizenreporterandroid.data.DataManager;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;

/**
 * Created by Ahereza on 7/28/17.
 */

public class AssignmentsFragmentPresenter implements AssignmentFragmentContract.Presenter {
  private AssignmentFragmentContract.View view;
  private DataManager manager;
  private List<Assignment> assignmentsList;

  public AssignmentsFragmentPresenter(AssignmentFragmentContract.View view,
      DataManager manager) {
    this.view = view;
    this.manager = manager;
  }

  @Override public void getAssignments() {
    view.showLoading();
    List<Assignment> assignmentList = manager.loadAssignments();
    checkNumberOfAssignments(assignmentList);
    view.hideLoading();
  }

  @Override public void pullToRefreshAssignments() {
    // query the data manager to get assignments from the server
    view.showLoading();
    manager.clearAssignmentsTable();
    List<Assignment> assignmentList = manager.fetchAssignmentsAPI();
    manager.saveAssignmentsIntoDb(assignmentList);
    List<Assignment> assignments = manager.loadAssignments();
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
