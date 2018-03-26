package com.wjl.reviewdemo.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * author: WuJinLi
 * time  : 2018/3/26
 * desc  :
 */

public class HttpConnectionActivity extends BaseActivity implements View.OnClickListener {

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
                getDataFromNet();
                break;
            default:
                break;
        }
    }

    public void getDataFromNet() {
        showLoading();
        //由于在主线程进行耗时操作容易出现anr，则新起线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader reader = null;
                HttpURLConnection httpURLConnection = null;
                try {

                    //httpURLConnection对象的初始化和相关网络请求属性的设置
                    URL url = new URL(URL_DATA);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);

                    //获取网络请求数据，将数据从流转换成字符串
                    InputStream inputStream = httpURLConnection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    showData(builder.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showData(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cancleLoading();
                tv_content.setText(s);
            }
        });
    }
}
