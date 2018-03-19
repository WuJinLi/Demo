package com.wjl.reviewdemo.base;

import android.app.Dialog;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.broadcast.ForceOfflineReceiver;

/**
 * author: WuJinLi
 * time  : 18/3/14
 * desc  :
 */

public class BaseActivity extends AppCompatActivity implements IBaseView {
    public Dialog progressDialog;
    ForceOfflineReceiver receiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.AddActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new ForceOfflineReceiver();

        IntentFilter intentFilter = new IntentFilter("com.wjl.FORCE_OFFLINE");
        registerReceiver(receiver, intentFilter);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;
        }
    }

    @Override
    public void showLoading() {
        String msg = "";
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        if (!(progressDialog != null)) {
            progressDialog = new Dialog(this, R.style.progress_dialog);
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            if (progressDialog.getWindow() != null) {
                progressDialog.getWindow().setBackgroundDrawableResource(
                        R.drawable.trans50bg);
            }
            TextView message = (TextView) progressDialog
                    .findViewById(R.id.id_tv_loadingmsg);
            if (msg.isEmpty()) {
                message.setVisibility(View.GONE);
            } else {
                message.setVisibility(View.VISIBLE);
                message.setText(msg);
            }
        }
        try {
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancleLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    @Override
    protected void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }
}
