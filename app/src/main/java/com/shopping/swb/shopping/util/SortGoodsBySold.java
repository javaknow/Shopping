package com.shopping.swb.shopping.util;

import com.shopping.swb.shopping.entity.Goods;

import java.util.Comparator;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-06
 * Time: 22:00
 */
public class SortGoodsBySold implements Comparator{
    @Override
    public int compare(Object obj1, Object obj2) {
        Goods g1 = (Goods) obj1;
        Goods g2 = (Goods) obj2;
        if(Integer.valueOf(g1.getSold()) > Integer.valueOf(g2.getSold())){
            return -1;
        }
        return 1;
    }
}
