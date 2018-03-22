package com.wjl.reviewdemo.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * author: WuJinLi
 * time  : 2018/3/22
 * desc  :
 */

public class CameraActivity extends BaseActivity implements View.OnClickListener {

    Button btn_camera;
    ImageView iv_photo;

    Uri imageUri;
    static final int TAKE_PHOTO = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_camera);
        iv_photo = findViewById(R.id.iv_photo);
        btn_camera = findViewById(R.id.btn_camera);

        btn_camera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
                takePhotoFromCamera();
                break;
            default:
                break;
        }
    }

    private void takePhotoFromCamera() {
        //创建文件用来存储照相机拍摄的照片，getExternalCacheDir代表的是该应用的缓存目录
        File output_image = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "output_image.tmp");


        //判断文件是否存在，不存在则创建，存在则删除再创建
        try {
            if (output_image.exists()) {
                output_image.delete();
            }
            output_image.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

//        if (Build.VERSION.SDK_INT >= 24) {
//            imageUri = FileProvider.getUriForFile(this, "com.wjl.reviewdemo.provider", output_image);
//        } else {
        imageUri = Uri.fromFile(output_image);
//        }

        //启动相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        iv_photo.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
