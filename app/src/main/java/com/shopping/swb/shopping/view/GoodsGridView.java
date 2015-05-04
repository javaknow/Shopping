package com.shopping.swb.shopping.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-04
 * Time: 13:10
 */
public class GoodsGridView extends GridView{
    public GoodsGridView(Context context) {
        super(context);
    }

    public GoodsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GoodsGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
