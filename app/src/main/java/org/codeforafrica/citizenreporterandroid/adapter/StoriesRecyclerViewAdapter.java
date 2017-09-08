package org.codeforafrica.citizenreporterandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.app.Constants;
import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.storyboard.Storyboard;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahereza on 7/30/17.
 */

public class StoriesRecyclerViewAdapter
        extends RecyclerView.Adapter<StoriesRecyclerViewAdapter.StoryHolder> {
    private List<Story> storyList;
    private Context context;
    private List<Story> storiesPendingRemoval;
    private static final int PENDING_REMOVAL_TIMEOUT = 3000;
    boolean undoOn;

    private Handler handler = new Handler();
    HashMap<Story, Runnable> pendingRunnables = new HashMap<>();

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

    public StoriesRecyclerViewAdapter(List<Story> storyList, Context context) {
        this.storyList = storyList;
        this.context = context;
        this.storiesPendingRemoval = storyList;
    }

    public class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.story_title)
        TextView story_title;
        @BindView(R.id.story_date_saved)
        TextView story_date_saved;
        @BindView(R.id.story_uploaded)
        ImageView uploaded;

        @BindView(R.id.undo_button)
        Button undoButton;

        public StoryHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            // get the position of thr row clicked
            int position = getAdapterPosition();
            Story currentStory = storyList.get(position);
            Toast.makeText(context, currentStory.getTitle(), Toast.LENGTH_SHORT).show();

            // TODO send story id then the story will be retrieved from the database
            Intent openStoryIntent = new Intent(context, Storyboard.class);
            openStoryIntent.setAction(Constants.ACTION_EDIT_VIEW_STORY);
            openStoryIntent.putExtra("STORY_ID", currentStory.getLocal_id());
            context.startActivity(openStoryIntent);
        }
    }

    @Override
    public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
        return new StoryHolder(view);
    }

    @Override
    public void onBindViewHolder(StoryHolder holder, int position) {
        final Story story = storyList.get(position);

        if (storiesPendingRemoval.contains(story)) {
            holder.itemView.setBackgroundColor(Color.RED);
            holder.story_title.setVisibility(View.GONE);
            holder.story_date_saved.setVisibility(View.GONE);
            holder.undoButton.setVisibility(View.VISIBLE);
            holder.undoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Runnable pendingRemovalRunnable = pendingRunnables.get(story);
                    pendingRunnables.remove(story);
                    if (pendingRemovalRunnable != null) handler.removeCallbacks(pendingRemovalRunnable);
                    storiesPendingRemoval.remove(story);
                    notifyItemChanged(storyList.indexOf(story));
                }
            });
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.story_title.setVisibility(View.VISIBLE);
            holder.story_date_saved.setVisibility(View.VISIBLE);
            holder.undoButton.setVisibility(View.GONE);
            holder.undoButton.setOnClickListener(null);
            holder.story_title.setText(story.getTitle());
            holder.story_date_saved.setText(story.getUpdated());
            setUploadedDisplay(story, holder.uploaded);
        }

    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public void notify(List<Story> list) {
        if (storyList != null) {
            storyList.clear();
            storyList.addAll(list);
        } else {
            storyList = list;
        }
        notifyDataSetChanged();
    }

    public void setUploadedDisplay(Story story, View uploadedView) {
        if (story.isUploaded()) {
            uploadedView.setVisibility(View.VISIBLE);
        }
    }

    public void pendingRemoval(int position) {
        final Story story = storyList.get(position);
        if (!storiesPendingRemoval.contains(story)) {
            storiesPendingRemoval.add(story);
            notifyItemChanged(position);
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    storyList.remove(storyList.indexOf(story));
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(story, pendingRemovalRunnable);
        }
    }

    public void remove(int position) {
        final Story story = storyList.get(position);
        if (storiesPendingRemoval.contains(story)) {
            storiesPendingRemoval.remove(story);
        }
        if (storyList.contains(story)) {
            storyList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean isPendingRemoval(int position) {
        final Story story = storyList.get(position);
        return storiesPendingRemoval.contains(story);
    }

    public void setUndoOn(boolean undoOn) {
        this.undoOn = undoOn;
    }

    public boolean isUndoOn() {
        return undoOn;
    }
}
