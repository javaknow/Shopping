package com.shopping.swb.shopping.entity;

import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-04
 * Time: 14:54
 */
public class ForenoticeList {
    private List<Forenotice> list;

    public ForenoticeList() {
    }

    public ForenoticeList(List<Forenotice> list) {
        this.list = list;
    }

    public List<Forenotice> getList() {
        return list;
    }

    public void setList(List<Forenotice> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "GoodsList{" +
                "list=" + list +
                '}';
    }
}
