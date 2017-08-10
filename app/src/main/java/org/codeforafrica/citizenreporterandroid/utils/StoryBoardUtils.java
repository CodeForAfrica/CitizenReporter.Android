package org.codeforafrica.citizenreporterandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import org.codeforafrica.citizenreporterandroid.R;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;

/**
 * Created by Ahereza on 8/10/17.
 */

public class StoryBoardUtils {

    public static void recordAudio(Activity activity, Environment environment) {
        String audio_filename = Constants.RECORDING_PREFIX +
                String.valueOf(System.currentTimeMillis()) + ".wav";
        String audio_path =  environment.getExternalStorageDirectory().getPath()
                + audio_filename;

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
    }

    public static void requestPermission(Activity activity, String permission) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, 0);
        }
    }
}
