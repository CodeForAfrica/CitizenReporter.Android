package org.codeforafrica.citizenreporterandroid.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.main.api.ApiClient;
import org.codeforafrica.citizenreporterandroid.main.api.ApiInterface;
import org.codeforafrica.citizenreporterandroid.main.models.Assignments;



import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private ApiInterface apiClient;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        apiClient = ApiClient.getApiClient();
        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()){
                            case R.id.assignments_item:
                                selectedFragment = AssignmentsFragment.newInstance();
                                break;
                            case R.id.stories_item:
                                selectedFragment = StoriesFragment.newInstance();
                                break;
                            case R.id.about_item:
                                selectedFragment = AboutFragment.newInstance();
                                break;

                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }

                });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, AssignmentsFragment.newInstance());
        transaction.commit();

    }

}
