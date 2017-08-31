package org.codeforafrica.citizenreporterandroid.main.assignments;

import java.util.List;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;

/**
 * Created by Ahereza on 8/31/17.
 */

public interface AssignmentFragmentContract {
  interface View {
    void showLoading();
    void hideLoading();
    void displayAssignments(List<Assignment> assignmentList);
    void displayNoAssignments();
    void showError();
    void showNoInternetError();
    void showAssignmentDetails();
  }

  interface Presenter {
    void getAssignments();
    void pullToRefreshAssignments();
    void goToAssignmentDetail(Assignment assignment);
    void checkForInternet();
  }
}
