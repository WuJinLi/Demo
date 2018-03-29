package com.wjl.reviewdemo.baidulocation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureMapView;
import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;

/**
 * author: WuJinLi
 * time  : 2018/3/29
 * desc  :
 */

public class BaiduMapShowActivity extends BaseActivity {
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_baidu_map_show);


        mMapView = (TextureMapView) findViewById(R.id.mTexturemap);
        mBaiduMap = mMapView.getMap();
    }
}
