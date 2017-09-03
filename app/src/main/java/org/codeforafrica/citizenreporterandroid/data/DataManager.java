package org.codeforafrica.citizenreporterandroid.data;

import java.util.List;
import org.codeforafrica.citizenreporterandroid.data.api.ApiHelper;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.data.prefs.PrefsHelper;

/**
 * Created by Ahereza on 8/31/17.
 */

public interface DataManager extends PrefsHelper, ApiHelper {
  List<Assignment> loadAssignmentsFromDb();

  List<Assignment> fetchAssignmentsAPI();

  void saveAssignmentsIntoDb(List<Assignment> assignments);

  void clearAssignmentsTable();

  List<Story> fetchStoriesFromDb();

}
