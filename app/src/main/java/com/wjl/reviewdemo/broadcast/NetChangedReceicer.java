package com.wjl.reviewdemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


/**
 * author: WuJinLi
 * time  : 18/3/16
 * desc  :
 */

public class NetChangedReceicer extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "network is not available", Toast.LENGTH_SHORT).show();
        }
    }
}
