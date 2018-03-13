package com.wjl.reviewdemo.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wjl.reviewdemo.R;


/**
 * author: WuJinLi
 * time  : 18/3/13
 * desc  :
 */

public class AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_loading, btn_cancle, btn_download;
    ProgressBar pb_progress;
    TextView tv_desc;
    MyTask myTask;
    ImageView iv_img;
    ImageViewAsyncTask imageViewAsyncTask;
    static final String IMAGEURL = "http://f.hiphotos.baidu.com/image/pic/item/c75c10385343fbf2f7da8133bc7eca8065388f2f.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_asynctask);

        myTask = new MyTask();

        btn_loading = findViewById(R.id.btn_loading);
        btn_cancle = findViewById(R.id.btn_cancle);
        btn_download = findViewById(R.id.btn_download);
        pb_progress = findViewById(R.id.pb_progress);
        tv_desc = findViewById(R.id.tv_desc);
        iv_img = findViewById(R.id.iv_img);


        btn_loading.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);
        btn_download.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_loading:
                myTask.execute();
                break;
            case R.id.btn_cancle:
                myTask.onCancelled();
                break;

            case R.id.btn_download:
                iv_img.setImageBitmap(null);
                imageViewAsyncTask = new ImageViewAsyncTask(iv_img);
                imageViewAsyncTask.execute(IMAGEURL);
                break;
            default:
                break;
        }
    }


    class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            tv_desc.setText("LOADING.......");
        }

        @Override
        protected String doInBackground(String... strings) {
            int count = 0;
            int length = 1;

            while (count < 99) {
                count += length;
                publishProgress(count);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pb_progress.setProgress(values[0]);
            tv_desc.setText("loading...." + values[0] + "%");
        }

        @Override
        protected void onPostExecute(String s) {
            tv_desc.setText("加载完毕.....");
        }

        @Override
        protected void onCancelled() {
            tv_desc.setText("已取消");
            pb_progress.setProgress(0);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTask.cancel(true);
    }
}
