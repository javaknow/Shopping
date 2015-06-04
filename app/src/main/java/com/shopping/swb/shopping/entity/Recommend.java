package com.shopping.swb.shopping.entity;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-04
 * Time: 14:50
 */
public class Recommend {
    private String title;
    private String sold_volu;
    private String pic_url;
    private double o_price;
    private double n_price;
    private double discount;
    private String id;

    public Recommend() {
    }

    public Recommend(String title, String sold_volu, String pic_url, double o_price, double n_price, double discount, String id) {
        this.title = title;
        this.sold_volu = sold_volu;
        this.pic_url = pic_url;
        this.o_price = o_price;
        this.n_price = n_price;
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

    public double getO_price() {
        return o_price;
    }

    public void setO_price(double o_price) {
        this.o_price = o_price;
    }

    public double getN_price() {
        return n_price;
    }

    public void setN_price(double n_price) {
        this.n_price = n_price;
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
        return "Recommend{" +
                "title='" + title + '\'' +
                ", sold='" + sold_volu + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", o_price=" + o_price +
                ", n_price=" + n_price +
                ", discount=" + discount +
                ", id='" + id + '\'' +
                '}';
    }
}
