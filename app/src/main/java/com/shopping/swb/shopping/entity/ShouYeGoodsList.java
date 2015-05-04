package com.shopping.swb.shopping.entity;

import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-04
 * Time: 11:41
 */
public class ShouYeGoodsList {
    private List<ShouYeGoods> list;

    public ShouYeGoodsList() {
    }

    public ShouYeGoodsList(List<ShouYeGoods> list) {
        this.list = list;
    }

    public List<ShouYeGoods> getList() {
        return list;
    }

    public void setList(List<ShouYeGoods> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "GoodsList{" +
                "list=" + list +
                '}';
    }
}
