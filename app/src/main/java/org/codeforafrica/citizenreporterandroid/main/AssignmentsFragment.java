package org.codeforafrica.citizenreporterandroid.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.codeforafrica.citizenreporterandroid.main.adapter.AssignmentsAdapter;
import org.codeforafrica.citizenreporterandroid.main.models.Assignments;

import org.codeforafrica.citizenreporterandroid.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link AssignmentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssignmentsFragment extends Fragment {

    private RecyclerView recyclerView;
    private AssignmentsAdapter adapter;
    private ArrayList<Assignments> assignmentsList;
    private SwipeRefreshLayout swipeContainer;

    public AssignmentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * .
     *
     * @return A new instance of fragment AssignmentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AssignmentsFragment newInstance() {
        AssignmentsFragment fragment = new AssignmentsFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Assignment Fragment", "onCreate: ");
        if (savedInstanceState != null){
            assignmentsList = getArguments().getParcelableArrayList("LIST");
        }

    }


/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignments_recycler, container, true);
        // Inflate the layout for this fragment
        RecyclerView recyclerView =  view.findViewById(R.id.assignment_recycler);
        adapter = new AssignmentsAdapter(getContext(), assignmentsList);


        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }*/

    public static AssignmentsFragment newInstance(ArrayList assignmentsList) {

        AssignmentsFragment assignmentsFragment = new AssignmentsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LIST", assignmentsList);
        return assignmentsFragment;
    }

}



