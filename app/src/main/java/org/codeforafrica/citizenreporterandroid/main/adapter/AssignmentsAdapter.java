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

/**
 * Created by Mugiwara_Munyi on 30/07/2017.
 */

public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.AssignmentsViewHolder>{
        private Context mContext;
        private List<Assignments> assignmentsList;

        public AssignmentsAdapter(Context mContext, List<Assignments> assignmentsList){
            this.mContext = mContext;
            this.assignmentsList = assignmentsList;
        }

        @Override
        public AssignmentsAdapter.AssignmentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            Context context = parent.getContext();

            LayoutInflater inflater = LayoutInflater.from(context);

            View mRootView = inflater.inflate(R.layout.fragment_assignments, parent, false);
            
            return new AssignmentsViewHolder(mRootView);

        }

        @Override
        public void onBindViewHolder(AssignmentsViewHolder viewHolder, int position){
            Assignments assignment = assignmentsList.get(position);

            viewHolder.assignmentTitleTextView.setText(assignment.getTitle());

            viewHolder.assignmentDescriptionTextView.setText(assignment.getDescription());

            /*Picasso.with(getContext()).load((Uri) assignment.getFeaturedImage())
                    .fit().into(viewHolder.assignmentImage);*/

        }

        @Override
        public int getItemCount(){
            return assignmentsList.size();
        }



    public class AssignmentsViewHolder extends RecyclerView.ViewHolder {
        private ImageView assignmentImage;
        private TextView assignmentTitleTextView;
        private TextView assignmentDescriptionTextView;


        public AssignmentsViewHolder(View view) {
            super(view);
            assignmentTitleTextView = (TextView) view.findViewById(R.id.title);
            assignmentDescriptionTextView = (TextView) view.findViewById(R.id.assignment_description);
            assignmentImage = (ImageView) view.findViewById(R.id.assignment_thumbnail);

        }


    }
}
