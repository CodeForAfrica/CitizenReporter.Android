package org.codeforafrica.citizenreporterandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_list);

        ArrayList<String> menuItems = new ArrayList<>();
        menuItems.add("About");
        menuItems.add("Support Channel");
        menuItems.add("Feedback");

        ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(menuAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent aboutIntent = new Intent(getBaseContext(), AboutActivity.class);
                    startActivity(aboutIntent);
                } else if (i == 1) {
                    Intent supportIntent = new Intent(getBaseContext(), SupportChannelActivity.class);
                    startActivity(supportIntent);
                } else {
                    Intent feedbackIntent = new Intent(getBaseContext(), FeedbackActivity.class);
                    startActivity(feedbackIntent);
                }
            }
        });
    }

    public void openAbout(View view) {
        Intent about = new Intent(this, AboutActivity.class);
        startActivity(about);
    }

    public void openSupportChannel(View view) {
        Intent support = new Intent(this, SupportChannelActivity.class);
        startActivity(support);
    }

    public void openFeedback(View view) {
        Intent about = new Intent(this, FeedbackActivity.class);
        startActivity(about);
    }
}