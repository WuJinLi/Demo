package com.wjl.reviewdemo.model;

import android.graphics.Bitmap;

/**
 * author: WuJinLi
 * time  : 18/3/15
 * desc  :
 */

public class NavigateModel {
    public String itemName;
    public int flag;
    public int  imageResouce;

    public NavigateModel() {
    }


    public NavigateModel(String itemName, int flag, int imageResouce) {
        this.itemName = itemName;
        this.flag = flag;
        this.imageResouce = imageResouce;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getImageResouce() {
        return imageResouce;
    }

    public void setImageResouce(int imageResouce) {
        this.imageResouce = imageResouce;
    }
}
