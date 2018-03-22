package com.wjl.reviewdemo.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;

/**
 * author: WuJinLi
 * time  : 2018/3/22
 * desc  :
 */

public class NotificationActivity extends BaseActivity implements View.OnClickListener {
    Button btn_send_notification, btn_send_have_action_notification, btn_send_have_img_notification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_notification);

        btn_send_notification = findViewById(R.id.btn_send_notification);
        btn_send_have_action_notification = findViewById(R.id.btn_send_have_action_notification);
        btn_send_have_img_notification = findViewById(R.id.btn_send_have_img_notification);
        btn_send_notification.setOnClickListener(this);
        btn_send_have_action_notification.setOnClickListener(this);
        btn_send_have_img_notification.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_notification:
                sendNotification();
                break;
            case R.id.btn_send_have_action_notification://跳转到指定页面
                sendNotificationHaveAction();
                break;
            case R.id.btn_send_have_img_notification:
                sendImageNotification();
                break;
            default:
                break;
        }
    }


    //点击notification没有人和操作的
    private void sendNotification() {
        //1.创建NotificaonManager 用来操作notification对象

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //2.创建notification对象，

        Notification notification = new Notification.Builder(this)
                .setContentTitle("this is content title")
                .setContentText("this is notification")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .build();

        //3.通过manager来显示通知
        manager.notify(1, notification);
    }

    //点击notification有操作, 涉及到一个对象PendingIntent，用来携带要执行的操作
    private void sendNotificationHaveAction() {
        Intent intent = new Intent(this, FromNoAcDetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this)
                .setContentTitle("this is title")
                .setContentText("this is a have action notfication")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .build();

        manager.notify(1, notification);
    }


    private void sendImageNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("this is image notification")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setStyle(new Notification.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.img_small)))
                .build();

        manager.notify(2, notification);
    }
}
