package org.codeforafrica.citizenreporterandroid.data;

import java.util.List;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;

/**
 * Created by Ahereza on 8/31/17.
 */

public class DataManagerImp implements DataManager {
  @Override public String getCurrentUsername() {
    return null;
  }

  @Override public void setCurrentUsername(String username) {

  }

  @Override public String getCurrentUserProfilePicUrl() {
    return null;
  }

  @Override public void setCurrentUserProfilePicUrl(String profilePicUrl) {

  }

  @Override public String getCurrentUserFbID() {
    return null;
  }

  @Override public void setCurrentUserFBID(String fbid) {

  }

  @Override public boolean isCurrentUserLoggedIn() {
    return false;
  }

  @Override public void setCurrentUserLoggedInMode() {

  }

  @Override public boolean hasCurrentUserBeenOnboarded() {
    return false;
  }

  @Override public void setCurrentUserHasBeenOnboarded() {

  }

  @Override public List<Assignment> loadAssignments() {
    return null;
  }

  @Override public List<Assignment> fetchAssignmentsAPI() {
    return null;
  }

  @Override public void saveAssignmentsIntoDb(List<Assignment> assignments) {

  }

  @Override public void clearAssignmentsTable() {

  }
}
