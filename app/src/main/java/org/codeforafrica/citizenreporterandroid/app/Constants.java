package org.codeforafrica.citizenreporterandroid.app;

/**
 * Created by Ahereza on 7/31/17.
 */

public class Constants {
  public static final String BASE_URL = "http://api.creporter.codeforafrica.org/api/";
  //public static final String BASE_URL = "http://a865dfff.ngrok.io/api/";

  public static final String SHARED_PREF_FILENAME = "creporter_prefs";

  public static final String DB_NAME = "citizenReporterDB";
  public static final int DB_VERSION = 1;

  // stories table
  public static final String STORIES_TABLE_NAME = "storiesTBL";

  // stories columns

  public static final String KEY_ID = "id";
  public static final String KEY_TITLE = "title";
  public static final String KEY_ASSIGNMENT_ID = "assignment";
  public static final String KEY_WHY = "why";
  public static final String KEY_WHO = "who";
  public static final String KEY_AUTHOR = "author";
  public static final String KEY_AUTHOR_ID = "author_id";
  public static final String KEY_WHEN = "q_when";
  public static final String KEY_MEDIA = "media";
  public static final String KEY_UPLOADED = "uploaded";
  public static final String KEY_UPDATED = "updated";
  public static final String KEY_WHERE = "location";

  //Assignment table
  public static final String ASSIGNMENTS_TABLE_NAME = "assignments";

  //Assignment columns

  public static final String KEY_ASSIGNMENT_TITLE = "title";
  public static final String KEY_ASSIGNMENT_DESCRIPTION = "description";
  public static final String KEY_ASSIGNMENT_MEDIA = "requiredMedia";
  public static final String KEY_ASSIGNMENT_RESPONSES = "numberOfResponses";
  public static final String KEY_ASSIGNMENT_AUTHOR = "author";
  public static final String KEY_ASSIGNMENT_DEADLINE = "deadline";
  public static final String KEY_ASSIGNMENT_LOCATION = "assignmentLocation";

  public static final String KEY_ASSIGNMENT_UPDATED = "updated";

  // shared preferences

  public static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";
  public static final String PREF_KEY_CURRENT_USER_UID = "PREF_KEY_CURRENT_USER_UID";
  public static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
  public static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_URL
      = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL";
  public static final String FALLBACK_PROFILE_PIC_URl = "http://www.freeiconspng"
      + ".com/uploads/account-icon-21.png";

  public static final String PREF_KEY_CURRENT_USER_ONBOARDED = "PREF_KEY_CURRENT_USER_ONBOARDED";

  // storyboard

  public static final int REQUEST_RECORD_AUDIO = 321;
  public static final int REQUEST_CAMERA_PERMISSION = 729;
  public static final String RECORDING_PREFIX = "/cr_";
  public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 31;
  public static final String AUDIO_MIMETYPE = "audio/x-wav";

  // intents

  public static final String ACTION_EDIT_VIEW_STORY = "EDIT_VIEW_STORY";
  public static final String ACTION_NEW_STORY = "NEW_STORY";

  public static final int CUSTOM_CAMERA_REQUEST_CODE = 9432;

  // mode constants

  public static final int CAMERA_MODE = 673;
  public static final int VIDEO_MODE = 674;
}
