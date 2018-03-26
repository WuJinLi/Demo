package com.wjl.reviewdemo.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.UrlManager;
import com.wjl.reviewdemo.base.BaseActivity;

/**
 * author: WuJinLi
 * time  : 2018/3/26
 * desc  :
 */

public class NetWorkActivity extends BaseActivity implements View.OnClickListener {
    Button tv_httpconnection, btn_okhttp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_network);
        tv_httpconnection = findViewById(R.id.tv_httpconnection);
        btn_okhttp = findViewById(R.id.btn_okhttp);
        tv_httpconnection.setOnClickListener(this);
        btn_okhttp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_httpconnection:
                UrlManager.startAc(this, HttpConnectionActivity.class);
                break;
            case R.id.btn_okhttp:
                UrlManager.startAc(this, OkhttpActivity.class);
                break;
            default:
                break;
        }
    }
}
