package com.tgnet.app.utils.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tgnet.app.utils.utils.ConnUtil;
import com.tgnet.events.ConnectionStateChangedEvent;
import com.tgnet.events.EventBusUtil;

/**
 * Created by fan-gk on 2017/4/21.
 */

public class SystemReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case ConnectivityManager.CONNECTIVITY_ACTION:
                onConnectivityAction(context);
                break;
        }
    }

    private void onConnectivityAction(Context context) {
        NetworkInfo networkInfo = ConnUtil.getActiveNetworkInfo(context);
        boolean isConnected = false;
        int type = -1;
        if (networkInfo != null) {
            isConnected = networkInfo.isConnected();
            type = networkInfo.getType();
        }
        EventBusUtil.getInstance().post(new ConnectionStateChangedEvent(type, isConnected));
    }
}

