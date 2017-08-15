package org.codeforafrica.citizenreporterandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.LoginManager;

import org.codeforafrica.citizenreporterandroid.auth.LoginActivity;
import org.codeforafrica.citizenreporterandroid.feedback.FeedbackActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.support_menu_item)
    public void startSupportChannelActivity(){
        startActivity(new Intent(getActivity(), SupportChannelActivity.class));
    }

    @OnClick(R.id.about_menu_item)
    public void startAboutActivity(){
        startActivity(new Intent(getActivity(), AboutActivity.class));
    }

    @OnClick(R.id.feedback_menu_item)
    public void startFeedbackActivity(){
        startActivity(new Intent(getActivity(), FeedbackActivity.class));
    }

    @OnClick(R.id.facebook_button_logout)
    public void facebookLogout(){
        LoginManager.getInstance().logOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }


}
