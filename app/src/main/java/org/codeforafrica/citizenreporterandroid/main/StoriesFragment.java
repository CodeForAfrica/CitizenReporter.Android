package org.codeforafrica.citizenreporterandroid.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.codeforafrica.citizenreporterandroid.R;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoriesFragment extends Fragment implements View.OnClickListener {

    public StoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoriesFragment newInstance() {
        StoriesFragment fragment = new StoriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stories, container, false);


        //Create TextView listener
        TextView textView = (TextView) view.findViewById(R.id.tvLocation);
        textView.setOnClickListener(this);

        //Create SliderLayout instance
        SliderLayout storiesSlider = (SliderLayout) view.findViewById(R.id.slider);

        //Create HashMap for SliderLayout
        HashMap<String, String> imageUrlMaps = new HashMap<>();
        imageUrlMaps.put("Citizen Reporter", "http://media.cleveland.com/pdextra/photo/pittsburgh-police-g20jpg-6ac2c6b9b15b78ef.jpg");
        imageUrlMaps.put("Citizen Reporter 2", "https://assets.fastcompany.com/image/upload/w_1280,f_auto,q_auto,fl_lossy/fc/3034902-poster-p-1-3034902-poster-riotpolice.jpg");
        imageUrlMaps.put("Citizen Reporter 3", "http://media.gettyimages.com/photos/baltimore-firefighters-prepare-to-connect-a-hose-while-inspecting-a-picture-id471446974?s=612x612");

        //Loop through the hash map
        for(String name: imageUrlMaps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getContext());

            textSliderView
                    .description(name)
                    .image(imageUrlMaps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            storiesSlider.addSlider(textSliderView);

        }

        return view;
    }


    @Override
    public void onClick(View view) {
      switch (view.getId()){
          case R.id.tvLocation:
              Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
              break;
      }
    }
}
