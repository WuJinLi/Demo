package com.wjl.reviewdemo.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;

/**
 * author: WuJinLi
 * time  : 2018/3/22
 * desc  :
 */

public class OpenPhotoActivity extends BaseActivity implements View.OnClickListener {
    Button btn_camera;
    ImageView iv_photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_camera);
        iv_photo = findViewById(R.id.iv_photo);
        btn_camera = findViewById(R.id.btn_camera);
        btn_camera.setText("获取照片");

        btn_camera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    getPhoto();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPhoto();
                } else {
                    Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    public void getPhoto(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,2);
    }
}
