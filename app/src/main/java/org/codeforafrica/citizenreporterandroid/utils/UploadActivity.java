package org.codeforafrica.citizenreporterandroid.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.codeforafrica.citizenreporterandroid.R;

/**
 * Created by Mugiwara_Munyi on 30/08/2017.
 */

public class UploadActivity extends Activity {
    ProgressBar progressBar;
    TextView txtPercentage;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txtPercentage = (TextView) findViewById(R.id.txtProgressBar);

    }



private class UploadFile extends AsyncTask<Void, Integer, String> {



    @Override
    protected void onPreExecute() {
        // setting progress bar to zero
        progressBar.setProgress(0);
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer...progress){
        //Making progress bar visible
        progressBar.setVisibility(View.VISIBLE);

        //Updating progress bar value
        progressBar.setProgress(progress[0]);

        //Updating percentage value
        txtPercentage.setText(String.valueOf(progress[0] + "%"));


    }
    protected String doInBackground(Void...params){
        return uploadFile();//
    }

    private String uploadFile(){
        String responseString = null;

        return responseString;

    }
}
}
