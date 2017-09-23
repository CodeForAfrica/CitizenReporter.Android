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

import butterknife.BindView;
import butterknife.ButterKnife;
import com.parse.ParseObject;
import java.util.Date;
import java.util.List;

import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.app.Constants;
import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.storyboard.Storyboard;

import org.codeforafrica.citizenreporterandroid.app.Constants;
import org.codeforafrica.citizenreporterandroid.utils.TimeUtils;


/**
 * Created by Ahereza on 7/30/17.
 */

public class StoriesRecyclerViewAdapter
    extends RecyclerView.Adapter<StoriesRecyclerViewAdapter.StoryHolder> {
  private List<ParseObject> storyList;
  private Context context;

  public void setStoryList(List<ParseObject> storyList) {
    this.storyList = storyList;
  }

  public StoriesRecyclerViewAdapter(List<ParseObject> storyList, Context context) {
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
      ParseObject currentStory = storyList.get(position);
      Toast.makeText(context, currentStory.getString("title"), Toast.LENGTH_SHORT).show();

      // TODO send story id then the story will be retrieved from the database
      Intent openStoryIntent = new Intent(context, Storyboard.class);
      openStoryIntent.setAction(Constants.ACTION_EDIT_VIEW_STORY);
      openStoryIntent.putExtra("STORY_ID", currentStory.getString("localID"));
      context.startActivity(openStoryIntent);
    }
  }

  @Override public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
    return new StoryHolder(view);
  }

  @Override public void onBindViewHolder(StoryHolder holder, int position) {
    ParseObject story = storyList.get(position);
    holder.story_title.setText(story.getString("title"));
    Date updated = story.getUpdatedAt();
    if (updated != null) {
      holder.story_date_saved.setText(TimeUtils.getShortDateFormat(updated));
    } else {
      holder.story_date_saved.setText(TimeUtils.getShortDateFormat(story.getDate("updatedAt")));
    }
    setUploadedDisplay(story, holder.uploaded);
  }

  @Override public int getItemCount() {
    return storyList.size();
  }

  public void notify(List<ParseObject> list) {
    if (storyList != null) {
      storyList.clear();
      storyList.addAll(list);
    } else {
      storyList = list;

    }
  }

  public void setUploadedDisplay(ParseObject story, ImageView uploadedView) {
    if (story.getBoolean("uploaded")) {
      uploadedView.setImageResource(R.drawable.ic_ic_cloud_checked_20);
    } else {
      uploadedView.setImageResource(R.drawable.ic_ic_upload_to_cloud_20);
    }
  }
}
