package com.shopping.swb.shopping.entity;

import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-04
 * Time: 14:54
 */
public class RecommendList {
    private List<Recommend> list;

    public RecommendList() {
    }

    public RecommendList(List<Recommend> list) {
        this.list = list;
    }

    public List<Recommend> getList() {
        return list;
    }

    public void setList(List<Recommend> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "GoodsList{" +
                "list=" + list +
                '}';
    }
}
