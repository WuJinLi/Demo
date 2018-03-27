package com.wjl.reviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.wjl.reviewdemo.base.BaseActivity;
import com.wjl.reviewdemo.broadcast.BroadcastActivity;
import com.wjl.reviewdemo.camera.CameraActivity;
import com.wjl.reviewdemo.model.NavigateModel;
import com.wjl.reviewdemo.multimedia.MultiMediaActivity;
import com.wjl.reviewdemo.multithreading.MultiThreadingActivity;
import com.wjl.reviewdemo.network.NetWorkActivity;
import com.wjl.reviewdemo.notification.NotificationActivity;
import com.wjl.reviewdemo.service.ServiceActivity;
import com.wjl.reviewdemo.storage.StorageActivity;

import java.net.URL;


public class MainActivity extends BaseActivity {

    RecyclerView rv_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        rv_main = findViewById(R.id.rv_main);
        ItemManager.init(this, rv_main, ConstantManager.MAINACTIVITYTAG, new ItemManager.GoTagListener() {
            @Override
            public void goTag(NavigateModel navigateModel) {
                goTagAc(navigateModel.getFlag());
            }
        });
    }

    private void goTagAc(int flag) {
        switch (flag) {
            case 1:
                UrlManager.startAc(this, MultiThreadingActivity.class);
                break;
            case 2:
                UrlManager.startAc(this, BroadcastActivity.class);
                break;
            case 3:
                UrlManager.startAc(this, StorageActivity.class);
                break;
            case 4:
                UrlManager.startAc(this, NotificationActivity.class);
                break;
            case 5:
                UrlManager.startAc(this, CameraActivity.class);
                break;
            case 6:
                UrlManager.startAc(this, MultiMediaActivity.class);
                break;
            case 7:
                UrlManager.startAc(this, NetWorkActivity.class);
                break;
            case 8:
                UrlManager.startAc(this, ServiceActivity.class);
                break;
            default:
                break;
        }
    }

}


