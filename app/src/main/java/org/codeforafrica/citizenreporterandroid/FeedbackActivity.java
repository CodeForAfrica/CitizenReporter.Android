package org.codeforafrica.citizenreporterandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.CheckBox;


public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//    //Get FeedBack UserName
//    final EditText nameField = (EditText) findViewById(R.id.EditTextName);
//    String name = nameField.getText().toString();
//
//    //Get FeedBack UserEmail
//    final EditText emailField = (EditText) findViewById(R.id.EditTextEmail);
//    String email = emailField.getText().toString();
//
//    //Get FeedBack UserFeedBackMessage
//    final EditText feedbackField = (EditText) findViewById(R.id.EditTextFeedbackBody);
//    String feedback = feedbackField.getText().toString();
//
//    //Get FeedBack CheckBoxStatus
//    final CheckBox responseCheckbox = (CheckBox) findViewById(R.id.CheckBoxResponse);
//    boolean bRequiresResponse = responseCheckbox.isChecked();

    public void sendFeedback(View button) {
        // Do click handling here
    }

}
