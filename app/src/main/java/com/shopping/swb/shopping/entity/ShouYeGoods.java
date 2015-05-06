package com.shopping.swb.shopping.entity;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-04
 * Time: 10:59
 */
public class ShouYeGoods {
    private String num_iid;
    private String deal_num;
    private String title;
    private String pic_url;
    private double now_price;
    private double origin_price;
    private double discount;

    public ShouYeGoods() {
    }

    public ShouYeGoods(String num_iid, String deal_num,String title, String pic_url, double now_price, double origin_price, double discount) {
        this.num_iid = num_iid;
        this.deal_num = deal_num;
        this.title = title;
        this.pic_url = pic_url;
        this.now_price = now_price;
        this.origin_price = origin_price;
        this.discount = discount;
    }

    public String getNum_iid() {
        return num_iid;
    }

    public void setNum_iid(String num_iid) {
        this.num_iid = num_iid;
    }

    public String getDeal_num() {
        return deal_num;
    }

    public void setDeal_num(String deal_num) {
        this.deal_num = deal_num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public double getNow_price() {
        return now_price;
    }

    public void setNow_price(double now_price) {
        this.now_price = now_price;
    }

    public double getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(double origin_price) {
        this.origin_price = origin_price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }


}
