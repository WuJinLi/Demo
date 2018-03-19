package com.wjl.reviewdemo.storage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;

/**
 * author: WuJinLi
 * time  : 18/3/19
 * desc  :
 */

public class DataBaseActivity extends BaseActivity implements View.OnClickListener {
    DBHelper dbHelper;

    Button btn_creat_db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_db);
        dbHelper = new DBHelper(this, "BookStore.db", null, 1);

        btn_creat_db=findViewById(R.id.btn_creat_db);

        btn_creat_db.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_creat_db:
                dbHelper.getWritableDatabase();
                break;
        }
    }
}
