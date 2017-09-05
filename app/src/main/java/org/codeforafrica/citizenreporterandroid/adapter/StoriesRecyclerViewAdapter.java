package org.codeforafrica.citizenreporterandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.storyboard.Storyboard;
import org.codeforafrica.citizenreporterandroid.app.Constants;

/**
 * Created by Ahereza on 7/30/17.
 */

public class StoriesRecyclerViewAdapter
    extends RecyclerView.Adapter<StoriesRecyclerViewAdapter.StoryHolder> {
  private List<Story> storyList;
  private Context context;

  public void setStoryList(List<Story> storyList) {
    this.storyList = storyList;
  }

  public StoriesRecyclerViewAdapter(List<Story> storyList, Context context) {
    this.storyList = storyList;
    this.context = context;
  }

  public class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.story_title) TextView story_title;
    @BindView(R.id.story_date_saved) TextView story_date_saved;
    @BindView(R.id.story_uploaded) ImageView uploaded;

    public StoryHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      ButterKnife.bind(this, itemView);
    }

    @Override public void onClick(View v) {
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

  @Override public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
    return new StoryHolder(view);
  }

  @Override public void onBindViewHolder(StoryHolder holder, int position) {
    Story story = storyList.get(position);
    holder.story_title.setText(story.getTitle());
    holder.story_date_saved.setText(story.getUpdated());
    setUploadedDisplay(story, holder.uploaded);
  }

  @Override public int getItemCount() {
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
}
