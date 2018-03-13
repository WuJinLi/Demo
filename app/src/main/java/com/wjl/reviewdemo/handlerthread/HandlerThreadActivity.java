package com.wjl.reviewdemo.handlerthread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wjl.reviewdemo.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * author: WuJinLi
 * time  : 18/3/13
 * desc  :
 */

public class HandlerThreadActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_download;
    ImageView iv_photo;
    Handler workHandler,mainHandler;
    static final String IMAGEURL = "http://f.hiphotos.baidu.com/image/pic/item/c75c10385343fbf2f7da8133bc7eca8065388f2f.jpg";
    HandlerThread handlerThread;
    Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_handlerthread);

        btn_download = findViewById(R.id.btn_download);
        iv_photo = findViewById(R.id.iv_photo);

        handlerThread = new HandlerThread("downloadimage");

        mainHandler=new Handler();


        handlerThread.start();


        workHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        try {
                            URL url = new URL(IMAGEURL);
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());


                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    iv_photo.setImageBitmap(bitmap);
                                }
                            });

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                iv_photo.setImageBitmap(null);
                Message msg = workHandler.obtainMessage();
                msg.what = 1;
                workHandler.sendMessage(msg);
                break;
            default:
                break;
        }
    }
}
