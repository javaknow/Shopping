package com.shopping.swb.shopping.util;


import com.shopping.swb.shopping.entity.Goods;

import java.util.Comparator;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-06
 * Time: 22:00
 */
public class SortGoodsBySold implements Comparator {
    @Override
    public int compare(Object obj1, Object obj2) {
        Goods g1 = (Goods) obj1;
        Goods g2 = (Goods) obj2;
        String str1 = g1.getSold();
        String str2 = g2.getSold();
        double sold1 = 0;
        double sold2 = 0;
        if (str1.contains("万")) {
            str1 = str1.substring(0, str1.length() - 1);
            sold1 = Double.valueOf(str1)*10000;
        } else {
            sold1 = Double.valueOf(str1);
        }
        if (str2.contains("万")) {
            str2 = str2.substring(0, str2.length() - 1);
            sold2 = Double.valueOf(str2)*10000;
        } else {
            sold2 = Double.valueOf(str2);
        }
        if (sold1 > sold2) {
            return -1;
        }
        return 1;

    }
}
