package org.codeforafrica.citizenreporterandroid.storyboard.overlay;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

import com.pixplicity.sharp.Sharp;
import com.pixplicity.sharp.SharpPicture;


import org.codeforafrica.citizenreporterandroid.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class OverlayCameraActivity extends AppCompatActivity {
    int overlayIdx = 0;
    int overlayGroup = 0;

    private int mStoryMode = -1;

    private Size previewSize;
    private String mCameraId;
    private TextureView mTextureView;
    private CaptureRequest mPreviewCaptureRequest;
    private CaptureRequest.Builder mPreviewCaptureRequestBuilder;
    private CameraCaptureSession mCameraCaptureSession;
    private ImageView mOverlayView;
    private CameraCaptureSession.CaptureCallback mSessionCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
            super.onCaptureStarted(session, request, timestamp, frameNumber);
        }
    };
    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            setupCamera(width, height);
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            configureTransform(width, height);

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };
    private String[] overlays;
    private Bitmap bitmap;
    private Canvas canvas;

    private void openCamera() {
        configureTransform(previewSize.getWidth(), previewSize.getHeight());
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            cameraManager.openCamera(mCameraId, mCameraDeviceStateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private CameraDevice mCameraDevice;
    private CameraDevice.StateCallback mCameraDeviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            mCameraDevice = camera;
            createCameraPreviewSession();
//            Toast.makeText(CamaraIntentActivity.this, "camera Opened", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            camera.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            camera.close();
            mCameraDevice = null;
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        overlayGroup = getIntent().getIntExtra("group", 0);
        overlayIdx = getIntent().getIntExtra("overlay", 0);

        mOverlayView = new ImageView(OverlayCameraActivity.this);

        mOverlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OverlayCameraActivity.this, "Overlay Clicked", Toast.LENGTH_LONG).show();
            }
        });

        mTextureView = (TextureView) findViewById(R.id.textureView);

        addContentView(mOverlayView, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));

    }


    private void closeOverlay () {


        Intent intent=new Intent();
        intent.putExtra("MESSAGE", "Overlay selected");
        setResult(mStoryMode, intent);

        finish();

    }
    @Override
    protected void onResume() {
        super.onResume();


        if (mTextureView.isAvailable()) {
            setupCamera(mTextureView.getWidth(), mTextureView.getHeight());

        } else {
            mTextureView.setSurfaceTextureListener(surfaceTextureListener);
        }
    }

    @Override
    protected void onPause() {

        closeCamera();

        super.onPause();
    }

    private void closeCamera() {
        if (mCameraCaptureSession != null) {
            mCameraCaptureSession.close();
            mCameraCaptureSession = null;
        }

        if (mCameraDevice != null) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setOverlayImage(int idx)
    {
        try
        {
            // images/overlays/svg
            String groupPath = "images/overlays/svg/" + overlayGroup;

            //if (overlays == null)
            overlays = getAssets().list(groupPath);

            bitmap = Bitmap.createBitmap(mOverlayView.getWidth(),mOverlayView.getHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);

            String imgPath = groupPath + '/' + overlays[idx];
            //    SVG images.overlays.svg = SVGParser.getSVGFromAsset(getAssets(), "images/overlays/images.overlays.svg/" + overlays[idx],0xFFFFFF,0xCC0000);

            Sharp svg = Sharp.loadAsset(getAssets(), imgPath);

            Rect rBounds = new Rect(0,0,mOverlayView.getWidth(),mOverlayView.getHeight());
            SharpPicture p = svg.getSharpPicture();
            canvas.drawPicture(p.getPicture(), rBounds);

            mOverlayView.setImageBitmap(bitmap);
        }
        catch(IOException ex)
        {
            Log.e("Cardi", "error rendering overlay",ex);
            return;
        }

    }


    private Bitmap changeColor(Bitmap src,int pixelRed, int pixelGreen, int pixelBlue){

        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap dest = Bitmap.createBitmap(
                width, height, src.getConfig());

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                int pixelColor = src.getPixel(x, y);
                int pixelAlpha = Color.alpha(pixelColor);

                int newPixel = Color.argb(
                        pixelAlpha, pixelRed, pixelGreen, pixelBlue);

                dest.setPixel(x, y, newPixel);
            }
        }
        return dest;
    }

    private void createCameraPreviewSession() {
        try {
            SurfaceTexture surfaceTexture = mTextureView.getSurfaceTexture();
            surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
            Surface previewSurface = new Surface(surfaceTexture);
            mPreviewCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mPreviewCaptureRequestBuilder.addTarget(previewSurface);
            mCameraDevice.createCaptureSession(Arrays.asList(previewSurface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if (mCameraDevice == null) {
                        return;
                    }

                    try {
                        mPreviewCaptureRequest = mPreviewCaptureRequestBuilder.build();
                        mCameraCaptureSession = session;
                        mCameraCaptureSession.setRepeatingRequest(mPreviewCaptureRequest, mSessionCaptureCallback, null);
                        setOverlayImage(overlayIdx);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Toast.makeText(OverlayCameraActivity.this, "create camera session failed", Toast.LENGTH_LONG).show();

                }
            }, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }



    private void setupCamera(int width, int height) {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)
                        == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }
                StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                previewSize = getPreferedPreviewSize(map.getOutputSizes(SurfaceTexture.class), width, height);
                mCameraId = cameraId;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private Size getPreferedPreviewSize(Size[] mapSizes, int width, int height) {
        List<Size> collectedSizes = new ArrayList<>();
        for(Size option: mapSizes) {
            if (width > height) {
                if (option.getWidth() > width && option.getHeight() > height) {
                    collectedSizes.add(option);
                }
            } else {
                if (option.getWidth() > height && option.getHeight() > width) {
                    collectedSizes.add(option);
                }
            }
        }
        if (collectedSizes.size() > 0) {
            return Collections.min(collectedSizes, new Comparator<Size>() {
                @Override
                public int compare(Size lhs, Size rhs) {
                    return Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getWidth() * rhs.getHeight());
                }
            });
        }
        return mapSizes[0];
    }


    private void configureTransform(int viewWidth, int viewHeight) {
        Activity activity = this;
        if (null == mTextureView || null == previewSize || null == activity) {
            return;
        }
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        RectF bufferRect = new RectF(0, 0, previewSize.getHeight(), previewSize.getWidth());
        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(
                    (float) viewHeight / previewSize.getHeight(),
                    (float) viewWidth / previewSize.getWidth());
            matrix.postScale(scale, 3, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180, centerX, centerY);
        }
        mTextureView.setTransform(matrix);
    }

}
