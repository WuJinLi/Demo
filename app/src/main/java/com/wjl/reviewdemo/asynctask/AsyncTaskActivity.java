package com.wjl.reviewdemo.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.UrlManager;
import com.wjl.reviewdemo.base.BaseActivity;


/**
 * author: WuJinLi
 * time  : 18/3/13
 * desc  :AsyncTask使用
 */

public class AsyncTaskActivity extends BaseActivity implements View.OnClickListener {
    Button btn_loading, btn_cancle, btn_download, btn_web;
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
        btn_web = findViewById(R.id.btn_web);
        btn_download = findViewById(R.id.btn_download);
        pb_progress = findViewById(R.id.pb_progress);
        tv_desc = findViewById(R.id.tv_desc);
        iv_img = findViewById(R.id.iv_img);


        btn_loading.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);
        btn_download.setOnClickListener(this);
        btn_web.setOnClickListener(this);

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
                imageViewAsyncTask = new ImageViewAsyncTask(AsyncTaskActivity.this,iv_img);
                imageViewAsyncTask.execute(IMAGEURL);
                break;

            case R.id.btn_web:
                UrlManager.startAc(AsyncTaskActivity.this, UrlManager.ASYNCTASK_URL);
                break;
            default:
                break;
        }
    }


    class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
//            tv_desc.setText("LOADING.......");
            showLoading();
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
            cancleLoading();
//            tv_desc.setText("加载完毕.....");
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


/**
 * AsyncTask是一个已经封装好的轻量级的异步类（抽象类）
 * 实现异步操作进行耗时操作，例如网络数据访问，数据库的访问等等，在进行耗时完成后实现和主线程数据更新
 * ＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
 * public abstract class AsyncTask<Params, Progress, Result> {...}
 * 抽象类三个参数的意义：
 * Params：开始异步传入的参数，对应和excute()方法中传入的参数，这里可以是耗时操作的是需要的参数，也可以是空值，传入的参数是可变参，及传入参数的个数不受限制
 * Progress：在进行耗时操作，会涉及到进度显示，则在此可以传入对应数据类型Integer,如果不涉及到进度的显示，则传入Void(注意与void的区别)，
 * Result: 耗时操作结果类型，与方法doInBackground()方法返回类型一致，例如耗时操作返回类型为图片，及类型为Bitmap
 * ＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
 * 抽象类中的抽象方法介绍：
 * onPreExecute（）:异步执行前的准备工作，例如提示信息，或者执行耗时之前的参数准备等等
 * doInBackground（）:工作线程，进行耗时操作，在此涉及到进度条显示的话，在此调用publishProgress(＊＊)方法来给onProgressUpdate（）传递实时的数据
 * onProgressUpdate（）：进行耗时操作中进度的更新展示操作
 * onPostExecute（）：耗时操作完成后进行UI线程数据更新，该方法的出参类型，就是doInBackground（）方法的返回值类型
 * onCancelled（）：取消异步任务
 * ＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
 * 使用步骤：
 * 1.创建子类（MyTask）继承AsyncTask，实现里边的抽象方法；
 * 2.初始化MyTask：MyTask myTask=new MyTask();
 * 3.启动异步任务: myTask.execute();
 * ＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
 * 使用注意：
 * 1.AsyncTask不与任何控件的生命周期绑定，所以在activity或者fragment中使用，在onDestory()方法中调用cancle(boolean)
 * 2.在使用AsyncTask是需要将其声明为静态的内部类，避免activity销毁时，AsyncTask持有activity的引用造成的内存泄漏
 * 3.在使用时，会出现 java.lang.IllegalStateException: Cannot execute task: the task has already been executed (a task can be executed only once)的异常信息
 * 解决方法：每一次执行异步操作的时候，都必须去重新初始化MyTask对象
 * 4.在涉及activity横竖屏切换过程，容易造成线程丢失或者线程结果丢失的现象，避免这种线程，需要在activity新状态的对应方法中重新启用任务线程
 */
