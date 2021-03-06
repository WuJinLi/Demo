package com.wjl.reviewdemo.service;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;
import com.wjl.reviewdemo.camera.ToastUtils;

/**
 * author: WuJinLi
 * time  : 2018/3/26
 * desc  :
 */

public class ServiceActivity extends BaseActivity implements View.OnClickListener {
    Button btn_start_service, btn_stop_service, btn_bind_service, btn_unbind_service, btn_intent_service, btn_start_down, btn_pause_down, btn_cancle_down;
    BindService.MyBinder myBinder;
    static final String DOWNLOADURL = "https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";

    DownLoadService.DownLoadBinder downLoadBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_service);
        btn_start_service = findViewById(R.id.btn_start_service);
        btn_stop_service = findViewById(R.id.btn_stop_service);
        btn_bind_service = findViewById(R.id.btn_bind_service);
        btn_unbind_service = findViewById(R.id.btn_unbind_service);
        btn_intent_service = findViewById(R.id.btn_intent_service);
        btn_start_down = findViewById(R.id.btn_start_down);
        btn_pause_down = findViewById(R.id.btn_pause_down);
        btn_cancle_down = findViewById(R.id.btn_cancle_down);

        btn_stop_service.setOnClickListener(this);
        btn_start_service.setOnClickListener(this);
        btn_bind_service.setOnClickListener(this);
        btn_unbind_service.setOnClickListener(this);
        btn_intent_service.setOnClickListener(this);
        btn_start_down.setOnClickListener(this);
        btn_pause_down.setOnClickListener(this);
        btn_cancle_down.setOnClickListener(this);


        Intent intent = new Intent(ServiceActivity.this, DownLoadService.class);
        bindService(intent, downConn, BIND_AUTO_CREATE);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ToastUtils.show(this, "拒绝授权", 0);
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
                break;
            case R.id.btn_stop_service:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;
            case R.id.btn_bind_service:
                Intent bindIntent = new Intent(this, BindService.class);
                bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service:
                unbindService(serviceConnection);
                break;
            case R.id.btn_intent_service:
                Log.d("ServiceActivity", "thread id is" + Thread.currentThread().getId());
                Intent intent_service = new Intent(this, MyIntentService.class);
                startService(intent_service);
                break;

            case R.id.btn_start_down:
                downLoadBinder.startDownload(DOWNLOADURL);
                break;
            case R.id.btn_pause_down:
                downLoadBinder.downLoadPause();
                break;
            case R.id.btn_cancle_down:
                downLoadBinder.cancleDownLoad();
                break;
            default:
                break;
        }
    }


    ServiceConnection serviceConnection = new ServiceConnection() {
        //服务链连接时指定的操作，涉及到service和activity之间的通信
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (BindService.MyBinder) service;
            myBinder.show();
        }

        //服务和活动断开连接时执行的操作，例如释放一些不用的资源等等
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    ServiceConnection downConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downLoadBinder = (DownLoadService.DownLoadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
        unbindService(downConn);
    }
}



