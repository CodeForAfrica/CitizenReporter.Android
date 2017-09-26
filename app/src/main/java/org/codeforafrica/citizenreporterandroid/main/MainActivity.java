package org.codeforafrica.citizenreporterandroid.main;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.parse.ParseUser;

import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.adapter.MainFragmentAdapter;
import org.codeforafrica.citizenreporterandroid.app.Constants;
import org.codeforafrica.citizenreporterandroid.ui.about.AboutActivity;
import org.codeforafrica.citizenreporterandroid.ui.assignments.AssignmentsFragment;
import org.codeforafrica.citizenreporterandroid.ui.base.BaseActivity;
import org.codeforafrica.citizenreporterandroid.ui.settings.SettingsFragment;
import org.codeforafrica.citizenreporterandroid.ui.stories.StoriesFragment;
import org.codeforafrica.citizenreporterandroid.utils.APIClient;
import org.codeforafrica.citizenreporterandroid.utils.CReporterAPI;
import org.codeforafrica.citizenreporterandroid.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_FINE_LOCATION = 12;
    private static final String TAG = "MainActivity";
    private CReporterAPI apiClient;
    private String fb_id;
    private String profile_url;
    private SharedPreferences preferences;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private ViewPager viewPager;
    private MainFragmentAdapter fragmentsAdapter;
    private TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        prepareDataResource();
        // Initialise and Bind the ViewPager and Adapter containing the fragments
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        fragmentsAdapter = new MainFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(fragmentsAdapter);

        // Bind TabLayout with the View Pager
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        String from_storyboard = intent.getStringExtra("Source");
        if (from_storyboard == "uploaded") {
            Log.d(TAG, "onCreate: has uploaded extra");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, StoriesFragment.newInstance());
            transaction.commit();
        }

        apiClient = APIClient.getApiClient();
        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        fb_id = preferences.getString("fb_id", "");
        profile_url = preferences.getString(Constants.PREF_KEY_CURRENT_USER_PROFILE_PIC_URL,
                "http://www.freeiconspng.com/uploads/account-icon-21.png");
        getUserLocation();

        String token = FirebaseInstanceId.getInstance().getToken();
        ParseUser user = ParseUser.getCurrentUser();
        if (token != null && user != null) {
            user.put("fcm_token", token);
            user.saveEventually();
        }

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);

    }


    // Add Fragments and their titles to the ArrayList
    private void prepareDataResource() {

        addData(new AssignmentsFragment(), "Assignments");
        addData(new StoriesFragment(), "Stories");
        addData(new SettingsFragment(), "Settings");
    }

    // Method for adding Fragments and their titles to the ArrayList
    private void addData(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }

    private void getUserLocation() {
        FusedLocationProviderClient mFusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location == null) {
                                // do nothing

                            } else {
                                String co_ord = String.valueOf(location.getLatitude()) + ", " + String.valueOf(
                                        location.getLongitude());

                                if (NetworkUtils.isNetworkAvailable(MainActivity.this) && !Objects.equals(fb_id,
                                        "")) {
                                    // TODO send location to the server
                                }
                                Log.d(TAG, "onSuccess: Long: "
                                        + String.valueOf(location.getLongitude())
                                        + " Lat: "
                                        + String.valueOf(location.getLatitude()));
                            }
                        }
                    });
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
            } else {
                Toast.makeText(this, "Permission to access your location was denied", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @OnClick(R.id.about_icon_appbar)
    public void goToAbout() {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }
}
