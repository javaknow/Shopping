package com.shopping.swb.shopping.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shopping.swb.shopping.R;
import com.shopping.swb.shopping.entity.JKJData;
import com.shopping.swb.shopping.entity.ShouYeGoods;

import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-04
 * Time: 10:58
 */
public class JKJAdapter extends DataBaseAdapter<JKJData>{
    public JKJAdapter(Context context, List<JKJData> list) {
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
            viewHolder.sold = (TextView) convertView.findViewById(R.id.sold);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(mList.get(position).getPic_url(),viewHolder.goodsImg,options);
        viewHolder.goodsTitle.setText(mList.get(position).getTitle());
        viewHolder.goodsPrice.setText(mList.get(position).getNow_price()+"");
        viewHolder.goodsOriginPrice.setText(mList.get(position).getOri_price()+"");
        viewHolder.discount.setText(mList.get(position).getDiscount()+"");
        viewHolder.sold.setText(mList.get(position).getSold_volu());
        return convertView;
    }
    static class ViewHolder{
        ImageView goodsImg;
        TextView goodsTitle;
        TextView goodsPrice;
        TextView goodsOriginPrice;
        TextView discount;
        TextView sold;
    }
}
