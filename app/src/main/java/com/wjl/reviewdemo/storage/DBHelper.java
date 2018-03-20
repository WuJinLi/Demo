package com.wjl.reviewdemo.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * author: WuJinLi
 * time  : 18/3/19
 * desc  :
 */

public class DBHelper extends SQLiteOpenHelper {
    //创建表Book的SQL语句
    public static final String CREATE_BOOK = "create table Book(id integer primary key autoincrement,author text,price real,pages integer,name text)";
    //创建表Categroy的SQL语句
    public static final String CREATE_CATEGROY = "create table Categroy(id integer primary key autoincrement,categroy_name text,categroy_code integer)";
    public Context mContext;


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建表的操作
        sqLiteDatabase.execSQL(CREATE_BOOK);
        sqLiteDatabase.execSQL(CREATE_CATEGROY);
        Toast.makeText(mContext, "create table successful", Toast.LENGTH_SHORT).show();
    }


    /**
     * 调用此方法的前提是，在初始化Helper是构造方法中version大于之前的vesion
     *
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //更新数据库，在数据库与存在下，将之前的删除
        sqLiteDatabase.execSQL("drop table if exists Book");
        sqLiteDatabase.execSQL("drop table if exists Categroy");

        onCreate(sqLiteDatabase);
    }
}
