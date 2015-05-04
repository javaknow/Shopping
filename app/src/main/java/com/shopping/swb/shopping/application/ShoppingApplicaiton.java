package com.shopping.swb.shopping.application;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.shopping.swb.shopping.activity.FeedbackActivity;
import com.umeng.fb.push.FeedbackPush;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-04-25
 * Time: 16:33
 */
public class ShoppingApplicaiton extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
    //    FeedbackPush.getInstance(this).init(false);
        FeedbackPush.getInstance(this).init(FeedbackActivity.class, true);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }
}
