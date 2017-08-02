package org.codeforafrica.citizenreporterandroid.main.stories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.data.models.Story;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahereza on 7/30/17.
 */

public class StoriesRecyclerViewAdapter extends
        RecyclerView.Adapter<StoriesRecyclerViewAdapter.StoryHolder> {
    private List<Story> storyList;
    private Context context;

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

    public StoriesRecyclerViewAdapter(List<Story> storyList, Context context) {
        this.storyList = storyList;
        this.context = context;
    }

    public class StoryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.story_title) TextView story_title;
        @BindView(R.id.story_date_saved) TextView story_date_saved;

        public StoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_story, parent, false);
        return new StoryHolder(view);
    }

    @Override
    public void onBindViewHolder(StoryHolder holder, int position) {
        Story story = storyList.get(position);
        holder.story_title.setText(story.getTitle());
        holder.story_date_saved.setText(story.getUpdated());

    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }
}
