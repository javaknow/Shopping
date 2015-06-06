package com.shopping.swb.shopping.entity;

import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-06-04
 * Time: 22:43
 */
public class JKJList {
    private int total_page;
    private int now_page;
    private String divide;
    private String order;
    private List<JKJData> list;

    public JKJList() {
    }

    public JKJList(int total_page, int now_page, String divide, String order, List<JKJData> list) {
        this.total_page = total_page;
        this.now_page = now_page;
        this.divide = divide;
        this.order = order;
        this.list = list;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public int getNow_page() {
        return now_page;
    }

    public void setNow_page(int now_page) {
        this.now_page = now_page;
    }

    public String getDivide() {
        return divide;
    }

    public void setDivide(String divide) {
        this.divide = divide;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<JKJData> getList() {
        return list;
    }

    public void setList(List<JKJData> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "JKJList{" +
                "total_page=" + total_page +
                ", now_page=" + now_page +
                ", divide='" + divide + '\'' +
                ", order='" + order + '\'' +
                ", list=" + list +
                '}';
    }
}
