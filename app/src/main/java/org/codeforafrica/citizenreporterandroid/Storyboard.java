package org.codeforafrica.citizenreporterandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.android.datetimepicker.date.DatePickerDialog;

import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.data.sources.LocalDataHelper;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;
import org.codeforafrica.citizenreporterandroid.utils.Constants;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Storyboard extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.slider) SliderLayout storiesSlider;
    @BindView(R.id.summary) TextView summary;
    @BindView(R.id.cause) TextView whatCausedthis;
    @BindView(R.id.who_is_involved) TextView whoIsInvolved;
    @BindView(R.id.when_happened) TextView whenDidItHappen;
    @BindView(R.id.location) TextView location;
    private Story activeStory;
    private LocalDataHelper dataHelper;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 31;
    private final Calendar calendar = Calendar.getInstance();
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

        //Create HashMap for SliderLayout
        HashMap<String, String> imageUrlMaps = new HashMap<>();
        imageUrlMaps.put("Citizen Reporter", "http://media.cleveland.com/pdextra/photo/pittsburgh-police-g20jpg-6ac2c6b9b15b78ef.jpg");
        imageUrlMaps.put("Citizen Reporter 2", "https://assets.fastcompany.com/image/upload/w_1280,f_auto,q_auto,fl_lossy/fc/3034902-poster-p-1-3034902-poster-riotpolice.jpg");
        imageUrlMaps.put("Citizen Reporter 3", "http://media.gettyimages.com/photos/baltimore-firefighters-prepare-to-connect-a-hose-while-inspecting-a-picture-id471446974?s=612x612");

        //Loop through the hash map
        for(String name: imageUrlMaps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);

            textSliderView
                    .description(name)
                    .image(imageUrlMaps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            storiesSlider.addSlider(textSliderView);

        }
    }

    public void addImagesToSliderLayout(String[] images) {
        // TODO
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
    public void openSummaryDialog() {}

    @OnClick(R.id.when_view)
    public void openCalendar() {
        datePickerDialog.setYearRange(1985, 2028);
        datePickerDialog.show(getFragmentManager(), "datepicker");
    }

    @OnClick(R.id.who_view)
    public void openWhoIsInvolvedDialog() {}

    @OnClick(R.id.caused_view)
    public void openWhatCausedThis() {}

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
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
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

    // TODO save and upload
}
