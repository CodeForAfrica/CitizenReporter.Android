package org.codeforafrica.citizenreporterandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import org.codeforafrica.citizenreporterandroid.R;

/**
 * Created by Mugiwara_Munyi on 16/08/2017.
 */


public class HelperOverlay extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    private SurfaceView overlay;
    private SurfaceHolder overlayHolder;
    private boolean inPreview = false;
    private Bitmap bmp;



    public HelperOverlay(Context context) {
        super(context);
        this.bmp = BitmapFactory.decodeResource(getResources(), R.drawable.close_face_overlay_001);
        overlayHolder = getHolder();

    }



    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Canvas canvas = overlayHolder.lockCanvas();
        if(canvas != null){
            draw(canvas);
            overlayHolder.unlockCanvasAndPost(canvas);
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }



}
