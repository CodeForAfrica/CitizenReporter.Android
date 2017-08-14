package org.codeforafrica.citizenreporterandroid.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codeforafrica.citizenreporterandroid.main.MainActivity;
import org.codeforafrica.citizenreporterandroid.main.models.Assignments;
import org.codeforafrica.citizenreporterandroid.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mugiwara_Munyi on 30/07/2017.
 */

public class AssignmentsAdapter extends
        RecyclerView.Adapter<AssignmentsAdapter.AssignmentsViewHolder> {

    public class AssignmentsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.assignment_title)
        TextView assignment_title;
        @BindView(R.id.assignment_deadline)
        TextView assignment_deadline;
        @BindView(R.id.assignment_location)
        TextView assignment_location;
        @BindView(R.id.assignment_author)
        TextView assignment_author;
        @BindView(R.id.assignment_details)
        Button assignment_details;

        public AssignmentsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }


    }

    private Context mContext;
    private List<Assignments> assignmentsList;


    public void setAssignmentList(List<Assignments> assignmentsList) {
        this.assignmentsList = assignmentsList;
    }

    public AssignmentsAdapter(List<Assignments> assignmentsList, Context context) {
        mContext = context;
        this.assignmentsList = assignmentsList;
    }


    @Override
    public AssignmentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_assignments, parent, false);


        return new AssignmentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AssignmentsViewHolder viewHolder, int position) {

        Assignments assignment = assignmentsList.get(position);

        viewHolder.assignment_title.setText(assignment.getTitle());
        viewHolder.assignment_deadline.setText(assignment.getDeadline());
        viewHolder.assignment_location.setText(assignment.getAssignmentLocation());
        viewHolder.assignment_author.setText(assignment.getAuthor());
        viewHolder.assignment_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return assignmentsList.size();
    }


}