package com.wjl.reviewdemo.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.base.BaseActivity;
import com.wjl.reviewdemo.storage.adapter.DataAdapter;
import com.wjl.reviewdemo.storage.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * author: WuJinLi
 * time  : 18/3/19
 * desc  :
 */

public class DataBaseActivity extends BaseActivity implements View.OnClickListener {
    DBHelper dbHelper;

    Button btn_creat_db, btn_add, btn_update, btn_select, btn_delete;
    SQLiteDatabase sqLiteDatabase;
    int id = 0;

    EditText et_author, et_price, et_pages, et_name;

    String author, name;
    int pages;
    float price;
    List<Book> list = new ArrayList<>();
    DataAdapter adapter;
    ListView lv_data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_db);
        dbHelper = new DBHelper(this, "BookStore.db", null, 1);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        btn_creat_db = findViewById(R.id.btn_creat_db);
        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_select = findViewById(R.id.btn_select);
        btn_delete = findViewById(R.id.btn_delete);
        et_author = findViewById(R.id.et_author);
        et_price = findViewById(R.id.et_price);
        et_pages = findViewById(R.id.et_pages);
        et_name = findViewById(R.id.et_name);
        lv_data = findViewById(R.id.lv_data);

        btn_creat_db.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_select.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        adapter = new DataAdapter(this, list);
        lv_data.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_creat_db:

                break;
            case R.id.btn_add:
                insertData();
                break;
            case R.id.btn_update:
                updateData();
                break;
            case R.id.btn_select:
                showLoading();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (list!=null||list.size()!=0){
                            list.clear();
                        }
                        list = selecteAllData();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cancleLoading();
                                adapter.setList(list);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();
                break;
            case R.id.btn_delete:
                deleteData();
                break;
            default:
                break;
        }
    }


    public void getEditeTextValues() {
        author = et_author.getText().toString();

        if (et_price.getText() == null) {
            price = 0;
        } else if (TextUtils.isEmpty(et_price.getText().toString())) {
            price = 0;
        } else {
            price = Float.parseFloat(et_price.getText().toString());
        }

        if (et_pages.getText() == null) {
            pages = 0;
        } else if (TextUtils.isEmpty(et_pages.getText().toString())) {
            pages = 0;
        } else {
            pages = Integer.parseInt(et_pages.getText().toString());
        }
        pages = Integer.parseInt(et_pages.getText().toString());
        name = et_name.getText().toString();
    }


    /**
     * 添加数据
     */
    public void insertData() {
        getEditeTextValues();
        ContentValues values = new ContentValues();

        values.put("id", id);
        values.put("author", author);
        values.put("price", price);
        values.put("pages", pages);
        values.put("name", name);
        sqLiteDatabase.insert("Book", null, values);
        id++;
        values.clear();

        Toast.makeText(this, "添加完成", Toast.LENGTH_SHORT).show();
    }


    /**
     * 更新数据
     */
    public void updateData() {
        getEditeTextValues();

        ContentValues values = new ContentValues();
        values.put("author", author);
        values.put("price", price);
        values.put("pages", pages);
        values.put("name", name);
        sqLiteDatabase.update("Book", values, "name=?", new String[]{"haha"});
        values.clear();
    }

    /**
     * 删除数据
     */
    public void deleteData() {
        sqLiteDatabase.delete("Book", "name=?", new String[]{"wuwu"});
    }

    /**
     * 查询数据
     */
    public List<Book> selecteAllData() {
        List<Book> list = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query("Book", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                book.setId(cursor.getInt(cursor.getColumnIndex("id")));
                book.setName(cursor.getString(cursor.getColumnIndex("name")));
                book.setPages(cursor.getInt(cursor.getColumnIndex("pages")));
                book.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));

                list.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
