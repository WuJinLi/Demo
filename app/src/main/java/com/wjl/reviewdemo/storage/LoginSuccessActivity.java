package com.wjl.reviewdemo.storage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;

/**
 * author: WuJinLi
 * time  : 18/3/19
 * desc  :
 */

public class LoginSuccessActivity extends BaseActivity {

    TextView tv_username, tv_password, tv_no_content;
    SharedPreferences sharedPreferences;
    LinearLayout ll_username, ll_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login_success);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        tv_password = findViewById(R.id.tv_password);
        tv_username = findViewById(R.id.tv_username);
        ll_username = findViewById(R.id.ll_username);
        ll_password = findViewById(R.id.ll_password);
        tv_no_content = findViewById(R.id.tv_no_content);

        if (sharedPreferences.getBoolean(SharedPrefencesActivity.ISREMEMBERKEY, false)) {
            ll_password.setVisibility(View.VISIBLE);
            ll_username.setVisibility(View.VISIBLE);
            tv_no_content.setVisibility(View.GONE);
            tv_username.setText(sharedPreferences.getString(SharedPrefencesActivity.USERNAMEKEY, ""));
            tv_password.setText(sharedPreferences.getString(SharedPrefencesActivity.PASSWORDKEY, ""));
        } else {
            tv_no_content.setVisibility(View.VISIBLE);
            ll_password.setVisibility(View.GONE);
            ll_username.setVisibility(View.GONE);
        }

    }

}
