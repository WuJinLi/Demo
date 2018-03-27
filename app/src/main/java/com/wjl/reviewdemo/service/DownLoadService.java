package com.wjl.reviewdemo.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.wjl.reviewdemo.MainActivity;
import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.camera.ToastUtils;

import java.io.File;

/**
 * author: WuJinLi
 * time  : 2018/3/27
 * desc  :
 */

public class DownLoadService extends Service {
    String downloadUrl;
    DownLoadTask downLoadTask;
    DownLoadBinder downLoadBinder = new DownLoadBinder();


    DownLoaderListener listener = new DownLoaderListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("Downloading...", progress));
        }

        @Override
        public void onSuccess() {
            downLoadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download success", -1));
        }

        @Override
        public void onFailed() {
            downLoadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Downloading failed", -1));
            ToastUtils.show(DownLoadService.this, "Downloading failed", 0);
        }

        @Override
        public void onPaused() {
            downLoadTask = null;
            ToastUtils.show(DownLoadService.this, "Downloading pause", 0);
        }

        @Override
        public void onCancled() {
            downLoadTask = null;
            ToastUtils.show(DownLoadService.this, "Downloading cancled", 0);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return downLoadBinder;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    class DownLoadBinder extends Binder {
        public void startDownload(String url) {
            if (downLoadTask == null) {
                downloadUrl = url;
                downLoadTask = new DownLoadTask(listener);
                downLoadTask.execute(downloadUrl);
                startForeground(1, getNotification("Downloading.....", 0));
                ToastUtils.show(DownLoadService.this, "Downloading...", 0);
            }

        }


        public void downLoadPause() {
            if (downLoadTask != null) {
                downLoadTask.pauseDownLoad();
            }
        }


        public void cancleDownLoad() {
            if (downLoadTask != null) {
                downLoadTask.cancleDownLoad();
            } else {
                if (downloadUrl != null) {
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String drictory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(drictory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }

                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    ToastUtils.show(DownLoadService.this, "Downloading is cancled......", 0);
                }
            }
        }

    }


    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }


    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentIntent(pi);
        if (progress > 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(0, progress, false);
        }

        return builder.build();
    }
}
