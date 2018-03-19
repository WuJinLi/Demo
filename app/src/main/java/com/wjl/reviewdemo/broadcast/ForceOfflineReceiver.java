package com.wjl.reviewdemo.broadcast;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.wjl.reviewdemo.MainActivity;
import com.wjl.reviewdemo.base.ActivityCollector;

/**
 * author: WuJinLi
 * time  : 18/3/16
 * desc  :
 */

public class ForceOfflineReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("强制下线")
                .setTitle("提示")
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCollector.finishAll();
                        Intent intent_go_tag = new Intent(context, MainActivity.class);
                        context.startActivity(intent_go_tag);
                    }
                })
                .setCancelable(false);
        builder.show();

    }
}
