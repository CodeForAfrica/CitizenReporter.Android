package org.codeforafrica.citizenreporterandroid.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ahereza on 8/31/17.
 */

public class PrefsHelperImpl implements PrefsHelper {

  private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";
  private static final String PREF_KEY_CURRENT_USER_UID = "PREF_KEY_CURRENT_USER_UID";
  private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
  private static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_URL
      = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL";
  public static final String FALLBACK_PROFILE_PIC_URl = "http://www.freeiconspng"
      + ".com/uploads/account-icon-21.png";

  private static final String PREF_KEY_CURRENT_USER_ONBOARDED = "PREF_KEY_CURRENT_USER_ONBOARDED";

  private final SharedPreferences mPrefs;
  private final Context context;
  private final String prefFileName;

  public PrefsHelperImpl(SharedPreferences mPrefs, Context context, String prefFileName) {
    this.mPrefs = mPrefs;
    this.context = context;
    this.prefFileName = prefFileName;
  }

  @Override public String getCurrentUsername() {
    return mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, "Unknown");
  }

  @Override public void setCurrentUsername(String username) {
    mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NAME, username).apply();
  }

  @Override public String getCurrentUserProfilePicUrl() {
    return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, FALLBACK_PROFILE_PIC_URl);
  }

  @Override public void setCurrentUserProfilePicUrl(String profilePicUrl) {
    mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl).apply();
  }

  @Override public String getCurrentUserUID() {
    return mPrefs.getString(PREF_KEY_CURRENT_USER_UID, "Unknown");
  }

  @Override public void setCurrentUserUID(String uid) {
    mPrefs.edit().putString(PREF_KEY_CURRENT_USER_UID, uid).apply();
  }

  @Override public boolean isCurrentUserLoggedIn() {
    return mPrefs.getBoolean(PREF_KEY_USER_LOGGED_IN_MODE, false);
  }

  @Override public void setUserLoggedOut() {
    mPrefs.edit().putBoolean(PREF_KEY_USER_LOGGED_IN_MODE, false).apply();
  }

  @Override public void setCurrentUserAsLoggedInMode() {
    mPrefs.edit().putBoolean(PREF_KEY_USER_LOGGED_IN_MODE, true).apply();
  }

  @Override public boolean hasCurrentUserBeenOnboarded() {
    return mPrefs.getBoolean(PREF_KEY_CURRENT_USER_ONBOARDED, false);
  }

  @Override public void setCurrentUserHasBeenOnboarded() {
    mPrefs.edit().putBoolean(PREF_KEY_CURRENT_USER_ONBOARDED, true).apply();

  }
}
