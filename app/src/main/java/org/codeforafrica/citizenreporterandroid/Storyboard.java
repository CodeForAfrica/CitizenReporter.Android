package org.codeforafrica.citizenreporterandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.security.AccessController.getContext;

public class Storyboard extends AppCompatActivity {
    @BindView(R.id.slider) SliderLayout storiesSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyboard);
        ButterKnife.bind(this);

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

//    @OnClick(R.id.summary_view)
//    private static void openSummaryDialog() {}
//
//    @OnClick(R.id.when_view)
//    private static void openCalendar() {}
//
//    @OnClick(R.id.who_view)
//    private static void openWhoIsInvolvedDialog() {}
//
//    @OnClick(R.id.caused_view)
//    private static void openWhatCausedThis() {}
}
