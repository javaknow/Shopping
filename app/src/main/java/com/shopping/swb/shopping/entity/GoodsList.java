package com.shopping.swb.shopping.entity;

import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-04
 * Time: 14:54
 */
public class GoodsList {
    private List<Goods> list;

    public GoodsList() {
    }

    public GoodsList(List<Goods> list) {
        this.list = list;
    }

    public List<Goods> getList() {
        return list;
    }

    public void setList(List<Goods> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "GoodsList{" +
                "list=" + list +
                '}';
    }
}
