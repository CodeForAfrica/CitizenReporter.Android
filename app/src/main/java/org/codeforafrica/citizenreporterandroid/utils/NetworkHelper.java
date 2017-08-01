package org.codeforafrica.citizenreporterandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import org.codeforafrica.citizenreporterandroid.auth.LoginActivity;
import org.codeforafrica.citizenreporterandroid.data.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ahereza on 8/1/17.
 */

public class NetworkHelper {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }

    public static boolean checkNetworkPermission(Context context){
        // TODO check network permission
        return true;
    }

    public static void registerUserDetails(
            final Context context, APIInterface apiClient, User user){
        Call<User> userCall = apiClient.createUser(user);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    switch (response.code()) {
                        case 200:
                            Toast.makeText(context, "User Successfully Created",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(context, "Server Error",
                                    Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }



}
