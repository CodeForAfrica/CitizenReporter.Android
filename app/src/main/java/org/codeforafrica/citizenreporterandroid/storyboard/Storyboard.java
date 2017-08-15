package org.codeforafrica.citizenreporterandroid.storyboard;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.data.sources.LocalDataHelper;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;
import org.codeforafrica.citizenreporterandroid.utils.Constants;
import org.codeforafrica.citizenreporterandroid.utils.StoryBoardUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Storyboard extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.slider)
    SliderLayout storiesSlider;
    @BindView(R.id.summary)
    TextView summary;
    @BindView(R.id.cause)
    TextView whatCausedthis;
    @BindView(R.id.who_is_involved)
    TextView whoIsInvolved;
    @BindView(R.id.when_happened)
    TextView whenDidItHappen;
    @BindView(R.id.location)
    TextView location;


    private Button submitButton;
    private Environment environment;
    private EditText editTextSummary;
    private Story activeStory;
    private Dialog questionDialog;
    private LocalDataHelper dataHelper;
    private final Calendar calendar = Calendar.getInstance();
    private String audio_path = "";
    private HashMap<String, Integer> localMediaMap = new HashMap<>();
    private Random randomGenerator = new Random();

    private static final int TITLE_ID = 0;
    private static final int WHO_ID = 1;
    private static final int CAUSE_ID = 2;

    private final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
            this, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyboard);
        dataHelper = new LocalDataHelper(this);
        ButterKnife.bind(this);
        String action = getIntent().getAction();


        if (action.equals(Constants.ACTION_EDIT_VIEW_STORY)) {
            int storyID = getIntent().getIntExtra("STORY_ID", -1);
            if (storyID > -1) {
                Log.d("OPENSTORY", "onCreate: YEAH");
                activeStory = dataHelper.getStory(storyID);

                addMediaToSliderLayout(activeStory);

                summary.setText(activeStory.getTitle());
                whoIsInvolved.setText(activeStory.getWho());
                whenDidItHappen.setText(activeStory.getWhen());
                whatCausedthis.setText(activeStory.getCause());
                location.setText(activeStory.getWhere());



            } else {
                Toast.makeText(this, "This activeStory can not be found",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }

        StoryBoardUtils.requestPermission(this, Manifest.permission.RECORD_AUDIO);
        StoryBoardUtils.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);


//        storyboard_add_media.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.capture_image:
//                        // TODO open scene picker
//                        Toast.makeText(Storyboard.this, "Image", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.record_audio:
//                        // TODO open audio recorder
//                        startRecording();
//                        break;
//                    case R.id.record_video:
//                        // TODO open scene picker
//                        Toast.makeText(Storyboard.this, "video", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.open_gallery:
//                        // TODO open gallery and pick images
//                        Toast.makeText(Storyboard.this, "gallery", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//                return true;
//            }
//        });


    }

    public void addMediaToSliderLayout(Story story) {
        Log.i(this.getLocalClassName(), "addMediaToSliderLayout: run");
        List<String> media = story.getMedia();
        Log.i(this.getLocalClassName(), "addMediaToSliderLayout: " + media.size());
        for (String item : media) {
            Log.i(this.getLocalClassName(), "addMediaToSliderLayout: " + item);

            String mimetype = null;
            try {
                mimetype = StoryBoardUtils.getMimeType(item);
                if (mimetype.equals(Constants.AUDIO_MIMETYPE)) {
                    localMediaMap.put(String.valueOf(randomGenerator.nextInt(10000)),
                            R.drawable.sound_wave);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        for (String name : localMediaMap.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            Log.d(this.getLocalClassName(), "name: " + name);

            textSliderView
                    .description(name)
                    .image(localMediaMap.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            storiesSlider.addSlider(textSliderView);

        }

    }

    @OnClick(R.id.button_mic)
    public void openRecorder(){
        startRecording();
    }

    @OnClick(R.id.button_camera)
    public void startCameraProcess(){
        startScenePicker(Constants.CAMERA_MODE);
    }

    @OnClick(R.id.button_video)
    public void startVideoProcess(){
        startScenePicker(Constants.VIDEO_MODE);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.summary_view)
    public void openSummaryDialog() {
        showAnswerQuestionDialog(TITLE_ID, summary);
    }

    @OnClick(R.id.when_view)
    public void openCalendar() {
        datePickerDialog.setYearRange(1985, 2028);
        datePickerDialog.show(getFragmentManager(), "datepicker");
    }

    @OnClick(R.id.who_view)
    public void openWhoIsInvolvedDialog() {
        showAnswerQuestionDialog(WHO_ID, whoIsInvolved);
    }

    @OnClick(R.id.caused_view)
    public void openWhatCausedThis() {
        showAnswerQuestionDialog(CAUSE_ID, whatCausedthis);
    }

    @OnClick(R.id.where_view)
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_RECORD_AUDIO:
                if (resultCode == RESULT_OK) {
                    Log.i(this.getLocalClassName(), "MimeType: " + StoryBoardUtils.getMimeType(audio_path));
                    Toast.makeText(this, "Audio recorded successfully! " + audio_path, Toast.LENGTH_SHORT).show();
                    activeStory.addMedia(audio_path);
                    Log.i(this.getLocalClassName(), "onActivityResult: " + activeStory.getMedia().size());
                    dataHelper.updateStory(activeStory);
                    addMediaToSliderLayout(activeStory);
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Audio was not recorded", Toast.LENGTH_SHORT).show();
                }
                break;

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

        }
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        String string_date = String.format("%02d", dayOfMonth) + " " + month + " " + year;
        // TODO attach date to post
        activeStory.setTitle("New Title");
        activeStory.setWhen(string_date);
        whenDidItHappen.setText(string_date);
        whenDidItHappen.setVisibility(View.VISIBLE);

        // update the current story
        int as = dataHelper.updateStory(activeStory);
        Log.d("UPDATE", "onDateSet: " + String.valueOf(as));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("STORYBOARD", "onResume: ");
    }

    public void showAnswerQuestionDialog(final int question_id, final TextView textView) {

        questionDialog = new Dialog(Storyboard.this);
        questionDialog.setContentView(R.layout.fragment_answer_dialog);

        submitButton = (Button) questionDialog.findViewById(R.id.saveDlgBtn);
        submitButton.setEnabled(false);

        editTextSummary = (EditText) questionDialog.findViewById(R.id.answerTextEdit);


        //find current value of summary
        String current_answer = "" + textView.getText().toString();

        //find the prompt for this question
        final String prompt = getResources().getStringArray(R.array.storyboard_prompts)[question_id];

        //if it's not default & not empty edit editTextSummary
        if (!current_answer.equals(prompt) && (!current_answer.equals(""))) {
            editTextSummary.setText(current_answer);
            submitButton.setEnabled(true);
        }

        editTextSummary.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String new_answer = "" + editTextSummary.getText().toString();
                if (new_answer.length() > 0) {
                    submitButton.setEnabled(true);
                } else {
                    submitButton.setEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        questionDialog.findViewById(R.id.cancelDlgBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionDialog.dismiss();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_answer = editTextSummary.getText().toString();

                String string_date = "";

                if (new_answer.trim().length() > 0) {

                    if (!new_answer.equals(prompt)) {
                        //save answer;

                        switch (question_id) {
                            case TITLE_ID:
                                activeStory.setTitle(new_answer);
                                break;
                            case WHO_ID:
                                activeStory.setWho(new_answer);
                                break;
                            case CAUSE_ID:
                                activeStory.setCause(new_answer);
                                break;
                        }
                        textView.setText(new_answer);
                    }
                } else {

                    textView.setText(prompt);
                }


                dataHelper.updateStory(activeStory);

                questionDialog.dismiss();
            }
        });

        questionDialog.show();
    }

    private void startRecording() {
        // ask for permissions
        audio_path = StoryBoardUtils.recordAudio(Storyboard.this, environment);
    }

    public void startScenePicker(final int mode) {

        final Dialog mDialog = new Dialog(this);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.list_pick_scene);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.setTitle(this.getResources().getString(R.string.pick_scene));
        mDialog.show();

        String[] sceneTitles = this.getResources().getStringArray(R.array.scenes);
        String[] sceneDescriptions = this.getResources().getStringArray(R.array.scenes_descriptions);
        TypedArray sceneImages = this.getResources().obtainTypedArray(R.array.scenes_images);
        ListView sceneslist = (ListView)mDialog.findViewById(R.id.listView);

        SceneAdapter scenesAdapter = new SceneAdapter(this, sceneTitles, sceneDescriptions, sceneImages, R.layout.row_scene);
        sceneslist.setAdapter(scenesAdapter);

        sceneslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int j, long l) {
                /**
                 * TODO send intent to open up camera or video overlay
                 * Forexample
                 *
                 * Intent i = new Intent(activity, OverlayCameraActivity.class);
                 * i.putExtra("mode", mode);
                 * i.putExtra("group", j);
                 * activity.startActivityForResult(i, RequestCodes.OVERLAY_CAMERA);
                 *
                 * where group, j indicates the scene picked
                 */
                mDialog.dismiss();
            }
        });

    }


    // TODO save and upload
}
