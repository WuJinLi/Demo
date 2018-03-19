package com.wjl.reviewdemo.storage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.wjl.reviewdemo.ConstantManager;
import com.wjl.reviewdemo.ItemManager;
import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.UrlManager;
import com.wjl.reviewdemo.base.BaseActivity;
import com.wjl.reviewdemo.model.NavigateModel;


/**
 * author: WuJinLi
 * time  : 18/3/19
 * desc  :
 */

public class StorageActivity extends BaseActivity {
    RecyclerView rv_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        rv_main = findViewById(R.id.rv_main);

        ItemManager.init(this, rv_main, ConstantManager.STORAGEACTIVITYTAG, new ItemManager.GoTagListener() {
            @Override
            public void goTag(NavigateModel navigateModel) {
                goTagAc(navigateModel.getFlag());
            }
        });
    }

    private void goTagAc(int flag) {
        switch (flag) {
            case 1:
                UrlManager.startAc(StorageActivity.this, FileSaveActivity.class);
                break;
            case 2:
                UrlManager.startAc(StorageActivity.this, SharedPrefencesActivity.class);
                break;
            case 3:
                UrlManager.startAc(StorageActivity.this, DataBaseActivity.class);
                break;
            default:
                break;
        }
    }
}
