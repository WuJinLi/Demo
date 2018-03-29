package com.wjl.reviewdemo.baidulocation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.UrlManager;
import com.wjl.reviewdemo.base.BaseActivity;


/**
 * author: WuJinLi
 * time  : 2018/3/29
 * desc  :baidu map activity
 */

public class BaiduMapActivity extends BaseActivity implements View.OnClickListener{
    Button btn_show_map;
    Button btn_get_location_info;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_baidu_map);
        btn_get_location_info=findViewById(R.id.btn_get_location_info);
        btn_show_map=findViewById(R.id.btn_show_map);

        btn_show_map.setOnClickListener(this);
        btn_get_location_info.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_show_map:
                UrlManager.startAc(this, BaiduMapShowActivity.class);
                break;
            case R.id.btn_get_location_info:
                UrlManager.startAc(this, BaiduLocationActivity.class);
                break;
            default:;break;
        }
    }
}
