package com.wjl.reviewdemo.storage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wjl.reviewdemo.R;
import com.wjl.reviewdemo.storage.model.Book;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * author: WuJinLi
 * time  : 18/3/21
 * desc  :
 */

public class DataAdapter extends BaseAdapter {
    List<Book> list;
    Context context;
    LayoutInflater inflater;

    public DataAdapter(Context context, List<Book> list) {
        this.list = list;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(List<Book> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_data_listview, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Book book = list.get(i);

        holder.tv_id.setText(book.getId() + "");
        holder.tv_name.setText(book.getName());
        holder.tv_author.setText(book.getAuthor());
        holder.tv_pages.setText(book.getPages() + "");
        holder.tv_price.setText(book.getPrice() + "");

        return view;
    }


    class ViewHolder {
        TextView tv_id, tv_name, tv_author, tv_pages, tv_price;

        public ViewHolder(View view) {
            tv_id = view.findViewById(R.id.tv_id);
            tv_name = view.findViewById(R.id.tv_name);
            tv_author = view.findViewById(R.id.tv_author);
            tv_pages = view.findViewById(R.id.tv_pages);
            tv_price = view.findViewById(R.id.tv_price);
        }
    }
}
