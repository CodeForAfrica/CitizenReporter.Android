package org.codeforafrica.citizenreporterandroid.storyboard;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;
import com.android.datetimepicker.date.DatePickerDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import gun0912.tedbottompicker.TedBottomPicker;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import org.codeforafrica.citizenreporterandroid.BuildConfig;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.app.Constants;
import org.codeforafrica.citizenreporterandroid.camera.CameraActivity;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;
import org.codeforafrica.citizenreporterandroid.ui.stories.AudioProgressUpdateThread;
import org.codeforafrica.citizenreporterandroid.ui.video.VideoViewActivity;
import org.codeforafrica.citizenreporterandroid.utils.MediaUtils;
import org.codeforafrica.citizenreporterandroid.utils.NetworkUtils;
import org.codeforafrica.citizenreporterandroid.utils.StoryBoardUtils;
import org.json.JSONArray;
import org.json.JSONException;

import static org.codeforafrica.citizenreporterandroid.utils.TimeUtils.getShortDateFormat;

public class Storyboard extends AppCompatActivity
    implements StoryboardContract.View, DatePickerDialog.OnDateSetListener {

  private static final String TAG = Storyboard.class.getSimpleName();
  StoryboardContract.Presenter presenter;
  ParseObject activeStory;
  LayoutInflater inflater;
  JSONArray media;
  private String audio_path;
  private final Calendar calendar = Calendar.getInstance();
  private final DatePickerDialog datePickerDialog =
      DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
          calendar.get(Calendar.DAY_OF_MONTH));

  private String selectedImage;

  @BindView(R.id.attachmentsLayout) LinearLayout attachmentsLayout;

  @BindView(R.id.storyboard_location) Button location_btn;

  @BindView(R.id.storybaord_date) Button date;

  @BindView(R.id.story_title) EditText story_title;

  @BindView(R.id.story_who_is_involved) EditText story_who;

  @BindView(R.id.summary) EditText story_summary;


  @BindView(R.id.storybard_bottom_navigation) BottomNavigationView bottomNavigationView;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_storyboard);

    ButterKnife.bind(Storyboard.this);
    disableShiftMode(bottomNavigationView);
    presenter = new StoryboardPresenter(this);

    inflater = LayoutInflater.from(Storyboard.this);
    String action = getIntent().getAction();
    if (action.equals(Constants.ACTION_EDIT_VIEW_STORY)) {
      String storyID = getIntent().getStringExtra("STORY_ID");
      Log.i(TAG, "onCreate: edit story");
      presenter.openSavedStory(storyID);
    } else {
      String assignmentID = getIntent().getStringExtra("assignmentID");
      presenter.createNewStory(assignmentID);
    }

    StoryBoardUtils.requestPermission(this, Manifest.permission.RECORD_AUDIO);
    StoryBoardUtils.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    bottomNavigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
              case R.id.record_audio:
                presenter.startRecorder();
                break;
              case R.id.capture_image:
                presenter.startCameraCapture();
                break;
              case R.id.open_gallery:
                presenter.getPicturesFromGallery();
                break;
            }
            return true;
          }
        });
  }

  @Override protected void onStart() {
    super.onStart();
  }

  @Override protected void onStop() {
    super.onStop();
    updateStoryObject(activeStory);
    Log.d(TAG, "Value of activeStory.title.isEmpty " + (activeStory.get("title").toString().isEmpty()));
    if(activeStory.get("title").toString().isEmpty()){
      System.out.println(activeStory.getString("title"));
      activeStory.unpinInBackground();
    } else{
      presenter.saveStory(activeStory);

    }


  }


  @Override protected void onDestroy() {
    super.onDestroy();

  }

  @Override protected void onPause() {
    super.onPause();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.upload_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.upload:
        Log.d(TAG, "onOptionsItemSelected: Clicked uploaded");
        if (NetworkUtils.isNetworkAvailable(this)) {
          presenter.uploadStory(activeStory);
        } else {
          Toast.makeText(this, R.string.no_active_internet, Toast.LENGTH_SHORT).show();
        }

        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
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

      case Constants.REQUEST_RECORD_AUDIO:
        if (resultCode == RESULT_OK) {
          if (audio_path != null) {
            File f = new File(audio_path);

            try {

              byte[] audio_data = FileUtils.readFileToByteArray(f);
              final ParseFile file = new ParseFile(f.getName(), audio_data);

              presenter.attachAudio(file.getName(), f.getAbsolutePath());
              file.saveInBackground(new SaveCallback() {
                @Override public void done(ParseException e) {
                  if (e == null) {
                    Log.i(TAG, "done: uploading file");
                    Log.i(TAG, "onActivityResult URL: " + file.getUrl());
                  } else {
                    Log.d(TAG, "Error: " + e.getLocalizedMessage());
                  }
                }
              });
              // upload file
              Log.i(TAG, "onActivityResult: " + file.getName());
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        } else if (resultCode == RESULT_CANCELED) {
          Toast.makeText(this, "Audio was not recorded", Toast.LENGTH_SHORT).show();
        }
        break;

      case Constants.CUSTOM_CAMERA_REQUEST_CODE:
        if (resultCode == RESULT_OK) {
          if (data.getStringExtra("imagePath") != null) {
            File imageFile = new File(data.getStringExtra("imagePath"));
            try {
              byte[] imageData = FileUtils.readFileToByteArray(imageFile);
              final ParseFile imageParseFile = new ParseFile(imageFile.getName(), imageData);
              imageParseFile.saveInBackground(new SaveCallback() {
                @Override public void done(ParseException e) {
                  if (e == null) {
                    presenter.attachImage(imageParseFile.getName(), imageParseFile.getUrl());
                    media.put(imageParseFile);
                    Log.i(TAG, "onActivityResult URL: image " + imageParseFile.getUrl());
                  }
                }
              });
            } catch (IOException e) {
              e.printStackTrace();
            }

          } else if (data.getStringExtra("videoPath") != null) {
            String path = data.getStringExtra("videoPath");
            File videoFile = new File(data.getStringExtra("videoPath"));
            try {
              byte[] video_data = FileUtils.readFileToByteArray(videoFile);
              final ParseFile file = new ParseFile(videoFile.getName(), video_data);
              presenter.attachVideo(file.getName(), data.getStringExtra("videoPath"));
              file.saveInBackground(new SaveCallback() {
                @Override public void done(ParseException e) {
                  if (e == null) {
                    Log.i(TAG, "done: uploading video file");
                    media.put(file);
                    Log.i(TAG, "onActivityResult video URL: " + file.getUrl());
                  } else {
                    Log.d(TAG, "Error: video " + e.getLocalizedMessage());
                  }
                }
              });
              // upload file
              Log.i(TAG, "onActivityResult: video " + file.getName());
            } catch (IOException e) {
              e.printStackTrace();
            }
          } else {
            Log.d(TAG, "onActivityResult: nothing came from the camera");
          }
        }
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
    boolean uploaded = story.getBoolean("uploaded");
    Date whenItOccurred = story.getDate("when");
    String loc = story.getString("location") != null ? story.getString("location") : "";
    activeStory.put("source_app", getString(R.string.app_name));
    media = story.getJSONArray("media") != null ? story.getJSONArray("media") : new JSONArray();
    Log.d(TAG, "loadSavedReport: media" + media.length());

    Log.d(TAG,
        "loadSavedReport: " + title + " " + summary + " " + whoIsInvolved + " " + location_btn);

    // set text to appropriate views

    if (uploaded) {
      story_title.setEnabled(false);
      story_title.setTextColor(Color.BLACK);
      story_who.setEnabled(false);
      story_who.setTextColor(Color.BLACK);
      story_summary.setEnabled(false);
      story_summary.setTextColor(Color.BLACK);
      date.setEnabled(false);
      date.setTextColor(Color.BLACK);
      location_btn.setEnabled(false);
      location_btn.setTextColor(Color.BLACK);
    }

    story_title.setText(title);
    story_summary.setText(summary);
    story_who.setText(whoIsInvolved);
    date.setText(whenItOccurred == null ? "Date" : getShortDateFormat(whenItOccurred));
    location_btn.setText(loc);

    try {
      presenter.loadAllAttachments(media);
    } catch (JSONException e) {
      Log.e(TAG, "loadSavedReport: JSON Array media", e.fillInStackTrace());
    }
  }

  @Override public void loadNewReport(String assignmentID) {
    ParseUser user = ParseUser.getCurrentUser();
    activeStory = new ParseObject("Story");
    activeStory.put("createdAt", new Date());
    String uniqueID = UUID.randomUUID().toString();
    // put a dummy local that will be removed right before the story is uploaded
    activeStory.put("localID", uniqueID);
    activeStory.put("uploaded", false);
    activeStory.put("assignment", assignmentID);
    activeStory.put("author", user.getObjectId());
    activeStory.put("source_app", getString(R.string.app_name));

    Log.i(TAG, "loadNewReport: Assignment " + activeStory.getString("assignment"));
    Log.i(TAG, "loadNewReport: uploaded " + activeStory.getBoolean("uploaded"));
    Log.i(TAG, "loadNewReport: author " + activeStory.getString("author"));

    try {
      activeStory.pin();
      Log.i(TAG, "loadNewReport: ID " + activeStory.getString("localID"));
      Log.d(TAG, "after loadNewReport: assignment" + assignmentID);
      Log.i(TAG, "after loadNewReport: Assignment " + activeStory.getString("assignment"));
      Log.i(TAG, "after loadNewReport: uploaded " + activeStory.getBoolean("uploaded"));

      // if creating a new story, initialize an empty media json array
      media = new JSONArray();
    } catch (ParseException e) {
      e.printStackTrace();
      Log.e(TAG, "loadNewReport: Error pinning", e.fillInStackTrace());
      Toast.makeText(this, "Error occured when creating the Report", Toast.LENGTH_LONG).show();
      Intent intent = new Intent(Storyboard.this, MainActivity.class);
      startActivity(intent);
      finish();
    }
  }

  @Override public void showStoryNotFoundError(String message) {

  }

  @Override public void displayAttachments(List<ParseFile> files) {
    // get list of files pass them to the attachments adapter

  }

  @Override public void showImageAttachment(String name, String url) {
    View view = inflater.inflate(R.layout.item_image, null);
    TextView filename = (TextView) view.findViewById(R.id.image_filename_tv);
    final String imageUrl = url;

    // Hook up clicks on the thumbnail views.
    final ImageView image = (ImageView) view.findViewById(R.id.attached_image);
    image.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        selectedImage = imageUrl;
        showDialog();
      }
    });

    filename.setText(name);
    Glide.with(Storyboard.this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
        .into(image);
    attachmentsLayout.addView(view);
  }

  @Override public void showVideoAttachment(String name, String uri) {
    View view = inflater.inflate(R.layout.item_video, null);
    TextView fileName = (TextView) view.findViewById(R.id.video_filename_tv);
    TextView fileSize = (TextView) view.findViewById(R.id.audio_filesize_tv);
    ImageView play_video_icon = (ImageView) view.findViewById(R.id.play_video_icon);
    ImageView videoThumbnail = (ImageView) view.findViewById(R.id.video_thumbnail);
    final String path = uri;
    final String videoFilename = name;
    Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
    final int videoHeight = thumb.getHeight();
    final int videoWidth = thumb.getWidth();
    videoThumbnail.setImageBitmap(thumb);
    play_video_icon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(Storyboard.this, VideoViewActivity.class);
        intent.putExtra("videoUrl", path);
        intent.putExtra("videoHeight", videoHeight);
        intent.putExtra("videoWidth", videoWidth);
        intent.putExtra("videoFilename", videoFilename);
        startActivity(intent);
      }
    });

    File file = new File(uri);
    long size  = file.length();
    fileName.setText(name);
    fileSize.setText(size/1024 + " KB");

    attachmentsLayout.addView(view);
  }

  @Override public void showAudioAttachment(String name, String uri) {
    Log.i(TAG, "showAudioAttachment: ");
    View view = inflater.inflate(R.layout.item_audio, null);
    TextView filename = (TextView) view.findViewById(R.id.audio_filename_tv);
    TextView fileSize = (TextView) view.findViewById(R.id.audio_filesize_tv);
    final TextView audioTime = (TextView) view.findViewById(R.id.audio_time);

    File file = new File(uri);
    long size  = (file.length())/1024;
    String size_label = size + " KB";


    if (size>1000){
      double sizeMb = (size * (.001));
      DecimalFormat df = new DecimalFormat("#.##");
      df.setRoundingMode(RoundingMode.CEILING);
      size_label = df.format(sizeMb) + " MB";
    }
    filename.setText(name);
    fileSize.setText(size_label);

    final MediaPlayer mediaPlayer = MediaPlayer.create(this, Uri.parse(audio_path));
    final SeekBar seekbar = (SeekBar)view.findViewById(R.id.audio_progress_bar);
    int audioDuration = mediaPlayer.getDuration();
    seekbar.setMax(audioDuration);
    final Handler myHandler = new Handler();
    final AudioProgressUpdateThread runnable = new AudioProgressUpdateThread(mediaPlayer, myHandler, audioTime, seekbar);
    myHandler.postDelayed(runnable, 100);

    final ImageView playPause = (ImageView)view.findViewById(R.id.audio_play_pause);
    playPause.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(mediaPlayer.isPlaying()){
          mediaPlayer.pause();
          playPause.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                  R.drawable.ic_icons8_play_filled_100, null));
        }
        else {
          mediaPlayer.start();
          playPause.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                  R.drawable.ic_icons8_pause_104, null));
        }
      }
    });

    seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser){

        }

      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        myHandler.removeCallbacks(runnable);

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        myHandler.removeCallbacks(runnable);
        int seekBarPosition = seekBar.getProgress();

        // forward or backward to certain seconds
        mediaPlayer.seekTo(seekBarPosition);
        myHandler.postDelayed(runnable, 100);

      }
    });

    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
      @Override
      public void onCompletion(MediaPlayer mediaPlayer) {
        playPause.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_icons8_play_filled_100, null));
      }
    });
    attachmentsLayout.addView(view);
  }

  @Override public void showUnknownAttachment(String name, String uri) {
    Log.i(TAG, "showUnknownAttachment: ");
    View view = inflater.inflate(R.layout.item_unkown, null);
    TextView filename = (TextView) view.findViewById(R.id.unknown_filename_tv);
    TextView fileSize = (TextView) view.findViewById(R.id.unknown_filesize_tv);

    filename.setText(name);
    File file = new File(uri);
    long size  = file.length();
    String size_label = size/1024 + " KB";
    if (size>1000){
      size_label = (size/1024 * .001) + " MB";
    }
    filename.setText(name);
    fileSize.setText(size_label);

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
    System.out.println(story_title.getText().toString());
    {
      activeStory.put("title", story_title.getText().toString());
      activeStory.put("summary", story_summary.getText().toString());
      activeStory.put("who", story_who.getText().toString());
      activeStory.put("media", media);
      activeStory.put("updatedAt", new Date());
    }

  }

  @Override public void showDatePickerDialog() {
    datePickerDialog.setYearRange(1985, 2028);
    datePickerDialog.show(getFragmentManager(), "datepicker");
  }

  @Override public void showRecorder() {
    String audio_filename =
        Constants.RECORDING_PREFIX + String.valueOf(System.currentTimeMillis()) + ".wav";
    audio_path = Environment.getExternalStorageDirectory().getPath() + audio_filename;

    AndroidAudioRecorder.with(Storyboard.this)
        // Required
        .setFilePath(audio_path)
        .setColor(ContextCompat.getColor(Storyboard.this, R.color.cfAfrica_red))
        .setRequestCode(Constants.REQUEST_RECORD_AUDIO)

        // Optional
        .setSource(AudioSource.MIC)
        .setChannel(AudioChannel.STEREO)
        .setSampleRate(AudioSampleRate.HZ_48000)
        .setAutoStart(false)
        .setKeepDisplayOn(true)

        // Start recording
        .record();
  }

  @Override public void readyStoryForUpload() {
    activeStory.put("media", media);
    presenter.saveStory(activeStory);
  }

  @Override public void showImagePicker() {
    Log.i(TAG, "showImagePicker: done");
    TedBottomPicker bottomSheetDialogFragment =
        new TedBottomPicker.Builder(this).setOnMultiImageSelectedListener(
            new TedBottomPicker.OnMultiImageSelectedListener() {
              @Override public void onImagesSelected(ArrayList<Uri> uriList) {
                for (Uri uri : uriList) {
                  Log.d("IMAGE SELECTOR",
                      "onImagesSelected: " + MediaUtils.getPathFromUri(Storyboard.this, uri));
                  File imageFile = new File(uri.getPath());
                  try {
                    byte[] imageData = FileUtils.readFileToByteArray(imageFile);
                    final ParseFile imageParseFile = new ParseFile(imageFile.getName(), imageData);
                    imageParseFile.saveInBackground(new SaveCallback() {
                      @Override public void done(ParseException e) {
                        if (e == null) {
                          presenter.attachImage(imageParseFile.getName(), imageParseFile.getUrl());
                          media.put(imageParseFile);
                          Log.i(TAG, "onActivityResult URL: " + imageParseFile.getUrl());
                        }
                      }
                    });
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                }
              }
            })
            .setPeekHeight(500)
            .showTitle(false)
            .showCameraTile(false)
            .setCompleteButtonText("Done")
            .setEmptySelectionText("No Select")
            .create();

    bottomSheetDialogFragment.show(getSupportFragmentManager());
  }

  @Override public void finishUploading() {
    onBackPressed();
    //Intent intent = new Intent(Storyboard.this, VideoViewActivity.class);
    //intent.putExtra("Source", "uploaded");
    //startActivity(intent);
    //finish();
  }

  @Override public void sendCameraIntent() {
    Intent intent = new Intent(Storyboard.this, CameraActivity.class);
    startActivityForResult(intent, Constants.CUSTOM_CAMERA_REQUEST_CODE);

  }

  @Override public void showUploadError() {
    Toast.makeText(this, "Upload Failed! Try again Later", Toast.LENGTH_SHORT).show();
  }

  @Override public void showUploadSuccess() {
    Toast.makeText(this, "Upload Successful", Toast.LENGTH_SHORT).show();
  }

  @OnClick(R.id.storyboard_location) public void getLocation() {
    presenter.getLocation();
  }

  @OnClick(R.id.storybaord_date) public void setWhen() {
    presenter.getWhenItOccurred();
  }

  @Override
  public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
    Calendar newCalendar = Calendar.getInstance();
    newCalendar.set(year, monthOfYear, dayOfMonth);
    activeStory.put("when", newCalendar.getTime());
    date.setText(getShortDateFormat(newCalendar.getTime()));
  }

  public static void disableShiftMode(BottomNavigationView view) {
    BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
    try {
      Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
      shiftingMode.setAccessible(true);
      shiftingMode.setBoolean(menuView, false);
      shiftingMode.setAccessible(false);
      for (int i = 0; i < menuView.getChildCount(); i++) {
        BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
        //noinspection RestrictedApi
        item.setShiftingMode(false);
        // set once again checked value, so view will be updated
        //noinspection RestrictedApi
        item.setChecked(item.getItemData().isChecked());
      }
    } catch (NoSuchFieldException e) {
      Log.e("BNVHelper", "Unable to get shift mode field", e);
    } catch (IllegalAccessException e) {
      Log.e("BNVHelper", "Unable to change value of shift mode", e);
    }
  }

  public void showDialog(){
    final Dialog dialog = new Dialog(Storyboard.this);
    //create dialog without title
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    //set the custom dialog's layout to the dialog
    dialog.setContentView(R.layout.image_preview_dialog);
    //display the dialog box
    ImageView expandedImageView = (ImageView)dialog.findViewById(R.id.expanded_image);
    Glide.with(Storyboard.this)
            .load(selectedImage)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .into(expandedImageView);
    dialog.show();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedState) {
    super.onRestoreInstanceState(savedState);
  }

}


