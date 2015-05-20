package com.shopping.swb.shopping.entity;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-04
 * Time: 14:50
 */
public class Goods {
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    private String title;
    private String sold;
    private String pic_path;
    private double price;
    private double price_with_rate;
    private double discount;
    private String item_id;

    public Goods() {
    }

    public Goods(String title, String sold, double price, String pic_path, double price_with_rate, double discount, String item_id) {
        this.title = title;
        this.sold = sold;
        this.price = price;
        this.pic_path = pic_path;
        this.price_with_rate = price_with_rate;
        this.discount = discount;
        this.item_id = item_id;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }

    public double getPrice_with_rate() {
        return price_with_rate;
    }

    public void setPrice_with_rate(double price_with_rate) {
        this.price_with_rate = price_with_rate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "title='" + title + '\'' +
                ", sold='" + sold + '\'' +
                ", pic_path='" + pic_path + '\'' +
                ", price=" + price +
                ", price_with_rate=" + price_with_rate +
                ", discount=" + discount +
                ", item_id='" + item_id + '\'' +
                '}';
    }
}
