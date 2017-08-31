package org.codeforafrica.citizenreporterandroid.data;

import java.util.List;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;

/**
 * Created by Ahereza on 8/31/17.
 */

public interface DataManager {
  List<Assignment> loadAssignments();

  List<Assignment> fetchAssignmentsAPI();

  void saveAssignmentsIntoDb(List<Assignment> assignments);

  void clearAssignmentsTable();

}
