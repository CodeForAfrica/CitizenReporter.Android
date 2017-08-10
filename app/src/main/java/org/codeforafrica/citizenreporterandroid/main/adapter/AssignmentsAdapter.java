package org.codeforafrica.citizenreporterandroid.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    private Context context;
    private List<Assignments> assignmentsList;


    public AssignmentsAdapter(Context mContext, List<Assignments> assignmentsList) {
        this.context = context;
        this.assignmentsList = assignmentsList;

    }

    @Override
    public AssignmentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_assignments, parent, false);
        AssignmentsViewHolder viewHolder = new AssignmentsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AssignmentsViewHolder viewHolder, int position) {

        Assignments assignment = assignmentsList.get(position);

        viewHolder.assignment_title.setText(assignment.getTitle());

        viewHolder.assignment_deadline.setText(assignment.getDeadline());

    }

    @Override
    public int getItemCount() {
        return assignmentsList.size();
    }

    public class AssignmentsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.assignment_title)
        TextView assignment_title;
        @BindView(R.id.assignment_deadline)
        TextView assignment_deadline;


        public AssignmentsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }


    }
}