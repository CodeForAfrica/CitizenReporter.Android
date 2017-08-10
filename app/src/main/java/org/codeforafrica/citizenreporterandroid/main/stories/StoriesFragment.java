package org.codeforafrica.citizenreporterandroid.main.stories;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.data.sources.LocalDataHelper;
import org.codeforafrica.citizenreporterandroid.utils.APIClient;
import org.codeforafrica.citizenreporterandroid.utils.APIInterface;
import org.codeforafrica.citizenreporterandroid.utils.NetworkHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StoriesFragment extends Fragment {
    @BindView(R.id.stories_recyclerview)
    RecyclerView storiesRecyclerView;
    private List<Story> stories;
    private StoriesRecyclerViewAdapter adapter;
    private LocalDataHelper dataHelper;
    private SharedPreferences preferences;
    private APIInterface apiClient;

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
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        storiesRecyclerView.setHasFixedSize(true);
        storiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        dataHelper = new LocalDataHelper(getActivity());

        stories = dataHelper.getAllStories();

        adapter = new StoriesRecyclerViewAdapter(stories, getContext());

        storiesRecyclerView.setAdapter(adapter);

        if (dataHelper.getStoriesCount() == 0) {
            apiClient = APIClient.getApiClient();
            String fb_id = preferences.getString("fb_id", "");
            if (fb_id != "") {
                Log.d("API", "making network call get stories");
                NetworkHelper.getUserStories(getActivity(), apiClient, fb_id, adapter);
            }
        }


//        adapter.setStoryList(dataHelper.getAllStories());
//        adapter.notifyDataSetChanged();
    }

}
