package com.wjl.reviewdemo.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.wjl.reviewdemo.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * author: WuJinLi
 * time  : 18/3/13
 * desc  :
 */

public class ImageViewAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    private BaseActivity activity;

    public ImageViewAsyncTask(BaseActivity activity,ImageView imageView) {
        this.imageView = imageView;
        this.activity=activity;
    }

    @Override
    protected void onPreExecute() {
        activity.showLoading();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        activity.cancleLoading();
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
