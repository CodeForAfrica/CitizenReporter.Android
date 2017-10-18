package org.codeforafrica.citizenreporterandroid.main.assignments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import org.codeforafrica.citizenreporterandroid.GlideApp;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.app.Constants;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.receiver.Notifications;
import org.codeforafrica.citizenreporterandroid.storyboard.Storyboard;
import org.codeforafrica.citizenreporterandroid.utils.AnalyticsHelper;
import org.codeforafrica.citizenreporterandroid.utils.TimeUtils;

import java.util.HashMap;

/**
 * Created by Mugiwara_Munyi on 14/08/2017.
 */

public class AssignmentDetailActivity extends Activity {

  private static final String TAG = "AssignmentDetail";

  @BindView(R.id.assignment_detail_title) TextView assignment_detail_title;

  @BindView(R.id.featuredImageView) ImageView featured_image;

  @BindView(R.id.assignment_detail_deadline) TextView assignment_detail_deadline;

  @BindView(R.id.assignment_detail_text) TextView assignment_detail_text;

  @BindView(R.id.assignment_detail_author) TextView assignment_detail_author;


  private Assignment assignment;
  private String assignmentID;
  private Boolean is_new_assignment;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_assignment_detail);

    ButterKnife.bind(this);

    assignmentID = getIntent().getStringExtra("assignment_id");
    is_new_assignment = getIntent().getExtras().getBoolean("is_new_assignment");


    if (is_new_assignment){
      Notifications notifications = Notifications.getInstance("");
      notifications.clearNotifications();
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Assignment");
      query.getInBackground(assignmentID, new GetCallback<ParseObject>() {
        public void done(ParseObject object, ParseException e) {
          if (e == null) {
            String assignmentTitle = object.getString("title");
            assignment_detail_title.setText(assignmentTitle);
            assignment_detail_deadline.setText(
                    TimeUtils.getShortDateFormat(object.getDate("deadline")));
            assignment_detail_text.setText(object.getString("description"));
            assignment_detail_author.setText(object.getString("author"));
            GlideApp.with(AssignmentDetailActivity.this)
                    .load(object.getParseFile("featured_image").getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(featured_image);
            trackAssignmentOpen(assignmentTitle);
            try {
              object.pin();
            } catch (ParseException exception) {
              Log.e(TAG, exception.getMessage());
            }
          } else {
            Log.e(TAG, e.getMessage());
            Toast.makeText(AssignmentDetailActivity.this, "This assignment was not found",
                    Toast.LENGTH_SHORT).show();
          }
        }
      });
    } else {
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Assignment");
      query.fromLocalDatastore();
      query.getInBackground(assignmentID, new GetCallback<ParseObject>() {
        public void done(ParseObject assignmentObject, ParseException e) {
          if (e == null) {
            String assignmentTitle = assignmentObject.getString("title");
            assignment_detail_title.setText(assignmentTitle);
            assignment_detail_deadline.setText(
                    TimeUtils.getShortDateFormat(assignmentObject.getDate("deadline")));
            assignment_detail_text.setText(assignmentObject.getString("description"));
            assignment_detail_author.setText(assignmentObject.getString("author"));
            GlideApp.with(AssignmentDetailActivity.this)
                    .load(assignmentObject.getParseFile("featured_image").getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(featured_image);
            trackAssignmentOpen(assignmentTitle);
          } else {
            // something went wrong
            Toast.makeText(AssignmentDetailActivity.this, "This assignment was not found",
                    Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }

  private void trackAssignmentOpen(String assignmentTitle) {
    // Track Assignment open
    HashMap<String, String> assignmentParameters = new HashMap<>(2);
    assignmentParameters.put(AnalyticsHelper.PARAM_ASSIGNMENT_TITLE, assignmentTitle);
    AnalyticsHelper.logEvent(AnalyticsHelper.EVENT_ASSIGNMENT_OPEN, assignmentParameters, true);
  }

  @OnClick(R.id.start_reporting_button) public void start_reporting() {
    Intent report = new Intent(this, Storyboard.class);
    report.setAction(Constants.ACTION_NEW_STORY);
    report.putExtra("assignmentID", assignmentID);
    startActivity(report);
  }
}
