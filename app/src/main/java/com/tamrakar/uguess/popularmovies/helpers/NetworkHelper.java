package com.tamrakar.uguess.popularmovies.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkHelper {

    private static final String LOG_TAG = NetworkHelper.class.getSimpleName();

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Log.d(LOG_TAG, "Not connected to the internet.");
            return false;
        } else {
            if (networkInfo.isConnected()) {
                Log.d(LOG_TAG, "Connected to the internet.");
                return true;
            } else {
                Log.d(LOG_TAG, "Not connected to the internet.");
                return false;
            }
        }
    }
}
