package org.codeforafrica.citizenreporterandroid.ui.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.codeforafrica.citizenreporterandroid.R;

public class GoogleSignInActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    private LinearLayout user_profile;
    private Button SignOut;
    private SignInButton SignIn;
    private TextView Name, Email;
    private ImageView profile_pic;
    private GoogleApiClient googleApiClient;
    private static final int REQUEST_CODE = 1;

    private Button HomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);
        user_profile = (LinearLayout)findViewById(R.id.user_profile);
        SignOut = (Button)findViewById(R.id.sign_out);
        SignIn = (SignInButton)findViewById(R.id.sign_in);
        Name = (TextView)findViewById(R.id.user_name);
        Email = (TextView)findViewById(R.id.user_email);
        profile_pic = (ImageView)findViewById(R.id.profile_pic);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build() ;

        SignIn.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        user_profile.setVisibility(View.GONE);

        HomePage = (Button)findViewById(R.id.home_button);
        HomePage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                onSignIn();
                break;
            case R.id.sign_out:
                onSignOut();
                break;
            case R.id.home_button:
                Intent intent = new Intent(GoogleSignInActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            signInResult(result);
        }
    }

    private void onSignIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQUEST_CODE );
    }

    private void onSignOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    private void signInResult(GoogleSignInResult result) {

        if(result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String image_url = account.getPhotoUrl().toString();
            Name.setText(name);
            Email.setText(email);
            Glide.with(this).load(image_url).into(profile_pic);
            updateUI(true);
        } else {
            updateUI(false);
        }
    }

    private void updateUI (boolean isLogin) {
        if(isLogin) {
            user_profile.setVisibility(View.VISIBLE);
            SignIn.setVisibility(View.GONE);
            HomePage.setVisibility(View.GONE);
        } else {
            user_profile.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
            HomePage.setVisibility(View.VISIBLE);
        }
    }
}
