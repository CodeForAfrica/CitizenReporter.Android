package org.codeforafrica.citizenreporterandroid.data;

import android.util.Log;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.List;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.data.models.Story;

/**
 * Created by Ahereza on 9/11/17.
 */

public class ParseHelper {
  private static final String TAG = ParseHelper.class.getSimpleName();
  public static void getParseAssignments() {
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Assignment");
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, ParseException e) {
        if (e == null) {
          try {
            Log.d(TAG, "Got all assignments: " + objects.size());
            ParseObject.pinAllInBackground(objects);
          } catch (NullPointerException e1) {
            e1.printStackTrace();
          }
        }

      }
    });

  }

  public static void getParseStories() {
    ParseQuery<ParseObject> storiesQuery = ParseQuery.getQuery("Story");
    storiesQuery.findInBackground(new FindCallback<ParseObject>() {
      public void done(List<ParseObject> storyList, ParseException e) {
        if (e == null) {
          try {
            Log.d("Stories", "done: storyList " + storyList.size());
            ParseObject.pinAllInBackground(storyList);
          } catch (NullPointerException e1) {
            e1.printStackTrace();
          }
        }


      }
    });
  }
}
