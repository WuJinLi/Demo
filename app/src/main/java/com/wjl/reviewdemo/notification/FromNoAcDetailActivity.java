package com.wjl.reviewdemo.notification;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;

/**
 * author: WuJinLi
 * time  : 2018/3/22
 * desc  :
 */

public class FromNoAcDetailActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_fromnoac);
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);
    }

    @Override
    public void onClick(View view) {

    }
}
