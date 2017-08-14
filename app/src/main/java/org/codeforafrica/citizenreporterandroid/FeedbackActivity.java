package org.codeforafrica.citizenreporterandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


public class FeedbackActivity extends AppCompatActivity {
    EditText feedbackField, nameField, emailField;
    CheckBox responseCheckbox;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_EMAIL, recipientEmail);
        intent.putExtra(Intent.EXTRA_CC, recipientCc);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, feedback);
        intent.setType("plain/text");

        startActivity(Intent.createChooser(intent, "Choose email client:"));

    }

}
