package com.shopping.swb.shopping.entity;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-06-04
 * Time: 22:44
 */
public class JKJData {
    private String title;
    private String sold_volu;
    private String pic_url;
    private double ori_price;
    private double now_price;
    private double discount;
    private String id;

    public JKJData() {
    }

    public JKJData(String title, String sold_volu, String pic_url, double ori_price, double now_price, double discount, String id) {
        this.title = title;
        this.sold_volu = sold_volu;
        this.pic_url = pic_url;
        this.ori_price = ori_price;
        this.now_price = now_price;
        this.discount = discount;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSold_volu() {
        return sold_volu;
    }

    public void setSold_volu(String sold_volu) {
        this.sold_volu = sold_volu;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public double getOri_price() {
        return ori_price;
    }

    public void setOri_price(double ori_price) {
        this.ori_price = ori_price;
    }

    public double getNow_price() {
        return now_price;
    }

    public void setNow_price(double now_price) {
        this.now_price = now_price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JKJData{" +
                "title='" + title + '\'' +
                ", sold_volu='" + sold_volu + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", ori_price=" + ori_price +
                ", now_price=" + now_price +
                ", discount=" + discount +
                ", id='" + id + '\'' +
                '}';
    }
}
