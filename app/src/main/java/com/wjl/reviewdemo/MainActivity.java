package com.wjl.reviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.wjl.reviewdemo.model.NavigateModel;
import com.wjl.reviewdemo.multithreading.MultiThreadingActivity;


public class MainActivity extends AppCompatActivity {

    RecyclerView rv_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            default:
                break;
        }
    }

}


