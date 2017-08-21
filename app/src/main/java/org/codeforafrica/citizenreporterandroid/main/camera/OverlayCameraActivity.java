package org.codeforafrica.citizenreporterandroid.main.camera;

import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;

import org.codeforafrica.citizenreporterandroid.R;

/**
 * Created by Mugiwara_Munyi on 17/08/2017.
 */

public class OverlayCameraActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);
        if (null == savedInstanceState){
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, OverlayCameraFragment.newInstance())
                    .commit();
        }
    }



}
