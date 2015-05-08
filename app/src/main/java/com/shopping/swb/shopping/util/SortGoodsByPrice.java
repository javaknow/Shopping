package com.shopping.swb.shopping.util;

import com.shopping.swb.shopping.entity.Goods;

import java.util.Comparator;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-06
 * Time: 22:15
 */
public class SortGoodsByPrice implements Comparator<Goods>{
    private int mFlag;
    public SortGoodsByPrice(int flag){
        mFlag = flag;
    }
    @Override
    public int compare(Goods g1, Goods g2) {
        if(mFlag == 0) {
            if (g1.getPrice_with_rate() > g2.getPrice_with_rate()) {
                return 1;
            }else if(g1.getPrice_with_rate() == g2.getPrice_with_rate()) {
                return 0;
            }else {
                return -1;
            }
        }else{
            if (g1.getPrice_with_rate() > g2.getPrice_with_rate()) {
                return -1;
            }else if(g1.getPrice_with_rate() == g2.getPrice_with_rate()) {
                return 0;
            }else {
                return 1;
            }
        }
    }
}
