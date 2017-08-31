package org.codeforafrica.citizenreporterandroid.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import java.util.List;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.main.assignments.AssignmentDetailActivity;

/**
 * Created by Mugiwara_Munyi on 30/07/2017.
 */

public class AssignmentsAdapter
    extends RecyclerView.Adapter<AssignmentsAdapter.AssignmentsViewHolder> {

  public class AssignmentsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.assignment_title) TextView assignment_title;
    @BindView(R.id.assignment_deadline) TextView assignment_deadline;
    @BindView(R.id.assignment_location) TextView assignment_location;
    @BindView(R.id.assignment_author) TextView assignment_author;
    @BindView(R.id.assignment_image) ImageView featured_image;
    @BindView(R.id.assignment_details) Button assignment_details;

    public AssignmentsViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  private Context mContext;
  private List<Assignment> assignmentsList;

  public void setAssignmentList(List<Assignment> assignmentsList) {
    this.assignmentsList = assignmentsList;
  }

  public AssignmentsAdapter(List<Assignment> assignmentsList, Context context) {
    mContext = context;
    this.assignmentsList = assignmentsList;
  }

  @Override public AssignmentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignments, parent, false);

    return new AssignmentsViewHolder(view);
  }

  @Override public void onBindViewHolder(final AssignmentsViewHolder viewHolder, int position) {

    final Assignment assignment = assignmentsList.get(position);

    viewHolder.assignment_title.setText(assignment.getTitle());
    viewHolder.assignment_deadline.setText(assignment.getDeadline());
    viewHolder.assignment_location.setText(assignment.getAssignmentLocation());
    viewHolder.assignment_author.setText(assignment.getAuthor());
    Glide.with(mContext).load(assignment.getFeaturedImage()).into(viewHolder.featured_image);
    viewHolder.assignment_details.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(mContext, AssignmentDetailActivity.class);
        intent.putExtra("assignment_id", assignment.getId());
        mContext.startActivity(intent);
      }
    });
  }

  @Override public int getItemCount() {
    return assignmentsList.size();
  }
}