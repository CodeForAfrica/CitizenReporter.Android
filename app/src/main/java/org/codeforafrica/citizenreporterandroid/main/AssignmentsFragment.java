package org.codeforafrica.citizenreporterandroid.main;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.codeforafrica.citizenreporterandroid.main.adapter.AssignmentsAdapter;
import org.codeforafrica.citizenreporterandroid.main.models.Assignments;
import org.codeforafrica.citizenreporterandroid.main.models.AssignmentsResponse;
import org.codeforafrica.citizenreporterandroid.main.api.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        //initViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assignments, container, true);
    }

/*
    private void initViews() {

        recyclerView = (RecyclerView) recyclerView.findViewById(R.id.main_content);
        adapter = new AssignmentsAdapter(getContext(), assignmentsList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else{
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    *//*    swipeContainer = (SwipeRefreshLayout) recyclerView.findViewById(R.id.main_content);
        //swipeContainer.setColorSchemeResources(R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                initViews();
                Toast.makeText(getActivity(), "Assignments Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
*//*

    }*/



}