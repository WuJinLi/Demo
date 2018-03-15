package com.wjl.reviewdemo;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wjl.reviewdemo.model.NavigateModel;

import java.util.ArrayList;
import java.util.List;


/**
 * author: WuJinLi
 * time  : 18/3/15
 * desc  :
 */

public class ItemManager {

    public static List<NavigateModel> initData(String flag) {
        if (ConstantManager.MAINACTIVITYTAG.equals(flag)) {
            return getMainData();
        } else if (ConstantManager.MULTITHREADINGACTIVITYTAG.equals(flag)) {
            return getMultiThreadingData();
        }
        return null;
    }


    public static void init(Context context, RecyclerView rv_main, String flag, final GoTagListener listener) {
        List<NavigateModel> list = new ArrayList<>();
        list = ItemManager.initData(flag);
        MainAcAdapter adapter = new MainAcAdapter(context, list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3);
        rv_main.setLayoutManager(layoutManager);
        rv_main.setItemAnimator(new DefaultItemAnimator());
        rv_main.setAdapter(adapter);


        adapter.setOnClickListener(new MainAcAdapter.RvItemOnClickListener() {
            @Override
            public void onClick(NavigateModel navigateModel) {
                listener.goTag(navigateModel);
            }
        });
    }


    public interface GoTagListener {
        void goTag(NavigateModel navigateModel);
    }


    public static List<NavigateModel> getMainData() {
        List<NavigateModel> list = new ArrayList<>();

        list.add(new NavigateModel("多线程", 1, R.mipmap.ic_launcher));
        return list;
    }


    public static List<NavigateModel> getMultiThreadingData() {
        List<NavigateModel> list = new ArrayList<>();

        list.add(new NavigateModel("Handler执行", 1, R.mipmap.ic_launcher));
        list.add(new NavigateModel("Runnable执行", 2, R.mipmap.ic_launcher));
        list.add(new NavigateModel("AsyncTask执行", 3, R.mipmap.ic_launcher));
        list.add(new NavigateModel("HandlerThread执行", 4, R.mipmap.ic_launcher));
        list.add(new NavigateModel("ThreadPool执行", 5, R.mipmap.ic_launcher));
        return list;
    }
}
