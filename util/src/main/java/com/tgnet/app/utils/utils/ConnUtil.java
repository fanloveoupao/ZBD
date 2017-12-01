package com.tgnet.app.utils.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by fan-gk on 2017/4/21.
 */


public class ConnUtil {
    public static NetworkInfo getActiveNetworkInfo(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);
        try {
            return connectivityManager.getActiveNetworkInfo();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

