package com.shopping.swb.shopping.entity;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-06-02
 * Time: 23:54
 */
public class AdvertisementData {
    private String ipadimg;
    private String iphoneimg;
    private String iphonezimg;
    private String ipadzimg;
    private String iphonemimg;
    private String title;
    private String target;
    private String link;

    public AdvertisementData() {
    }

    public AdvertisementData(String ipadimg, String iphoneimg, String iphonezimg, String ipadzimg, String iphonemimg, String title, String target, String link) {
        this.ipadimg = ipadimg;
        this.iphoneimg = iphoneimg;
        this.iphonezimg = iphonezimg;
        this.ipadzimg = ipadzimg;
        this.iphonemimg = iphonemimg;
        this.title = title;
        this.target = target;
        this.link = link;
    }

    public String getIpadimg() {
        return ipadimg;
    }

    public void setIpadimg(String ipadimg) {
        this.ipadimg = ipadimg;
    }

    public String getIphoneimg() {
        return iphoneimg;
    }

    public void setIphoneimg(String iphoneimg) {
        this.iphoneimg = iphoneimg;
    }

    public String getIphonezimg() {
        return iphonezimg;
    }

    public void setIphonezimg(String iphonezimg) {
        this.iphonezimg = iphonezimg;
    }

    public String getIpadzimg() {
        return ipadzimg;
    }

    public void setIpadzimg(String ipadzimg) {
        this.ipadzimg = ipadzimg;
    }

    public String getIphonemimg() {
        return iphonemimg;
    }

    public void setIphonemimg(String iphonemimg) {
        this.iphonemimg = iphonemimg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "AdvertisementData{" +
                "ipadimg='" + ipadimg + '\'' +
                ", iphoneimg='" + iphoneimg + '\'' +
                ", iphonezimg='" + iphonezimg + '\'' +
                ", ipadzimg='" + ipadzimg + '\'' +
                ", iphonemimg='" + iphonemimg + '\'' +
                ", title='" + title + '\'' +
                ", target='" + target + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
