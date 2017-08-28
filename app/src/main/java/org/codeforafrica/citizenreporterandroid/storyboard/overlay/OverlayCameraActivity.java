package org.codeforafrica.citizenreporterandroid.storyboard.overlay;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.pixplicity.sharp.Sharp;
import com.pixplicity.sharp.SharpPicture;


import java.io.IOException;


public class OverlayCameraActivity extends ActionBarActivity implements Callback, SwipeInterface
{

    private Camera camera;
    private SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;
    private ImageView mOverlayView;
    private Canvas canvas;
    private Bitmap bitmap;

    String[] overlays = null;
    int overlayIdx = 0;
    int overlayGroup = 0;

    boolean cameraOn = false;

    private int mColorRed = 0;
    private int mColorGreen = 0;
    private int mColorBlue = 0;

    private int mStoryMode = -1;
    Dialog mDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        overlayGroup = getIntent().getIntExtra("group", 0);
        overlayIdx = getIntent().getIntExtra("overlay", 0);



        showOverlayCam();
    }

    public void showOverlayCam(){

        mStoryMode = getIntent().getIntExtra("mode",-1);

        mOverlayView = new ImageView(OverlayCameraActivity.this);

        ActivitySwipeDetector swipe = new ActivitySwipeDetector(OverlayCameraActivity.this);
        mOverlayView.setOnTouchListener(swipe);

        mOverlayView.setOnClickListener(new OnClickListener (){
            @Override
            public void onClick(View v) {

                closeOverlay();

            }

        });

        mSurfaceView = new SurfaceView(OverlayCameraActivity.this);
        addContentView(mSurfaceView, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(OverlayCameraActivity.this);
//        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        addContentView(mOverlayView, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
    }


    private void closeOverlay () {

        if (cameraOn)
        {
            cameraOn = false;

            if (camera != null)
            {
                camera.stopPreview();
                camera.release();
            }
        }

        Intent intent=new Intent();
        intent.putExtra("MESSAGE", "Overlay selected");
        setResult(mStoryMode, intent);

        finish();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        closeOverlay();
    }


    private void setOverlayImage (int idx)
    {
        try
        {
            String groupPath = "images/overlays/svg/" + overlayGroup;

            //if (overlays == null)
            overlays = getAssets().list(groupPath);

            bitmap = Bitmap.createBitmap(mOverlayView.getWidth(),mOverlayView.getHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);

            String imgPath = groupPath + '/' + overlays[idx];
            //    SVG svg = SVGParser.getSVGFromAsset(getAssets(), "images/overlays/svg/" + overlays[idx],0xFFFFFF,0xCC0000);

            Sharp svg = Sharp.loadAsset(getAssets(), imgPath);

            Rect rBounds = new Rect(0,0,mOverlayView.getWidth(),mOverlayView.getHeight());
            SharpPicture p = svg.getSharpPicture();
            canvas.drawPicture(p.getPicture(), rBounds);

            mOverlayView.setImageBitmap( bitmap);
        }
        catch(IOException ex)
        {
            return;
        }

    }


    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

        if (camera != null)
        {
            Camera.Parameters p = camera.getParameters();
            p.setPreviewSize(arg2, arg3);
            bitmap = Bitmap.createBitmap(arg2, arg3, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            setOverlayImage (overlayIdx);
            try {
                camera.setPreviewDisplay(arg0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if (camera == null && (!cameraOn)&& Camera.getNumberOfCameras() > 0)
        {
            camera = Camera.open();
            cameraOn = true;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        closeOverlay();
    }


    @Override
    public void bottom2top(View v) {
        mColorRed = 255;
        mColorGreen = 255;
        mColorBlue = 255;
        setOverlayImage(overlayIdx);

    }


    @Override
    public void left2right(View v) {
        // TODO Auto-generated method stub
        overlayIdx--;
        if (overlayIdx < 0)
            overlayIdx = overlays.length-1;

        setOverlayImage(overlayIdx);
    }


    @Override
    public void right2left(View v) {

        overlayIdx++;
        if (overlayIdx == overlays.length)
            overlayIdx = 0;

        setOverlayImage(overlayIdx);

    }


    @Override
    public void top2bottom(View v) {
        mColorRed = 0;
        mColorGreen = 0;
        mColorBlue = 0;

        setOverlayImage(overlayIdx);
    }

}