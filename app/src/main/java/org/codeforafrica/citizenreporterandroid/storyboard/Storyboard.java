package org.codeforafrica.citizenreporterandroid.storyboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;
import java.util.Date;
import java.util.List;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.app.Constants;
import org.json.JSONArray;

public class Storyboard extends AppCompatActivity implements StoryboardContract.View {
  private static final String TAG = Storyboard.class.getSimpleName();
  StoryboardContract.Presenter presenter;
  ParseObject activeStory;
  LayoutInflater inflater;

  @BindView(R.id.attachmentsLayout) LinearLayout attachmentsLayout;

  @BindView(R.id.storyboard_location) Button location_btn;

  @BindView(R.id.storybaord_date) Button date;

  @BindView(R.id.story_title) EditText story_title;

  @BindView(R.id.story_cause) EditText story_summary;

  @BindView(R.id.story_who_is_involved) EditText story_who;

  @BindView(R.id.attachments_button) ImageView attachmentsMenuBtn;

  @BindView(R.id.summary) EditText summary;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_storyboard);
    ButterKnife.bind(Storyboard.this);
    presenter = new StoryboardPresenter(this);
    String action = getIntent().getAction();
    if (action.equals(Constants.ACTION_EDIT_VIEW_STORY)) {
      String storyID = getIntent().getStringExtra("STORY_ID");
      Log.i(TAG, "onCreate: edit story");
      presenter.openSavedStory(storyID);
    } else {
      String assignmentID = getIntent().getStringExtra("assignmentID");
      presenter.createNewStory(assignmentID);
    }
  }

  @Override protected void onStart() {
    super.onStart();
  }

  @Override protected void onStop() {
    super.onStop();
    presenter.saveStory(activeStory);

  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.saveStory(activeStory);

  }

  @Override protected void onPause() {
    super.onPause();

  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE:
        if (resultCode == RESULT_OK) {
          Place place = PlaceAutocomplete.getPlace(this, data);
          // TODO check out LatLong Object
          location_btn.setText(place.getName());

          // add location to current story
          activeStory.put("location", place.getName());

        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
          Status status = PlaceAutocomplete.getStatus(this, data);
          Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();

        } else if (resultCode == RESULT_CANCELED) {
          // The user canceled the operation.
          Toast.makeText(this, "Get Location operation has been cancelled", Toast.LENGTH_SHORT)
              .show();

        }
        break;
    }
  }

  @Override public void showLoading() {

  }

  @Override public void hideLoading() {

  }

  @Override public void showUploadingProgress() {

  }

  @Override public void loadSavedReport(ParseObject story) {
    activeStory = story;
    String title = story.getString("title") != null ? story.getString("title") : "";
    String summary = story.getString("summary") != null ? story.getString("summary") : "";
    String whoIsInvolved = story.getString("who") != null ? story.getString("who") : "";
    Date whenItOccurred = story.getDate("when");
    String loc = story.getString("location") != null ? story.getString("location") : "";
    JSONArray media =
        story.getJSONArray("media") != null ? story.getJSONArray("media") : new JSONArray();

    Log.d(TAG, "loadSavedReport: " + title + " " + summary + " " + whoIsInvolved + " " + location_btn);

    // set text to appropriate views

    story_title.setText(title);
    story_summary.setText(summary);
    story_who.setText(whoIsInvolved);
    date.setText(whenItOccurred.toString() == null ? "Date" : whenItOccurred.toString());
    location_btn.setText(loc);

    presenter.loadAttachments(media);
  }

  @Override public void loadNewReport(ParseObject story) {
    Log.d(TAG, "loadNewReport: Log new report " + story.getObjectId());
    activeStory = story;
  }

  @Override public void showStoryNotFoundError(String message) {

  }

  @Override public void displayAttachments(List<ParseFile> files) {
    // get list of files pass them to the attachments adapter

  }

  @Override public void attachImage(ParseFile file) {
    View view = inflater.inflate(R.layout.item_image, null);
    TextView filename = (TextView) view.findViewById(R.id.image_filename_tv);
    ImageView image = (ImageView) view.findViewById(R.id.attached_image);

    filename.setText(file.getName());
    Picasso.with(Storyboard.this).load(file.getUrl()).into(image);
    attachmentsLayout.addView(view);
  }

  @Override public void attachVideo(ParseFile file) {
    View view = inflater.inflate(R.layout.item_video, null);
    TextView filename = (TextView) view.findViewById(R.id.video_filename_tv);

    filename.setText(file.getName());

    attachmentsLayout.addView(view);
  }

  @Override public void attachAudio(ParseFile file) {
    View view = inflater.inflate(R.layout.item_audio, null);
    TextView filename = (TextView) view.findViewById(R.id.audio_filename_tv);

    filename.setText(file.getName());

    attachmentsLayout.addView(view);
  }

  @Override public void showLocationSearch() {
    try {
      Intent intent =
          new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
      startActivityForResult(intent, Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE);
    } catch (GooglePlayServicesRepairableException e) {
      // TODO: Handle the error.

    } catch (GooglePlayServicesNotAvailableException e) {
      // TODO: Handle the error.
    }
  }

  @Override public void updateStoryObject(ParseObject activeStory) {
    //story_title.setText(title);
    //story_summary.setText(summary);
    //story_who.setText(whoIsInvolved);
    //date.setText(whenItOccurred.toString() == null ? "Date" : whenItOccurred.toString());
    //location_btn.setText(loc);
    activeStory.put("title", story_title.getText().toString());
    activeStory.put("summary", story_summary.getText().toString());
    activeStory.put("who", story_who.getText().toString());
    // TODO: 9/13/17 media and date

  }

  @OnClick(R.id.storyboard_location) public void getLocation() {
    presenter.getLocation();
  }
}