package com.shopping.swb.shopping.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.fb.FeedbackAgent;
import com.umeng.message.PushAgent;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-04-29
 * Time: 21:23
 */
public class Utility {

    public static void saveData(Context context, String name, List<String> list) {
        String data = new Gson().toJson(list);
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(name, data).apply();
    }

    public static List<String> getData(Context context, String name) {
        String data = PreferenceManager.getDefaultSharedPreferences(context).getString(name, "");
        return new Gson().fromJson(data, new TypeToken<List<String>>() {
        }.getType());

    }

    public static void shareToWeixin(Context context) {
        String appId = "wx967daebe835fbeac";
        String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
// 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(context, appId, appSecret);
        wxHandler.addToSocialSDK();
// 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    public static void shareToQQ(Activity activity) {
        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qqSsoHandler.addToSocialSDK();
    }

    public static void shareToQQZone(Activity activity) {
        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();
    }

    public static void shareToSina() {
        SinaSsoHandler sinaSsoHandler = new SinaSsoHandler();
        sinaSsoHandler.addToSocialSDK();
    }

    public static void shareToTencent() {
        TencentWBSsoHandler tencentWBSsoHandler = new TencentWBSsoHandler();
        tencentWBSsoHandler.addToSocialSDK();
    }

    public static void shareToRenren(Context context) {
        //添加人人网SSO授权功能
        //APPID:201874
        //API Key:28401c0964f04a72a14c812d6132fcef
        //Secret:3bf66e42db1e4fa9829b955cc300b737
        RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(context,
                "201874", "28401c0964f04a72a14c812d6132fcef",
                "3bf66e42db1e4fa9829b955cc300b737");
        renrenSsoHandler.addToSocialSDK();
    }

    public static UMSocialService share(Activity activity,String content) {
        shareToQQ(activity);
        shareToQQZone(activity);
        shareToWeixin(activity);
        shareToRenren(activity);
        shareToSina();
        shareToTencent();
        UMSocialService controller = UMServiceFactory.getUMSocialService("com.umeng.share");
        controller.setShareContent(content);
        controller.openShare(activity,false);
        return controller;
    }

    public static FeedbackAgent feedback(Context context){
        final FeedbackAgent feedbackAgent = new FeedbackAgent(context);
        feedbackAgent.sync();
    //    feedbackAgent.openAudioFeedback();
        feedbackAgent.openFeedbackPush();
    //    PushAgent.getInstance(context).enable();
        //fb.setWelcomeInfo();
        //  fb.setWelcomeInfo("Welcome to use umeng feedback app");
//        FeedbackPush.getInstance(this).init(true);
//        PushAgent.getInstance(this).setPushIntentServiceClass(MyPushIntentService.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = feedbackAgent.updateUserInfo();
            }
        }).start();
        return feedbackAgent;
    }
    public static PushAgent getPushAgent(Context context){
        PushAgent pushAgent = PushAgent.getInstance(context);
        return pushAgent;
    }
    public static <T> List<T> getGoodsList(String jsonString,Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (Exception e) {
        }
        return list;
    }

    public static <T> T getGoods(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return t;
    }

    public static PackageInfo getPackageInfo(Context context,String packageName){
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(packageName,PackageManager.GET_CONFIGURATIONS);
            return info;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
