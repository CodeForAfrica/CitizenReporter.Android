package org.codeforafrica.citizenreporterandroid.main.assignments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import org.codeforafrica.citizenreporterandroid.GlideApp;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.app.Constants;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.storyboard.Storyboard;
import org.codeforafrica.citizenreporterandroid.utils.TimeUtils;

/**
 * Created by Mugiwara_Munyi on 14/08/2017.
 */

public class AssignmentDetailActivity extends Activity {
  @BindView(R.id.assignment_detail_title) TextView assignment_detail_title;

  @BindView(R.id.featuredImageView) ImageView featured_image;

  @BindView(R.id.assignment_detail_deadline) TextView assignment_detail_deadline;

  @BindView(R.id.assignment_detail_text) TextView assignment_detail_text;

  @BindView(R.id.assignment_detail_author) TextView assignment_detail_author;


  private Assignment assignment;
  private String assignmentID;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_assignment_detail);

    ButterKnife.bind(this);

    assignmentID = getIntent().getStringExtra("assignment_id");
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Assignment");
    query.fromLocalDatastore();
    query.getInBackground(assignmentID, new GetCallback<ParseObject>() {
      public void done(ParseObject assignmentObject, ParseException e) {
        if (e == null) {
          assignment_detail_title.setText(assignmentObject.getString("title"));
          assignment_detail_deadline.setText(
              TimeUtils.getShortDateFormat(assignmentObject.getDate("deadline")));
          assignment_detail_text.setText(assignmentObject.getString("description"));
          assignment_detail_author.setText(assignmentObject.getString("author"));
          GlideApp.with(AssignmentDetailActivity.this)
              .load(assignmentObject.getParseFile("featured_image").getUrl())
              .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
              .into(featured_image);
        } else {
          // something went wrong
          Toast.makeText(AssignmentDetailActivity.this, "This assignment was not found",
              Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  @OnClick(R.id.start_reporting_button) public void start_reporting() {
    Intent report = new Intent(this, Storyboard.class);
    report.setAction(Constants.ACTION_NEW_STORY);
    report.putExtra("assignmentID", assignmentID);
    startActivity(report);
  }
}
