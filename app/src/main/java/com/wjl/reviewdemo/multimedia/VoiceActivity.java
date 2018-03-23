package com.wjl.reviewdemo.multimedia;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;
import com.wjl.reviewdemo.camera.ToastUtils;

import java.io.File;
import java.io.IOException;

/**
 * author: WuJinLi
 * time  : 2018/3/23
 * desc  :播放音频
 */

public class VoiceActivity extends BaseActivity implements View.OnClickListener {
    Button btn_play, btn_pause, btn_stop, btn_source_style;

    static final int WRITE_EXTERNAL_STOTAGE_CODE = 1;
    MediaPlayer mediaPlayer = new MediaPlayer();

    boolean isPhoneSource = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_voice);
        btn_play = findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);
        btn_stop = findViewById(R.id.btn_stop);
        btn_source_style = findViewById(R.id.btn_source_style);

        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_source_style.setOnClickListener(this);

        if (isPhoneSource) {
            btn_source_style.setText("本地资源");
        } else {
            btn_source_style.setText("asset资源");
        }

        checkPermiession();
//        initVoiceMedia();
    }


    public void checkPermiession() {
        //播放内容路径涉及到外部存储，这需要的是动态授权
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STOTAGE_CODE);
        } else {
            initVoiceMedia();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                break;
            case R.id.btn_pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                break;
            case R.id.btn_stop:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                    initVoiceMedia();
                }
                break;

            case R.id.btn_source_style:
                if (isPhoneSource) {
                    isPhoneSource = false;
                    btn_source_style.setText("asset资源");
                } else {
                    isPhoneSource = true;
                    btn_source_style.setText("本地资源");
                }

                mediaPlayer.reset();
                initVoiceMedia();
                mediaPlayer.start();
                break;
            default:
                break;
        }
    }


    public void initVoiceMedia() {
        if (isPhoneSource) {
            readPhoneSource();
        } else {
            readProjectAsset();
        }
    }


    /**
     * 读取项目asset目录资源
     */
    public void readProjectAsset() {
        try {
            //读取asset资源需要使用到AssetManager对象来获取音频资源
            AssetManager am = getAssets();
            AssetFileDescriptor afd = am.openFd("Ehrling - Champagne Ocean.mp3");
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取sdcard资源
     */
    public void readPhoneSource() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "Blue Stahli - The Pure and the Tainted.mp3");
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_EXTERNAL_STOTAGE_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVoiceMedia();
                } else {
                    ToastUtils.show(this, "权限被拒绝", Toast.LENGTH_SHORT);
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}

