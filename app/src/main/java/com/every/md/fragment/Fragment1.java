package com.every.md.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.every.md.R;
import com.every.md.adapter.Fragment1RecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by Yunga on 2017/8/10.
 */

public class Fragment1 extends BaseFragment {
    private ArrayList<String> data;
    private SwipeRefreshLayout refresh_view;
    private Fragment1RecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        data = new ArrayList<>();

        refresh_view = (SwipeRefreshLayout) view.findViewById(R.id.refresh_view);
        refresh_view.setOnRefreshListener(refreshListener);
        adapter = new Fragment1RecyclerAdapter(getActivity());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        data.add("hahahaha");
        data.add("1111111111111");
        data.add("333333333333333333333");

        adapter.setData(data);

        return view;
    }

    @Override
    public String getTitle() {
        return "标题1";
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            data.clear();
            data.add(getResources().getString(R.string.test));
            adapter.setData(data);
            refresh_view.setRefreshing(false);
        }
    };

}
