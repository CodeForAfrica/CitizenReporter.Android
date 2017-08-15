package org.codeforafrica.citizenreporterandroid.main;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import org.codeforafrica.citizenreporterandroid.BaseActivity;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.SettingsFragment;
import org.codeforafrica.citizenreporterandroid.main.assignments.AssignmentsFragment;
import org.codeforafrica.citizenreporterandroid.main.stories.StoriesFragment;
import org.codeforafrica.citizenreporterandroid.utils.APIClient;
import org.codeforafrica.citizenreporterandroid.utils.APIInterface;
import org.codeforafrica.citizenreporterandroid.utils.NetworkHelper;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    private static final int REQUEST_FINE_LOCATION = 12;
    private static final String TAG = "MainActivity";
    private APIInterface apiClient;
    private String fb_id;
    private String profile_url;
    private SharedPreferences preferences;

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.profile_pic)
    AppCompatImageView profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        apiClient = APIClient.getApiClient();
        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        fb_id = preferences.getString("fb_id", "");
        profile_url = preferences.getString("profile_url",
                "http://www.freeiconspng.com/uploads/account-icon-21.png");
        Picasso.with(this).load(profile_url).fit().into(profile_pic);
        getUserLocation();


        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.assignments_item:
                                selectedFragment = AssignmentsFragment.newInstance();
                                break;
                            case R.id.stories_item:
                                selectedFragment = StoriesFragment.newInstance();
                                break;
                            case R.id.settings_item:
                                selectedFragment = SettingsFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, AssignmentsFragment.newInstance());
        transaction.commit();

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);
    }




    private void getUserLocation() {
        FusedLocationProviderClient mFusedLocationClient
                = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this,
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location == null) {
                                // do nothing

                            } else {
                                String co_ord = String.valueOf(location.getLatitude())
                                        + ", "
                                        + String.valueOf(location.getLongitude());

                                if (NetworkHelper.isNetworkAvailable(MainActivity.this) && !Objects.equals(fb_id, "")) {
                                    // TODO send location to the server
                                    NetworkHelper.updateLocation(MainActivity.this, apiClient, co_ord, fb_id);
                                }
                                Log.d(TAG, "onSuccess: Long: "
                                        + String.valueOf(location.getLongitude())
                                        + " Lat: " + String.valueOf(location.getLatitude()));
                            }

                        }
                    });

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){

            }

            ActivityCompat.requestPermissions(
                    this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
            } else {
                Toast.makeText(this,
                        "Permission to access your location was denied",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
