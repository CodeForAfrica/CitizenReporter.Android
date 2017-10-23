package org.codeforafrica.citizenreporterandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.List;
import org.codeforafrica.citizenreporterandroid.GlideApp;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.main.assignments.AssignmentDetailActivity;
import org.codeforafrica.citizenreporterandroid.utils.TimeUtils;

/**
 * Created by Mugiwara_Munyi on 30/07/2017.
 */

public class AssignmentsAdapter
    extends RecyclerView.Adapter<AssignmentsAdapter.AssignmentsViewHolder> {
  private static final String TAG = AssignmentsAdapter.class.getSimpleName();

  public class AssignmentsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.assignment_title) TextView assignment_title;
    @BindView(R.id.assignment_deadline) TextView assignment_deadline;
    @BindView(R.id.assignment_location) TextView assignment_location;
    @BindView(R.id.assignment_image) ImageView featured_image;
    @BindView(R.id.assignment_card) CardView assignment_card;

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
    viewHolder.assignment_deadline.setText(TimeUtils.getShortDateFormat(assignment.getDeadline()));
    viewHolder.assignment_location.setText(assignment.getAssignmentLocation());
    GlideApp.with(mContext)
        .load(assignment.getFeaturedImage())
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(viewHolder.featured_image);

    viewHolder.assignment_card.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(mContext, AssignmentDetailActivity.class);
        intent.putExtra("assignment_id", assignment.getId());
        Log.d(TAG, "onClick: assignmentID" + assignment.getId());
        mContext.startActivity(intent);
      }
    });
  }

  @Override public int getItemCount() {
    return assignmentsList.size();
  }

  public void notify(List<Assignment> list) {
    if (list != null) {
      list.clear();
      list.addAll(list);
    } else {
      assignmentsList = list;
    }
    notifyDataSetChanged();
  }
}