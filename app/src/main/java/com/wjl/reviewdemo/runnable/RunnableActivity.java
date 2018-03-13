package com.wjl.reviewdemo.runnable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wjl.reviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author: WuJinLi
 * time  : 18/3/12
 * desc  :
 */

public class RunnableActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_runnable_normal, btn_runnable_innfer, btn_runnable_1, btn_runnable_2, btn_runnable_3, btn_runnable_cancle;
    TextView tv_example_desc, tv_example_result;

    List<Thread> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_runnable);

        btn_runnable_normal = findViewById(R.id.btn_runnable_normal);
        btn_runnable_innfer = findViewById(R.id.btn_runnable_innfer);
        btn_runnable_1 = findViewById(R.id.btn_runnable_1);
        btn_runnable_2 = findViewById(R.id.btn_runnable_2);
        btn_runnable_3 = findViewById(R.id.btn_runnable_3);
        tv_example_desc = findViewById(R.id.tv_example_desc);
        tv_example_result = findViewById(R.id.tv_example_result);


        btn_runnable_1.setOnClickListener(this);
        btn_runnable_innfer.setOnClickListener(this);
        btn_runnable_2.setOnClickListener(this);
        btn_runnable_3.setOnClickListener(this);
        btn_runnable_normal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_runnable_normal:
                createNormal();
                break;
            case R.id.btn_runnable_innfer:
                createInnfer();
                break;
            case R.id.btn_runnable_1:
                example_one();
                break;
            case R.id.btn_runnable_2:
                example_two();
                break;
            case R.id.btn_runnable_3:
                example_three();
                break;
            default:
                break;
        }
    }

    private void example_three() {
        tv_example_desc.setText("实现2个窗口同时卖火车票；两个窗口一共卖100张，卖票速度均为1s/张");

        MyRunnable1 myRunnable1 = new MyRunnable1();

        Thread thread1 = new Thread(myRunnable1, "窗口1");
        Thread thread2 = new Thread(myRunnable1, "窗口2");


        list.add(thread1);
        list.add(thread2);

        thread1.start();
        thread2.start();
    }

    private void example_two() {
        tv_example_desc.setText("实现2个窗口同时卖火车票；每个窗口卖100张，但卖票速度不同：窗口1是1s/张，窗口2是3s/张");
        MyRunnable1 myRunnable1 = new MyRunnable1();
        MyRunnable2 myRunnable2 = new MyRunnable2();

        Thread thread1 = new Thread(myRunnable1, "窗口1");
        Thread thread2 = new Thread(myRunnable2, "窗口2");


        list.add(thread1);
        list.add(thread2);

        thread1.start();
        thread2.start();
    }

    private void example_one() {
        tv_example_desc.setText("实现2个窗口同时卖火车票；每个窗口卖100张，卖票速度都是1s/张");
        MyRunnable1 runnable1 = new MyRunnable1();
        MyRunnable1 runnable2 = new MyRunnable1();

        Thread thread1 = new Thread(runnable1, "窗口1");
        Thread thread2 = new Thread(runnable2, "窗口2");

        list.add(thread1);
        list.add(thread2);

        thread1.start();
        thread2.start();
    }

    /**
     * 匿名类
     */
    private void createInnfer() {
        tv_example_desc.setText("匿名类实现");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_example_result.setText("匿名类实现");
                    }
                });
            }
        };

        Thread thread = new Thread(runnable);
        list.add(thread);

        thread.start();
    }


    /**
     * 正常写法
     */
    private void createNormal() {
        tv_example_desc.setText("常规写法");
        Thread thread = new Thread(new MyRunnable());
        list.add(thread);
        thread.start();
    }

    /**
     * 常规写法
     */
    class MyRunnable implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_example_result.setText("常规写法");
                }
            });
        }
    }


    class MyRunnable1 implements Runnable {
        int ticket = 100;

        @Override
        public void run() {

            while (ticket > 0) {
                ticket--;
                Log.d("TICKET", Thread.currentThread().getName() + "卖掉了1张票，剩余票数为:" + ticket);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    class MyRunnable2 implements Runnable {
        int ticket = 100;

        @Override
        public void run() {
            while (ticket > 0) {
                ticket--;
                Log.d("TICKET", Thread.currentThread().getName() + "卖掉了1张票，剩余票数为:" + ticket);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
