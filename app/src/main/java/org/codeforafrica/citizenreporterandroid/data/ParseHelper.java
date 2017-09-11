package org.codeforafrica.citizenreporterandroid.data;

import java.util.List;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.data.models.Story;

/**
 * Created by Ahereza on 9/11/17.
 */

public interface ParseHelper {
  List<Assignment> parseRetrieveAssignments();
  List<Story> parseRetrieveUserStories();
  void parseSaveStory(Story story);
  void parseUploadStory(String key);
}
