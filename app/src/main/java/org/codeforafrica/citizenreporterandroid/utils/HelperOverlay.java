package org.codeforafrica.citizenreporterandroid.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import android.widget.LinearLayout;

import org.codeforafrica.citizenreporterandroid.R;

/**
 * Created by Mugiwara_Munyi on 16/08/2017.
 */


public class HelperOverlay extends Activity implements SurfaceHolder.Callback{
    Context context;
    private SurfaceView overlay;
    private SurfaceHolder overlayHolder;
    private boolean inPreview = false;
    ImageView image;
    CameraManager camera;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.photo_overlay);
        image = (ImageView) findViewById(R.id.image);
        overlay = (SurfaceView) findViewById(R.id.surface_view);

        overlayHolder = overlay.getHolder();

        showOverlay();
    }


    private void showOverlay(){

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        dialog.setContentView(R.layout.photo_overlay);
        LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.overlayLayout);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
