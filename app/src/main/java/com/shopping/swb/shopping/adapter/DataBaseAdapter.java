package com.shopping.swb.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-04-29
 * Time: 19:48
 */
public abstract class DataBaseAdapter<T> extends BaseAdapter{
    public List<T> mList;
    public Context mContext;
    public LayoutInflater mInflater;
    public DataBaseAdapter(Context context,List<T> list){
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList!=null&&!mList.isEmpty()?mList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position,convertView,parent);
    }
    public abstract View createView(int position, View convertView, ViewGroup parent);
}
