package org.codeforafrica.citizenreporterandroid.main.stories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.data.models.Story;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StoriesFragment extends Fragment {
    @BindView(R.id.stories_recyclerview)
    RecyclerView storiesRecyclerView;
    private List<Story> stories;
    private StoriesRecyclerViewAdapter adapter;

    public StoriesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StoriesFragment newInstance() {
        StoriesFragment fragment = new StoriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stories, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storiesRecyclerView.setHasFixedSize(true);
        storiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        stories = new ArrayList<>();

        Story story = new Story();
        story.setTitle("Chaos in Naivasha");
        story.setWhen(new Date());

        stories.add(story);

        Story story1 = new Story();
        story1.setTitle("Cholera is Kibera");
        story1.setWhen(new Date());

        stories.add(story1);

        adapter = new StoriesRecyclerViewAdapter(stories, getContext());

        storiesRecyclerView.setAdapter(adapter);
    }


}
