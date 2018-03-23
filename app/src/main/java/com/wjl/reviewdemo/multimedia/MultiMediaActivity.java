package com.wjl.reviewdemo.multimedia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.UrlManager;
import com.wjl.reviewdemo.base.BaseActivity;

/**
 * author: WuJinLi
 * time  : 2018/3/23
 * desc  :多媒体
 */

public class MultiMediaActivity extends BaseActivity implements View.OnClickListener {

    Button btn_voice, btn_video;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_multimedia);
        btn_voice = findViewById(R.id.btn_voice);
        btn_video = findViewById(R.id.btn_video);

        btn_voice.setOnClickListener(this);
        btn_video.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_voice:
                UrlManager.startAc(this, VoiceActivity.class);
                break;
            case R.id.btn_video:
                UrlManager.startAc(this, VideoActivity.class);
                break;
            default:
                break;
        }
    }
}
