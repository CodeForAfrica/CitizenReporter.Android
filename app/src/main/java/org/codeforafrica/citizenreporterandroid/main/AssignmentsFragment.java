package org.codeforafrica.citizenreporterandroid.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


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
    private List<Assignments> assignmentsList;
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

    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assignments, container, true);
    }
*/

}



