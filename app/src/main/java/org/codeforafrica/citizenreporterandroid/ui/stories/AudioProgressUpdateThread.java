package org.codeforafrica.citizenreporterandroid.ui.stories;

import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by edwinkato on 9/28/17.
 */

public class AudioProgressUpdateThread implements Runnable{
    private MediaPlayer mediaPlayer;
    private Handler myHandler;
    private TextView audioTime;
    private SeekBar seekbar;

    public AudioProgressUpdateThread(MediaPlayer mediaPlayer, Handler myHandler, TextView audioTime, SeekBar seekbar) {
        this.mediaPlayer = mediaPlayer;
        this.myHandler = myHandler;
        this.audioTime = audioTime;
        this.seekbar = seekbar;
    }

    @Override
    public void run() {
        int currentPosition = mediaPlayer.getCurrentPosition();
        int duration = mediaPlayer.getDuration();
        audioTime.setText(String.format(Locale.ROOT, "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours((long) currentPosition),
                TimeUnit.MILLISECONDS.toMinutes((long) currentPosition),
                TimeUnit.MILLISECONDS.toSeconds((long) currentPosition) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                toMinutes((long) currentPosition)))
        );

        seekbar.setMax(duration);
        seekbar.setProgress(currentPosition);

        myHandler.postDelayed(this, 100);
    }
}
