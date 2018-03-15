package com.wjl.reviewdemo;

import android.content.Context;
import android.content.Intent;

import com.wjl.reviewdemo.web.WebDetailActivity;

/**
 * author: WuJinLi
 * time  : 18/3/14
 * desc  :
 */

public class UrlManager {

    public static final String HANDLER_URL = "https://www.jianshu.com/p/e172a2d58905";
    public static final String HANDLER_THREAD_URL = "https://www.jianshu.com/p/9c10beaa1c95";
    public static final String RUNNABLE_URL = "https://www.jianshu.com/p/95b186fbf192";
    public static final String ASYNCTASK_URL = "https://www.jianshu.com/p/ee1342fcf5e7";
    public static final String THREAD_POOL="https://www.jianshu.com/p/0e4a5e70bf0e";


    public static void startAc(Context context, String url) {
        Intent intent = new Intent(context, WebDetailActivity.class);
        intent.putExtra("WEB_URL", url);
        context.startActivity(intent);
    }
}
