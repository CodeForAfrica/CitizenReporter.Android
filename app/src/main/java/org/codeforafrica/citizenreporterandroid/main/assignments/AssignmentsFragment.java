package org.codeforafrica.citizenreporterandroid.main.assignments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.codeforafrica.citizenreporterandroid.main.adapter.AssignmentsAdapter;
import org.codeforafrica.citizenreporterandroid.data.models.Assignments;

import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.data.sources.LocalDataHelper;
import org.codeforafrica.citizenreporterandroid.utils.APIInterface;
import org.codeforafrica.citizenreporterandroid.utils.APIClient;
import org.codeforafrica.citizenreporterandroid.utils.NetworkHelper;

import java.util.List;

import butterknife.ButterKnife;


public class AssignmentsFragment extends Fragment {

    //@BindView(R.id.assignment_recycler)
    RecyclerView recyclerView;

    private List<Assignments> assignmentsList;
    private LocalDataHelper dataHelper;
    private AssignmentsAdapter adapter;
    private SharedPreferences preferences;
    private APIInterface apiClient;

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


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignments, container, false);
        ButterKnife.bind(this, view);

        // Inflate the layout for this fragment

        return view;

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        dataHelper = new LocalDataHelper(getActivity());
        if (dataHelper.getAssignmentsCount() == 0) {
            apiClient = APIClient.getApiClient();
            NetworkHelper.getAssignments(getActivity(), apiClient, adapter);
        }
        //System.out.println(dataHelper.getAssignmentsCount());

        assignmentsList = dataHelper.getAssignments();
        System.out.println(assignmentsList.size());
        adapter = new AssignmentsAdapter(assignmentsList, getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.assignment_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


//        recyclerView.setHasFixedSize(true);


    }

}



