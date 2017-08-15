package org.codeforafrica.citizenreporterandroid.feedback;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import org.codeforafrica.citizenreporterandroid.BuildConfig;
import org.codeforafrica.citizenreporterandroid.R;


public class FeedbackActivity extends AppCompatActivity {

    EditText feedbackField, nameField, emailField;
    CheckBox responseCheckbox;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackField = (EditText) findViewById(R.id.EditTextFeedbackBody);
        nameField = (EditText) findViewById(R.id.EditTextName);
        emailField = (EditText) findViewById(R.id.EditTextEmail);
        responseCheckbox = (CheckBox) findViewById(R.id.CheckBoxResponse);
        spinner = (Spinner) findViewById(R.id.SpinnerFeedbackType);
    }

    public void sendFeedback(View button) {
        //Get FeedBack
        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        String feedback = feedbackField.getText().toString();
        boolean bRequiresResponse = responseCheckbox.isChecked();
        String subject = spinner.getSelectedItem().toString();
        String[] recipientEmail = new String[]{"support@codeforafrica.org"};
        String[] recipientCc = new String[]{"phillip@codeforuganda.org"};

        String appVersionName = BuildConfig.VERSION_NAME;
        String appVersionCode = String.valueOf(BuildConfig.VERSION_CODE);
        String androidVersion = getAndroidVersion();
        String deviceName = getDeviceName();

        StringBuilder body = new StringBuilder();
        body.append(feedback);
        body.append("\nApp Version Name: " + appVersionName);
        body.append("\nAndroid Version: " + androidVersion);
        body.append("\nApp Version Code: " + appVersionCode);
        body.append("\nDevice name: " + deviceName);

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_EMAIL, recipientEmail);
        intent.putExtra(Intent.EXTRA_CC, recipientCc);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body.toString());
        intent.setType("plain/text");

        startActivity(Intent.createChooser(intent, "Choose email client:"));

    }

    public String getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        String versionPrompt = this.getResources().getString(R.string.os_version);
        return versionPrompt + " : " + sdkVersion + " (" + release + ")";
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String modelPrompt = this.getResources().getString(R.string.device_model);
        if (model.startsWith(manufacturer)) {
            return modelPrompt + capitalize(model);
        } else {
            return modelPrompt + " : " + capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

}
