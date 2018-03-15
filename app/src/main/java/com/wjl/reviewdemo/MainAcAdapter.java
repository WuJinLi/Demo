package com.wjl.reviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wjl.reviewdemo.model.NavigateModel;

import java.util.List;

/**
 * author: WuJinLi
 * time  : 18/3/15
 * desc  :
 */

public class MainAcAdapter extends RecyclerView.Adapter<MainAcAdapter.MyViewHolder> {

    private Context context;
    private List<NavigateModel> list;
    private LayoutInflater inflater;

    public MainAcAdapter(Context context, List<NavigateModel> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.item_main_rv, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.iv_img_item.setImageResource(list.get(position).getImageResouce());
        holder.tv_desc_item.setText(list.get(position).getItemName());

        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img_item;
        TextView tv_desc_item;
        LinearLayout ll_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_img_item = itemView.findViewById(R.id.iv_img_item);
            tv_desc_item = itemView.findViewById(R.id.tv_desc_item);
            ll_item = itemView.findViewById(R.id.ll_item);
        }
    }



    RvItemOnClickListener listener;


    public void setOnClickListener(RvItemOnClickListener listener){
        this.listener=listener;
    }

    interface RvItemOnClickListener {
        void onClick(NavigateModel navigateModel);
    }
}
