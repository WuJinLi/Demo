package com.wjl.reviewdemo.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wjl.reviewdemo.R;

/**
 * author: WuJinLi
 * time  : 18/3/12
 * desc  :
 */

public class HandlerActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_style_1, btn_style_2, btn_style_3;

    TextView tv_show;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    tv_show.setText(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_handler);
        btn_style_1 = findViewById(R.id.btn_style_1);
        btn_style_2 = findViewById(R.id.btn_style_2);
        btn_style_3 = findViewById(R.id.btn_style_3);

        tv_show = findViewById(R.id.tv_show);
        btn_style_1.setOnClickListener(this);
        btn_style_2.setOnClickListener(this);
        btn_style_3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_style_1:
                style_1();
                break;

            case R.id.btn_style_2:
                style_2();
                break;


            case R.id.btn_style_3:
                style_3();
                break;
            default:
                break;
        }
    }


    public void style_1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message message = mHandler.obtainMessage();

                message.what = 1;
                message.obj = "子类继承handler实现";
//                message.obj = "1";
                mHandler.sendMessage(message);
            }
        }).start();
    }


    public void style_2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_show.setText("Handler.post实现");
//                        tv_show.setText("2");
                    }
                });
            }
        }).start();
        ;
    }


    public void style_3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message msg = handler.obtainMessage();
                msg.what = 2;
                msg.obj = "匿名内部类";
//                msg.obj = "3";
                handler.sendMessage(msg);
            }
        }).start();
    }


    private Handler mHandler = new mHandler();

    class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        tv_show.setText(msg.obj.toString());
                        break;
                }
            }
        }
    }

}

/**
 * Handler操作：
 * Handler中涉及到的对象：handler,Message,MessageQueue,looper，MainThread,WorkThread,
 * handler 用于处理消息，负责更新UI
 * Message:负责携带信息，参数有what,arg1,arg2,obj
 * MessageQueue：消息队列，用于接受handler发送来的消息，将接受的msg按照队列的方式进行排列,
 * looper：用于将消息队列中的消息msg传给handler的handleMessage方法
 * WorkThread:用于执行耗时操作，此线程涉及操作的对象有：Message,handler.sendMessage(msg)，new Thread(new Runnable()),new Thread().start();
 * MainThread:主线程更新UI
 * ＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
 * 使用步骤：
 * 1:初始化handler对象（内部类，子类）
 * 2:启用子线程进行耗时操作，new Thread（），
 * 3:耗时操作结束，初始化Message，并将业务数据交由Message携带
 * 4:Message初始化完成，则交由handler对象向主线程（UI线程）发送消息
 * 5:handler的handlMessage（）方法更新UI
 */
