package org.codeforafrica.citizenreporterandroid.ui.assignments;

import android.util.Log;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;

/**
 * Created by Ahereza on 7/28/17.
 */

public class AssignmentsFragmentPresenter implements AssignmentFragmentContract.Presenter {
  private AssignmentFragmentContract.View view;

  @Inject public AssignmentsFragmentPresenter() {
  }

  @Override public void setView(AssignmentFragmentContract.View view) {
    this.view = view;
  }

  @Override public void getAndDisplayAssignments() {
    Date currentTime = new Date();
    view.showLoading();
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Assignment");
    query.fromLocalDatastore();
    query.whereGreaterThan("deadline", currentTime);
    query.addDescendingOrder("createdAt");
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, ParseException e) {
        List<Assignment> assignments = parseListAssignments(objects);
        Log.d("Assignments", "done: " + assignments.size());
        checkNumberOfAssignments(assignments);
      }
    });


    view.hideLoading();
  }

  @Override public void pullToRefreshAssignments() {
    // query the data dataManager to get assignments from the server
    view.showLoading();
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Assignment");
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, ParseException e) {
        ParseObject.pinAllInBackground(objects);
        view.hideLoading();
        getAndDisplayAssignments();
      }
    });
    view.hideLoading();
  }

  private void checkNumberOfAssignments(List<Assignment> assignmentsList) {
    if (assignmentsList.size() > 0) {
      view.displayAssignments(assignmentsList);
    } else {
      view.displayNoAssignments();
    }
  }

  private Assignment parseObjectToAssignment(ParseObject assignmentObject) {
    Assignment assignment = new Assignment();
    assignment.setAuthor(assignmentObject.getString("author"));
    assignment.setTitle(assignmentObject.getString("title"));
    assignment.setDescription(assignmentObject.getString("description"));
    assignment.setAssignmentLocation(assignmentObject.getString("location"));
    assignment.setFeaturedImage(assignmentObject.getParseFile
        ("featured_image").getUrl());
    Log.d("Parse", "parseObjectToAssignment featuredImage: " + assignmentObject.getParseFile
        ("featured_image").getUrl());
    assignment.setDeadline(assignmentObject.getDate("deadline"));
    assignment.setId(assignmentObject.getObjectId());

    return assignment;
  }

  private List<Assignment> parseListAssignments(List<ParseObject> assignmentObjects) {
    List<Assignment> assignmentList = new ArrayList<>();
    for (ParseObject assignmentObject : assignmentObjects) {
      assignmentList.add(parseObjectToAssignment(assignmentObject));
    }
    return assignmentList;
  }
}
