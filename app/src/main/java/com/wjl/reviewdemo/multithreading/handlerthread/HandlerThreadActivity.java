package com.wjl.reviewdemo.multithreading.handlerthread;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.UrlManager;
import com.wjl.reviewdemo.base.BaseActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * author: WuJinLi
 * time  : 18/3/13
 * desc  :HandlerThread使用
 */

public class HandlerThreadActivity extends BaseActivity implements View.OnClickListener {
    Button btn_download, btn_web;
    ImageView iv_photo;
    Handler workHandler, mainHandler;
    static final String IMAGEURL = "http://f.hiphotos.baidu.com/image/pic/item/c75c10385343fbf2f7da8133bc7eca8065388f2f.jpg";
    HandlerThread handlerThread;
    Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_handlerthread);

        btn_download = findViewById(R.id.btn_download);
        btn_web = findViewById(R.id.btn_web);
        iv_photo = findViewById(R.id.iv_photo);

        handlerThread = new HandlerThread("downloadimage");

        mainHandler = new Handler();


        handlerThread.start();


        btn_download.setOnClickListener(this);
        btn_web.setOnClickListener(this);
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
                                    cancleLoading();
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
                showLoading();
                iv_photo.setImageBitmap(null);
                Message msg = workHandler.obtainMessage();
                msg.what = 1;
                workHandler.sendMessage(msg);
                break;
            case R.id.btn_web:
                UrlManager.startAc(HandlerThreadActivity.this, UrlManager.HANDLER_THREAD_URL);
                break;
            default:
                break;
        }
    }
}

/**
 * HandlerThread是一个android已经封装好的异步类,
 * 内部原理：Thread类＋Handler类机制：
 * 1.通过继承Thread类创建一个自带looper的工作线程
 * 2.通过封装的Handler来实现不同线程的信息通信
 * <p>
 * 使用步骤：
 * 1.创建HandlerThread对象
 * 传入参数 = 线程名字，作用 = 标记该线程
 * HandlerThread handlerThread=new HandlerThread("*******");
 * 2.启用HandlerThread
 * handlerThread.start();
 * 3.创建工作线程Handler,执行异步操作
 * Handler workHandler=new Handler(handlerThread.getLooper()){
 * handleMessage(){
 * 处理耗时操作
 * }
 * }
 * <p>
 * 4.线程也启动就绪了，则有消息通知去执行耗时操作，则涉及到发送信息的操作，
 * Message msg=workHandler.obtain();这里发消息的必须是工作线程的handler
 * msg.what,obj,arg1,arg2等参数的初始化（择需使用）
 * workHandler.setMessage(msg)
 * <p>
 * 5.结束线程时候，要停止线程消息的循环
 * handlerThread.quit()
 */
