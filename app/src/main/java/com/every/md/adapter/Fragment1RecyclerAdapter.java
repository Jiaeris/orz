package com.every.md.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.every.md.R;

import java.util.ArrayList;

/**
 * Created by Yunga on 2017/8/22.
 */

public class Fragment1RecyclerAdapter extends RecyclerView.Adapter<Fragment1RecyclerAdapter.ViewHolder1> {

    private final static int GENERAL_TYPE = 0X00001;
    private final static int LOAD_MORE_TYPE = 0X00002;

    private ArrayList<String> data;
    private Context context;

    public Fragment1RecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<String> data) {
        this.data = (ArrayList<String>) data.clone();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < data.size()) {
            return GENERAL_TYPE;
        }
        return LOAD_MORE_TYPE;
    }

    @Override
    public Fragment1RecyclerAdapter.ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == GENERAL_TYPE) {
            return new ViewHolder1(LayoutInflater.from(context).inflate(R.layout.adapter_fragment1_recycler, parent, false), GENERAL_TYPE);
        }
        return new ViewHolder1(LayoutInflater.from(context).inflate(R.layout.adapter_fragment1_recycler_load, parent, false), LOAD_MORE_TYPE);
    }

    @Override
    public void onBindViewHolder(Fragment1RecyclerAdapter.ViewHolder1 holder, int position) {
        if (position < data.size()) {
            holder.text_view.setText(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size() + 1;
    }


    public class ViewHolder1 extends RecyclerView.ViewHolder {

        private TextView text_view;

        public ViewHolder1(View itemView, int viewType) {
            super(itemView);
            if (viewType == GENERAL_TYPE) {
                text_view = (TextView) itemView.findViewById(R.id.text_view);
            }
        }
    }


}
