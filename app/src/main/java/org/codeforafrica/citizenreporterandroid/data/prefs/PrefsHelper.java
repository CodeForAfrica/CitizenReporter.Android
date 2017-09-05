package org.codeforafrica.citizenreporterandroid.data.prefs;

/**
 * Created by Ahereza on 8/31/17.
 */

public interface PrefsHelper {

  String getCurrentUsername();

  void setCurrentUsername(String username);

  String getCurrentUserProfilePicUrl();

  void setCurrentUserProfilePicUrl(String profilePicUrl);

  String getCurrentUserFbID();

  void setCurrentUserFBID(String fbid);

  boolean isCurrentUserLoggedIn();

  void setCurrentUserLoggedInMode();

  boolean hasCurrentUserBeenOnboarded();

  void setCurrentUserHasBeenOnboarded();

}
