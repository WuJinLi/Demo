package com.wjl.reviewdemo.multimedia;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;
import com.wjl.reviewdemo.camera.ToastUtils;

import java.io.File;

/**
 * author: WuJinLi
 * time  : 2018/3/23
 * desc  :
 */

public class VideoActivity extends BaseActivity implements View.OnClickListener {

    Button btn_play, btn_pause, btn_replay;
    VideoView vv_video;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_video);
        btn_play = findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);
        btn_replay = findViewById(R.id.btn_replay);
        vv_video = findViewById(R.id.vv_video);


        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_replay.setOnClickListener(this);

        checkPremiesson();
    }

    private void checkPremiesson() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initVideo();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideo();
                } else {
                    ToastUtils.show(this, "授权失败", 0);
                }
                break;
            default:
                break;
        }
    }


    public void initVideo() {
        File file = new File(Environment.getExternalStorageDirectory(), "video.mp4");
        vv_video.setVideoPath(file.getPath());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (!vv_video.isPlaying()){
                    vv_video.start();
                }
                break;
            case R.id.btn_pause:
                if (vv_video.isPlaying()){
                    vv_video.pause();
                }
                break;
            case R.id.btn_replay:
                if (vv_video.isPlaying()){
                    vv_video.resume();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vv_video!=null){
            vv_video.suspend();
        }
    }
}
