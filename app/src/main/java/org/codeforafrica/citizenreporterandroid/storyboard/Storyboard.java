package org.codeforafrica.citizenreporterandroid.storyboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.squareup.picasso.Picasso;

import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.data.sources.LocalDataHelper;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;
import org.codeforafrica.citizenreporterandroid.utils.Constants;
import org.codeforafrica.citizenreporterandroid.utils.MediaUtils;
import org.codeforafrica.citizenreporterandroid.utils.StoryBoardUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gun0912.tedbottompicker.TedBottomPicker;

public class Storyboard extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.storyboard_location)
    Button location;

    @BindView(R.id.storybaord_date)
    Button date;

    @BindView(R.id.story_title)
    EditText story_title;

    @BindView(R.id.story_cause)
    EditText story_cause;

    @BindView(R.id.story_who_is_involved)
    EditText story_who;

    @BindView(R.id.attachments_button)
    ImageView attachmentsMenuBtn;

    @BindView(R.id.attachmentsLayout)
    LinearLayout attachmentsLayout;


    private LocalDataHelper dataHelper;
    private Environment environment;
    private PopupMenu popupMenu;
    private Story activeStory;
    private SharedPreferences preferences;
    private List<String> local_media;
    private final Calendar calendar = Calendar.getInstance();

    private final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
            this, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    private LayoutInflater inflater;
    private String audio_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyboard);
        dataHelper = new LocalDataHelper(this);
        ButterKnife.bind(this);
        String action = getIntent().getAction();
        inflater = LayoutInflater.from(Storyboard.this);
        local_media = new ArrayList<>();


        if (action.equals(Constants.ACTION_EDIT_VIEW_STORY)) {
            long storyID = getIntent().getLongExtra("STORY_ID", -1);
            if (storyID > -1) {
                // open a saved story

                Log.d("OPENSTORY", "onCreate: YEAH");
                activeStory = dataHelper.getStory(storyID);
                attachAuthorCred(activeStory);
                story_title.setText(activeStory.getTitle());
                story_cause.setText(activeStory.getCause());
                story_who.setText(activeStory.getWho());

                date.setText(activeStory.getWhen());

                location.setText(activeStory.getWhere());


            } else {
                Toast.makeText(this, "This activeStory can not be found",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        } else {
            int assignmentID = getIntent().getIntExtra("assignmentID", 0);
            activeStory = new Story();
            activeStory.setCause("");
            activeStory.setWhen("");
            activeStory.setWho("");
            activeStory.setTitle("");
            activeStory.setAssignmentId(assignmentID);
            attachAuthorCred(activeStory);
            long savedID = dataHelper.saveStory(activeStory);

            // make story null

            activeStory = null;

            if (savedID >= 0) {
                activeStory = dataHelper.getStory(savedID);
            }


        }

        popupMenu = new PopupMenu(this, attachmentsMenuBtn);
        popupMenu.inflate(R.menu.attachments_menu);


        StoryBoardUtils.requestPermission(this, Manifest.permission.RECORD_AUDIO);
        StoryBoardUtils.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("STORYBOARD", "onResume: ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(this, data);
                    // TODO check out LatLong Object
                    location.setText(place.getName());
                    activeStory.setWhere(place.getName().toString());
                    // update the current story
                    int an = dataHelper.updateStory(activeStory);
                    Log.d("UPDATE", "onActivityResult: " + String.valueOf(an));
                    Log.i("Storybaord", "Place: " + place.getName());
                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(this, data);
                    // TODO: Handle the error.
                    Log.i("Storybaord", status.getStatusMessage());

                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }
                break;

            case Constants.REQUEST_RECORD_AUDIO:
                if (resultCode == RESULT_OK) {
                    Log.i(this.getLocalClassName(), "MimeType: " + StoryBoardUtils.getMimeType(audio_path));
                    Toast.makeText(this, "Audio recorded successfully! " + audio_path, Toast.LENGTH_SHORT).show();
                    addAudioAttachment();
                    local_media.add(audio_path);
                    Log.i(this.getLocalClassName(), "onActivityResult: " + activeStory.getMedia().size());
                    dataHelper.updateStory(activeStory);
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Audio was not recorded", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @OnClick(R.id.storyboard_location)
    public void getLocation() {
        // set a country filter when deploying to specific countries
//        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                .setCountry("KE")
//                .build();
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(this);
            startActivityForResult(intent, Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @OnClick(R.id.storybaord_date)
    public void openCalendar() {
        datePickerDialog.setYearRange(1985, 2028);
        datePickerDialog.show(getFragmentManager(), "datepicker");
    }

    @OnClick(R.id.attachments_button)
    public void openAttachmentsMenu() {
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.select_from_gallery:
                        openImagePicker();
                        return true;

                    case R.id.capture_photo:
                        // todo open scene picker
                        return true;

                    case R.id.capture_video:
                        // todo open scene pocker
                        return true;

                    case R.id.record_sound:
                        startRecording();
                        return true;
                    default:
                        return true;

                }
            }
        });
        popupMenu.show();

    }

    public void attachAuthorCred(Story story) {
        preferences = PreferenceManager.getDefaultSharedPreferences(Storyboard.this);
        String fb_id = preferences.getString("fb_id", "");
        String name = preferences.getString("username", "");
        story.setAuthor(name);
        story.setAuthorId(fb_id);
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        String string_date = String.format("%02d", dayOfMonth) + " " + month + " " + year;
        // TODO attach date to post
        activeStory.setTitle("New Title");
        activeStory.setWhen(string_date);
        date.setText(string_date);

        // update the current story
        int as = dataHelper.updateStory(activeStory);
        Log.d("UPDATE", "onDateSet: " + String.valueOf(as));
    }

    public void openImagePicker(){
        final Context context = this;
        TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(this)
                .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(ArrayList<Uri> uriList) {
                        for (Uri uri : uriList) {
                            Log.d("IMAGE SELECTOR", "onImagesSelected: "
                                    + MediaUtils.getPathFromUri(context, uri));
                            addImageAttachment(uri);

                            local_media.add(MediaUtils.getPathFromUri(context, uri));
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

    public void addImageAttachment(Uri uri) {
        View view = inflater.inflate(R.layout.item_image, null);
        TextView filename = (TextView) view.findViewById(R.id.image_filename_tv);
        TextView filesize = (TextView) view.findViewById(R.id.image_filesize_tv);
        ImageView image = (ImageView) view.findViewById(R.id.attached_image);

        filename.setText(uri.getLastPathSegment());
        filesize.setText(getFileSize(uri));
        Picasso
                .with(Storyboard.this)
                .load(uri)
                .into(image);
        attachmentsLayout.addView(view);
    }

    public void addAudioAttachment() {
        View view = inflater.inflate(R.layout.item_audio, null);
        TextView filename = (TextView) view.findViewById(R.id.audio_filename_tv);
        TextView filesize = (TextView) view.findViewById(R.id.audio_filesize_tv);

        filename.setText("recording.mp3");
        filesize.setText("1.5MB");

        attachmentsLayout.addView(view);

    }

    public void addVideoAttachment() {
        View view = inflater.inflate(R.layout.item_video, null);
        TextView filename = (TextView) view.findViewById(R.id.video_filename_tv);
        TextView filesize = (TextView) view.findViewById(R.id.video_filesize_tv);


        filename.setText("recording.mp4");
        filesize.setText("1.5MB");

        attachmentsLayout.addView(view);

    }

    public String getFileSize(Uri uri) {
        File f = new File(uri.getPath());
        double size = f.length() / (1024*1024);
        if (size > 1.0) {
            return String.valueOf(Math.round(size*100*1024)/100D) + "KB";
        } else {
            return String.valueOf(Math.round(size*100)/100D) + "KB";
        }
    }

    private void startRecording() {
        // ask for permissions
        audio_path = StoryBoardUtils.recordAudio(Storyboard.this, environment);
    }

    public void savePost(Story story) {
        // first lets add the media
        story.setMedia(local_media);
    }




}
