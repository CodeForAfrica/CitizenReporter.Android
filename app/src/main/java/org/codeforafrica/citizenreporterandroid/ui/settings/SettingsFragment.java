package org.codeforafrica.citizenreporterandroid.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.parse.ParseUser;
import javax.inject.Inject;
import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.SupportChannelActivity;
import org.codeforafrica.citizenreporterandroid.app.CitizenReporterApplication;
import org.codeforafrica.citizenreporterandroid.data.DataManager;
import org.codeforafrica.citizenreporterandroid.ui.auth.LoginActivity;
import org.codeforafrica.citizenreporterandroid.ui.about.AboutActivity;
import org.codeforafrica.citizenreporterandroid.ui.feedback.FeedbackActivity;

public class SettingsFragment extends Fragment {
  private Button fbLogoutBtn;
  private Button googleSignOut;
  @Inject DataManager manager;

  public SettingsFragment() {
    // Required empty public constructor
  }

  // TODO: Rename and change types and number of parameters
  public static SettingsFragment newInstance() {
    SettingsFragment fragment = new SettingsFragment();
    Bundle args = new Bundle();
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((CitizenReporterApplication) getActivity().getApplication()).getAppComponent().inject(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    AccessToken token = AccessToken.getCurrentAccessToken();

    View view = inflater.inflate(R.layout.fragment_settings, container, false);
    fbLogoutBtn = (Button) view.findViewById(R.id.button_logout);
    if (token == null) {
      googleSignOut.setVisibility(View.VISIBLE);
    } else {
      fbLogoutBtn.setVisibility(View.VISIBLE);
    }

    ButterKnife.bind(this, view);
    return view;
  }

  //@OnClick(R.id.google_sign_out) public void googleSignOut() {
  //  manager.setUserLoggedOut();
  //  startActivity(new Intent(getActivity(), LoginActivity.class));
  //  getActivity().finish();
  //
  //}

  @OnClick(R.id.support_menu_item) public void startSupportChannelActivity() {
    startActivity(new Intent(getActivity(), SupportChannelActivity.class));
  }

  @OnClick(R.id.about_menu_item) public void startAboutActivity() {
    startActivity(new Intent(getActivity(), AboutActivity.class));
  }

  @OnClick(R.id.feedback_menu_item) public void startFeedbackActivity() {
    startActivity(new Intent(getActivity(), FeedbackActivity.class));
  }

  @OnClick(R.id.button_logout) public void facebookLogout() {
    LoginManager.getInstance().logOut();
    manager.setUserLoggedOut();
    ParseUser.logOut();
    startActivity(new Intent(getActivity(), LoginActivity.class));
    getActivity().finish();
  }
}
