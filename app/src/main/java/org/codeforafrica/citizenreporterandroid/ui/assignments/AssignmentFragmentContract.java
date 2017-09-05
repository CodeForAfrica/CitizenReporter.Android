package org.codeforafrica.citizenreporterandroid.ui.assignments;

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
  }

  interface Presenter {
    void setView(AssignmentFragmentContract.View view);
    void getAndDisplayAssignments();
    void pullToRefreshAssignments();
  }
}
