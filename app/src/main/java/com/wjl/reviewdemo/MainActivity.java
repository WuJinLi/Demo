package com.wjl.reviewdemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wjl.reviewdemo.asynctask.AsyncTaskActivity;
import com.wjl.reviewdemo.handler.HandlerActivity;
import com.wjl.reviewdemo.handlerthread.HandlerThreadActivity;
import com.wjl.reviewdemo.runnable.RunnableActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_handler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HandlerActivity.class));
            }
        });

        findViewById(R.id.btn_runnable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RunnableActivity.class));
            }
        });

        findViewById(R.id.btn_asynctask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AsyncTaskActivity.class));
            }
        });


        findViewById(R.id.btn_handlerthread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HandlerThreadActivity.class));
            }
        });
    }
}


