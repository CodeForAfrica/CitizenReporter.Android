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
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.data.sources.LocalDataHelper;
import org.codeforafrica.citizenreporterandroid.storyboard.Storyboard;
import org.codeforafrica.citizenreporterandroid.utils.Constants;

/**
 * Created by Mugiwara_Munyi on 14/08/2017.
 */

public class AssignmentDetailActivity extends Activity {
  @BindView(R.id.assignment_detail_title) TextView assignment_detail_title;

  @BindView(R.id.featuredImageView) ImageView featured_image;

  @BindView(R.id.assignment_detail_deadline) TextView assignment_detail_deadline;

  @BindView(R.id.assignment_detail_text) TextView assignment_detail_text;

  @BindView(R.id.assignment_detail_location) TextView assignment_detail_location;

  @BindView(R.id.assignment_detail_author) TextView assignment_detail_author;

  private LocalDataHelper dataHelper;

  private Assignment assignment;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_assignment_detail);
    dataHelper = new LocalDataHelper(this);
    ButterKnife.bind(this);

    int assignmentID = getIntent().getIntExtra("assignment_id", -1);
    if (assignmentID == -1) {
      Toast.makeText(this, "This assignment was not found", Toast.LENGTH_SHORT).show();
    } else {
      assignment = dataHelper.getAssignment(assignmentID);
      assignment_detail_title.setText(assignment.getTitle());
      assignment_detail_deadline.setText(assignment.getDeadline());
      assignment_detail_text.setText(assignment.getDescription());
      assignment_detail_author.setText(assignment.getAuthor());
      assignment_detail_location.setText(assignment.getAssignmentLocation());
    }
  }

  @OnClick(R.id.start_reporting_button) public void start_reporting() {
    Intent report = new Intent(this, Storyboard.class);
    report.setAction(Constants.ACTION_NEW_STORY);
    report.putExtra("assignmentID", assignment.getId());
    startActivity(report);
  }
}
