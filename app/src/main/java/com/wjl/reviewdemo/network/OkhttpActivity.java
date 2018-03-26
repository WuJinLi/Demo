package com.wjl.reviewdemo.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: WuJinLi
 * time  : 2018/3/26
 * desc  :
 */

public class OkhttpActivity extends BaseActivity implements View.OnClickListener {
    Button btn_getdata;
    TextView tv_content;
    static final String URL_DATA = "https://www.baidu.com/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_connention);
        btn_getdata = findViewById(R.id.btn_getdata);
        tv_content = findViewById(R.id.tv_content);

        btn_getdata.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getdata:
                getDataSync();
                break;
            default:
                break;
        }
    }

    public void getDataSync(){
        showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url(URL_DATA)
                            .build();
                    final Response response = okHttpClient.newCall(request).execute();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cancleLoading();
                            tv_content.setText(response.body().toString());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
