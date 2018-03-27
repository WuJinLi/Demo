package com.wjl.reviewdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.wjl.reviewdemo.camera.ToastUtils;

public class BindService extends Service {
    MyBinder myBinder = new MyBinder();

    public BindService() {

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BindService", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("BindService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BindService", "onDestroy");
    }


    class MyBinder extends Binder {
        public void show() {
            Log.d("BindService", "MyBinder extends Binde");
        }
    }
}
