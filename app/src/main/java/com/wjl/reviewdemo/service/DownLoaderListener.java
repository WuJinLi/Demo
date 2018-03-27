package com.wjl.reviewdemo.service;

/**
 * author: WuJinLi
 * time  : 2018/3/27
 * desc  :
 */

public interface DownLoaderListener {
    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCancled();
}
