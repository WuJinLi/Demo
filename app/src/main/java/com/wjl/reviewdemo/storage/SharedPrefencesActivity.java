package com.wjl.reviewdemo.storage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.UrlManager;
import com.wjl.reviewdemo.base.BaseActivity;

/**
 * author: WuJinLi
 * time  : 18/3/19
 * desc  :
 */

public class SharedPrefencesActivity extends BaseActivity implements View.OnClickListener {

    EditText et_username, et_password;
    CheckBox cb_rember_pwd;
    Button btn_login;
    static final String USERNAMEKEY = "username";
    static final String PASSWORDKEY = "password";
    static final String ISREMEMBERKEY = "isremember";

    SharedPreferences sharedPreferences;
    boolean isremember;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        cb_rember_pwd = findViewById(R.id.cb_rember_pwd);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);


//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        isremember = sharedPreferences.getBoolean(SharedPrefencesActivity.ISREMEMBERKEY, false);
        if (isremember) {
            //将帐号密码都显示在文本框
            et_username.setText(sharedPreferences.getString(SharedPrefencesActivity.USERNAMEKEY, ""));
            et_password.setText(sharedPreferences.getString(SharedPrefencesActivity.PASSWORDKEY, ""));
            et_password.setSelection(sharedPreferences.getString(SharedPrefencesActivity.PASSWORDKEY, "").length());
            et_username.setSelection(sharedPreferences.getString(SharedPrefencesActivity.USERNAMEKEY, "").length());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                SharedPreferences.Editor edit = sharedPreferences.edit();
                if (checkInput()) {
                    if (cb_rember_pwd.isChecked()) {
                        edit.putBoolean(SharedPrefencesActivity.ISREMEMBERKEY, true);
                        edit.putString(SharedPrefencesActivity.USERNAMEKEY, et_username.getText().toString());
                        edit.putString(SharedPrefencesActivity.PASSWORDKEY, et_password.getText().toString());
                    } else {
                        edit.clear();
                    }
                    edit.apply();
                    UrlManager.startAc(SharedPrefencesActivity.this, LoginSuccessActivity.class);
                }
                break;
            default:
                break;
        }
    }


    public boolean checkInput() {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}