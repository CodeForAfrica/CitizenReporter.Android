package org.codeforafrica.citizenreporterandroid.main.assignments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.utils.Constants;

import static org.codeforafrica.citizenreporterandroid.utils.Constants.ACTIVITY_RECORD_VIDEO;

public class VideoCaptureActivity extends AppCompatActivity {

    private Button recordVideo, playVideo;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_capture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recordVideo = (Button) findViewById(R.id.recordButton);
        playVideo = (Button) findViewById(R.id.playButton);
        videoView = (VideoView) findViewById(R.id.videoView1);

        // Set onClick Listeners for the buttons
        recordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recordIntent = new Intent();
                recordIntent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);

                startActivityForResult(recordIntent, ACTIVITY_RECORD_VIDEO);
            }
        });

        playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ACTIVITY_RECORD_VIDEO && resultCode==RESULT_OK) {
            Uri videoUri = data.getData();
            Log.d("Video", "Uri " + videoUri);
            videoView.setVideoURI(videoUri );
        }
    }

}
