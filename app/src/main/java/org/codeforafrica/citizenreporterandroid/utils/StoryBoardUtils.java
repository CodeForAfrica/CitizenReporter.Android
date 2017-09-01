package org.codeforafrica.citizenreporterandroid.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.webkit.MimeTypeMap;
import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;
import java.io.File;
import java.io.IOException;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.app.Constants;

/**
 * Created by Ahereza on 8/10/17.
 */

public class StoryBoardUtils {

  public interface LaunchCameraCallback {
    void onMediaCapturePathReady(String mediaCapturePath);
  }

  public interface LaunchVideoCameraCallback {
    void onMediaCapturePathReady(String mediaCapturePath);
  }

  public static String recordAudio(Activity activity, Environment environment) {
    String audio_filename =
        Constants.RECORDING_PREFIX + String.valueOf(System.currentTimeMillis()) + ".wav";
    String audio_path = environment.getExternalStorageDirectory().getPath() + audio_filename;

    AndroidAudioRecorder.with(activity)
        // Required
        .setFilePath(audio_path)
        .setColor(ContextCompat.getColor(activity, R.color.cfAfrica_red))
        .setRequestCode(Constants.REQUEST_RECORD_AUDIO)

        // Optional
        .setSource(AudioSource.MIC)
        .setChannel(AudioChannel.STEREO)
        .setSampleRate(AudioSampleRate.HZ_48000)
        .setAutoStart(false)
        .setKeepDisplayOn(true)

        // Start recording
        .record();

    return audio_path;
  }

  public static void requestPermission(Activity activity, String permission) {
    if (ContextCompat.checkSelfPermission(activity, permission)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(activity, new String[] { permission }, 0);
    }
  }

  public static String getMimeType(String url) {
    String type = null;
    String extension = MimeTypeMap.getFileExtensionFromUrl(url);
    if (extension != null) {
      type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
    return type;
  }

  public static void launchCamera(Activity activity, LaunchCameraCallback callback) {
    //showOverlayCamera(activity, 1);
    Intent intent = preparelaunchCamera(activity, callback);
    if (intent != null) {
      activity.startActivityForResult(intent, RequestCodes.TAKE_PHOTO);
    }
  }

  private static Intent preparelaunchCamera(Context context, LaunchCameraCallback callback) {
    String state = android.os.Environment.getExternalStorageState();
    if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
      showSDCardRequiredDialog(context);
      return null;
    } else {
      return getLaunchCameraIntent(callback);
    }
  }

  private static void showSDCardRequiredDialog(Context context) {
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
    dialogBuilder.setTitle(context.getResources().getText(R.string.sdcard_title));
    dialogBuilder.setMessage(context.getResources().getText(R.string.sdcard_message));
    dialogBuilder.setPositiveButton(context.getString(R.string.ok),
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();
          }
        });
    dialogBuilder.setCancelable(true);
    dialogBuilder.create().show();
  }

  private static Intent getLaunchCameraIntent(LaunchCameraCallback callback) {

    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

    String mediaCapturePath = path
        + File.separator
        + "Camera"
        + File.separator
        + "cr-"
        + System.currentTimeMillis()
        + ".jpg";
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
        Uri.fromFile(new File(mediaCapturePath)));

    if (callback != null) {
      callback.onMediaCapturePathReady(mediaCapturePath);
    }
    // make sure the directory we plan to store the recording in exists
    File directory = new File(mediaCapturePath).getParentFile();
    if (!directory.exists() && !directory.mkdirs()) {
      try {
        throw new IOException("Path to file could not be created.");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return intent;
  }

  public static void launchVideoCamera_SD(Activity activity, LaunchVideoCameraCallback callback) {
    Intent intent = prepareVideoCameraIntent_SD(activity, callback);
    if (intent != null) {
      activity.startActivityForResult(intent, RequestCodes.TAKE_PHOTO);
    }
  }

  private static Intent prepareVideoCameraIntent_SD(Context context,
      LaunchVideoCameraCallback callback) {
    String state = android.os.Environment.getExternalStorageState();
    if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
      showSDCardRequiredDialog(context);
      return null;
    }
    String app_name = context.getResources().getString(R.string.app_name);

    File path = new File(Environment.getExternalStorageDirectory(), app_name + "/video");

    String mediaCapturePath = path + File.separator + "cr-" + System.currentTimeMillis() + ".mp4";
    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
        Uri.fromFile(new File(mediaCapturePath)));

    if (callback != null) {
      callback.onMediaCapturePathReady(mediaCapturePath);
    }
    // make sure the directory we plan to store the recording in exists
    File directory = new File(mediaCapturePath).getParentFile();
    if (!directory.exists() && !directory.mkdirs()) {
      try {
        throw new IOException("Path to file could not be created.");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return intent;
  }
}
