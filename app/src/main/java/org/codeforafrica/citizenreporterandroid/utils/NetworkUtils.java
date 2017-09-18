package org.codeforafrica.citizenreporterandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ahereza on 9/18/17.
 */

public class NetworkUtils {
  public static boolean isNetworkAvailable(Context context) {
    ConnectivityManager cm =
        (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    return isConnected;
  }
}
