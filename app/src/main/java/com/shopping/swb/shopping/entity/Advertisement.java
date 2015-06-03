package com.shopping.swb.shopping.entity;

import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-06-02
 * Time: 23:51
 */
public class Advertisement {
    private List<AdvertisementData> data;

    public Advertisement() {
    }

    public Advertisement(List<AdvertisementData> data) {
        this.data = data;
    }

    public List<AdvertisementData> getData() {
        return data;
    }

    public void setData(List<AdvertisementData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "data=" + data +
                '}';
    }
}
