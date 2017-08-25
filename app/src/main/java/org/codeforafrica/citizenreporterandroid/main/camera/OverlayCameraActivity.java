package org.codeforafrica.citizenreporterandroid.main.camera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.codeforafrica.citizenreporterandroid.R;

/**
 * Created by Mugiwara_Munyi on 17/08/2017.
 */

public class OverlayCameraActivity extends Activity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        intent = getIntent();
        int g = intent.getIntExtra("group", 0);

        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState){
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, OverlayCameraFragment.newInstance(g))
                    .commit();

        }
    }



}
