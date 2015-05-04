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
    public GoodsAdapter(Context context, List<Goods> list) {
        super(context, list);
    }

    @Override
    public View createView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_grid,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.goodsImg = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.goodsTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.goodsPrice = (TextView) convertView.findViewById(R.id.price);
            viewHolder.goodsOriginPrice = (TextView) convertView.findViewById(R.id.origin_price);
            viewHolder.discount = (TextView) convertView.findViewById(R.id.discount);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(mList.get(position).getPic_path(),viewHolder.goodsImg,options);
        viewHolder.goodsTitle.setText(mList.get(position).getTitle());
        viewHolder.goodsPrice.setText(mList.get(position).getPrice_with_rate()+"");
        viewHolder.goodsOriginPrice.setText(mList.get(position).getPrice()+"");
        viewHolder.discount.setText(mList.get(position).getDiscount()+"");
        return convertView;
    }
    static class ViewHolder{
        ImageView goodsImg;
        TextView goodsTitle;
        TextView goodsPrice;
        TextView goodsOriginPrice;
        TextView discount;
    }
}
