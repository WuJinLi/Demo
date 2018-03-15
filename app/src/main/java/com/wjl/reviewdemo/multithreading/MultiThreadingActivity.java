package com.wjl.reviewdemo.multithreading;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wjl.reviewdemo.ConstantManager;
import com.wjl.reviewdemo.ItemManager;
import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.UrlManager;
import com.wjl.reviewdemo.base.BaseActivity;
import com.wjl.reviewdemo.model.NavigateModel;
import com.wjl.reviewdemo.multithreading.asynctask.AsyncTaskActivity;
import com.wjl.reviewdemo.multithreading.handler.HandlerActivity;
import com.wjl.reviewdemo.multithreading.handlerthread.HandlerThreadActivity;
import com.wjl.reviewdemo.multithreading.runnable.RunnableActivity;

/**
 * author: WuJinLi
 * time  : 18/3/15
 * desc  :
 */

public class MultiThreadingActivity extends BaseActivity {

    RecyclerView rv_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_main=findViewById(R.id.rv_main);
        ItemManager.init(this, rv_main, ConstantManager.MULTITHREADINGACTIVITYTAG,new ItemManager.GoTagListener() {
            @Override
            public void goTag(NavigateModel navigateModel) {
                goTagAc(navigateModel.getFlag());
            }
        });
    }

    private void goTagAc(int flag) {
        switch (flag){
            case 1:
                startActivity(new Intent(MultiThreadingActivity.this, HandlerActivity.class));
                break;
            case 2:
                startActivity(new Intent(MultiThreadingActivity.this, RunnableActivity.class));
                break;
            case 3:
                startActivity(new Intent(MultiThreadingActivity.this, AsyncTaskActivity.class));
                break;
            case 4:
                startActivity(new Intent(MultiThreadingActivity.this, HandlerThreadActivity.class));
                break;
            case 5:
                UrlManager.startAc(MultiThreadingActivity.this, UrlManager.THREAD_POOL);
                break;
            default:
                break;

        }
    }
}
