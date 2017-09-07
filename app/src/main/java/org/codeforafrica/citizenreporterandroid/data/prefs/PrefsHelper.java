package org.codeforafrica.citizenreporterandroid.data.prefs;

/**
 * Created by Ahereza on 8/31/17.
 */

public interface PrefsHelper {

  String getCurrentUsername();

  void setCurrentUsername(String username);

  String getCurrentUserProfilePicUrl();

  void setCurrentUserProfilePicUrl(String profilePicUrl);

  String getCurrentUserUID();

  void setCurrentUserUID(String uid);

  boolean isCurrentUserLoggedIn();

  void setUserLoggedOut();

  void setCurrentUserAsLoggedInMode();

  boolean hasCurrentUserBeenOnboarded();

  void setCurrentUserHasBeenOnboarded();

}
