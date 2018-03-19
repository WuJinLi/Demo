package com.wjl.reviewdemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;

/**
 * author: WuJinLi
 * time  : 18/3/15
 * desc  :
 */

public class BroadcastActivity extends BaseActivity implements View.OnClickListener {
    NetWorkChangedReceiver netWorkChangedReceiver;
    Button btn_net_work_status,btn_offline;
    NetChangedReceicer netChangedReceicer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_broadcast);
//        initBroadcast();
        btn_net_work_status = findViewById(R.id.btn_net_work_status);
        btn_offline = findViewById(R.id.btn_offline);


        btn_net_work_status.setOnClickListener(this);
        btn_offline.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_net_work_status:
//                initBroadcast();
                break;
            case R.id.btn_offline:
                offline();
                break;
            default:
                break;
        }
    }

    private void offline() {
        Intent intent=new Intent("com.wjl.FORCE_OFFLINE");
        sendBroadcast(intent);
    }

    private void initBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChangedReceiver = new NetWorkChangedReceiver();
//        netChangedReceicer=new NetChangedReceicer();

        registerReceiver(netWorkChangedReceiver, intentFilter);
    }


    class NetWorkChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "network is not available", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onDestroy() {
        if (netWorkChangedReceiver!=null){
            unregisterReceiver(netWorkChangedReceiver);
            netChangedReceicer=null;
        }

        super.onDestroy();
    }
}
