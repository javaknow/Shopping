package com.shopping.swb.shopping.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.entity.Goods;

import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-04
 * Time: 10:58
 */
public class GoodsAdapter extends DataBaseAdapter<Goods>{
    private boolean mIsShow;

    public void setShow(boolean isShow) {
        mIsShow = isShow;
    }

    public GoodsAdapter(Context context, List<Goods> list) {
        super(context, list);
    }
    @Override
    public View createView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_grid,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.goodsImg = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.goodsTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.goodsPrice = (TextView) convertView.findViewById(R.id.price);
            viewHolder.goodsOriginPrice = (TextView) convertView.findViewById(R.id.origin_price);
            viewHolder.discount = (TextView) convertView.findViewById(R.id.discount);
            viewHolder.sold = (TextView) convertView.findViewById(R.id.sold);
            viewHolder.select = (ImageView) convertView.findViewById(R.id.select);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(mList.get(position).getPic_path(),viewHolder.goodsImg,options);
        viewHolder.goodsTitle.setText(mList.get(position).getTitle());
        viewHolder.goodsPrice.setText(mList.get(position).getPrice_with_rate()+"");
        viewHolder.goodsOriginPrice.setText(mList.get(position).getPrice()+"");
        viewHolder.discount.setText(mList.get(position).getDiscount()+"");
        viewHolder.sold.setText(mList.get(position).getSold());
        if (mIsShow && mList.get(position).isSelect()) {
            viewHolder.select.setVisibility(View.VISIBLE);
            viewHolder.select.setImageResource(R.drawable.icon_selected);
        } else if (mIsShow && !mList.get(position).isSelect()) {
            viewHolder.select.setVisibility(View.VISIBLE);
            viewHolder.select.setImageResource(R.drawable.icon_unselect);
        } else {
            viewHolder.select.setVisibility(View.INVISIBLE);
            mList.get(position).setSelect(false);
        }
        return convertView;
    }
    static class ViewHolder{
        ImageView goodsImg, select;
        TextView goodsTitle;
        TextView goodsPrice;
        TextView goodsOriginPrice;
        TextView discount;
        TextView sold;
    }
}
