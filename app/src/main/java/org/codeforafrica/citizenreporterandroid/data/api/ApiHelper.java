package org.codeforafrica.citizenreporterandroid.data.api;

import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.data.models.User;

/**
 * Created by Ahereza on 9/1/17.
 */

public interface ApiHelper {
  void registerUserDetails(User user);

  void uploadUserStory(Story story);

  void getUserStories(String fb_id);

  void updateLocation(String fb_id, String location);

  void updateFCM(String fb_id, String FCM_token);

  void getAssignments();

}
