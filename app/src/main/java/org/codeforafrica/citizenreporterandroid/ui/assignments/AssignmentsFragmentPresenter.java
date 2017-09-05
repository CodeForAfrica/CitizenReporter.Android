package org.codeforafrica.citizenreporterandroid.ui.assignments;

import java.util.List;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.data.DataManager;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;

/**
 * Created by Ahereza on 7/28/17.
 */

public class AssignmentsFragmentPresenter implements AssignmentFragmentContract.Presenter {
  private AssignmentFragmentContract.View view;
  private DataManager dataManager;

  @Inject public AssignmentsFragmentPresenter(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Override public void setView(AssignmentFragmentContract.View view) {
    this.view = view;
  }

  @Override public void getAndDisplayAssignments() {
    view.showLoading();
    List<Assignment> assignmentList = dataManager.loadAssignmentsFromDb();

    checkNumberOfAssignments(assignmentList);
    view.hideLoading();
  }

  @Override public void pullToRefreshAssignments() {
    // query the data dataManager to get assignments from the server
    view.showLoading();
    List<Assignment> assignments = dataManager.loadAssignmentsFromDb();
    checkNumberOfAssignments(assignments);
    view.hideLoading();
  }

  private void checkNumberOfAssignments(List<Assignment> assignmentsList) {
    if (assignmentsList.size() > 0) {
      view.displayAssignments(assignmentsList);
    } else {
      view.displayNoAssignments();
    }
  }
}
