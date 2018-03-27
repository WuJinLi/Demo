package com.wjl.reviewdemo.service;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: WuJinLi
 * time  : 2018/3/27
 * desc  :
 */

public class DownLoadTask extends AsyncTask<String, Integer, Integer> {


    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCLED = 3;

    boolean isCancled = false;
    boolean isPaused = false;
    int lastprogress = 0;

    DownLoaderListener listener;

    public DownLoadTask(DownLoaderListener listener) {
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... strings) {
        InputStream is = null;
        RandomAccessFile saveFile = null;
        File file = null;

        try {
            long downloadLength = 0;
            //获取下载地址
            String downloadurl = strings[0];
            //初始化文件（文件的路径）
            String fileName = downloadurl.substring(downloadurl.lastIndexOf("/"));
            String dirctory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
            file = new File(dirctory + fileName);

            if (file.exists()) {
                downloadLength = file.length();
            }

            long contentLength = getContentLength(downloadurl);


            if (contentLength == 0) {
                return TYPE_FAILED;
            } else if (contentLength == downloadLength) {
                return TYPE_SUCCESS;
            }

            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes" + downloadLength + "-")
                    .url(downloadurl)
                    .build();


            Response response = okHttpClient.newCall(request).execute();

            if (response != null) {
                is = response.body().byteStream();
                saveFile = new RandomAccessFile(file, "rw");
                saveFile.seek(downloadLength);//跳过一下在章节

                byte[] bytes = new byte[1024];
                int len;
                int total;
                while ((len = is.read(bytes)) != -1) {
                    if (isCancled) {
                        return TYPE_CANCLED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total = +len;
                        saveFile.write(bytes, 0, total);

                        //计算下载百分比:下载数/下载内容总长度
                        int progress = (int) ((total + downloadLength) * 100 / contentLength);
                        publishProgress(progress);
                    }
                }
            }
            response.close();
            return TYPE_SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (saveFile != null) {
                try {
                    saveFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if (file != null && isCancled) {
                file.delete();
            }
        }

        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastprogress) {
            listener.onProgress(progress);
            lastprogress = progress;
        }
    }

    //获取内容长度
    private long getContentLength(String downloadurl) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadurl)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer) {
            case 0://TYPE_SUCCESS
                listener.onSuccess();
                break;
            case 1://TYPE_FAILED
                listener.onFailed();
                break;
            case 2://TYPE_PAUSED
                listener.onPaused();
                break;
            case 3://TYPE_CANCLED
                listener.onCancled();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }


    public void pauseDownLoad() {
        isPaused = true;
    }

    public void cancleDownLoad() {
        isCancled = true;
    }
}
