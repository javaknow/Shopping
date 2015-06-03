package com.shopping.swb.shopping.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shopping.swb.shopping.entity.Advertisement;

import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-06-03
 * Time: 09:03
 */
public class AdvertisementAdapter extends PagerAdapter {
    private List<ImageView> mList;
    private Context mContext;
    public AdvertisementAdapter(Context context,List<ImageView> list){
        this.mContext = context;
        this.mList = list;
    }
    @Override
    public int getCount() {
        return mList != null && !mList.isEmpty() ?mList.size():0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
