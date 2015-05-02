package com.shopping.swb.shopping.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.shopping.swb.shopping.R;

import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-04-29
 * Time: 19:47
 */
public class SearchHistoryAdapter extends DataBaseAdapter<String>{
    private NoDataCallback mCallback;
    public SearchHistoryAdapter(Context context, List<String> list) {
        super(context, list);
    }
    public interface NoDataCallback{
        public  void handleNoData();
    }
    @Override
    public View createView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_search_list,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text_search_history);
            viewHolder.delete = (ImageView) convertView.findViewById(R.id.search_history_delete);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mList.get(position));
        final View finalConvertView = convertView;
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.anim_delete);
                finalConvertView.startAnimation(animation);
                mHandler.sendEmptyMessageDelayed(position,300);
            }
        });
        return convertView;
    }
    static class ViewHolder{
        TextView textView;
        ImageView delete;
    }
    public void setNoDataCallback(NoDataCallback callback){
        this.mCallback = callback;
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mList.remove(msg.what);
            notifyDataSetChanged();
            if(getCount() == 0) {
                mCallback.handleNoData();
            }
        }
    };

}
