package com.shopping.swb.shopping.util;

import android.app.Activity;
import android.content.Context;

import com.shopping.swb.shopping.R;
import com.umeng.fb.FeedbackAgent;
import com.umeng.message.PushAgent;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.laiwang.controller.UMLWHandler;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.RenrenShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.EmailHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.umeng.socialize.yixin.controller.UMYXHandler;
import com.umeng.socialize.ynote.controller.UMYNoteHandler;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-11
 * Time: 11:01
 */
public class UMUtil {
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

    public static void shareToSMS() {
        SmsHandler smsHandler = new SmsHandler();
        smsHandler.addToSocialSDK();
    }

    public static void shareToEmail() {
        EmailHandler emailHandler = new EmailHandler();
        emailHandler.addToSocialSDK();
    }

    public static void shareToYNote(Context context) {
        UMYNoteHandler yNoteHandler = new UMYNoteHandler(context);
        yNoteHandler.addToSocialSDK();
    }

    public static void shareToYX(Context context) {
        // 添加易信平台,参数1为当前activity, 参数2为在易信开放平台申请到的app id
        UMYXHandler yixinHandler = new UMYXHandler(context,
                "yxc0614e80c9304c11b0391514d09f13bf");
// 关闭分享时的等待Dialog
        yixinHandler.enableLoadingDialog(false);
// 把易信添加到SDK中
        yixinHandler.addToSocialSDK();

// 易信朋友圈平台,参数1为当前activity, 参数2为在易信开放平台申请到的app id
        UMYXHandler yxCircleHandler = new UMYXHandler(context,
                "yxc0614e80c9304c11b0391514d09f13bf");
        yxCircleHandler.setToCircle(true);
        yxCircleHandler.addToSocialSDK();
    }
    public static void shareToLW(Context context){
        //添加来往
        UMLWHandler umlwHandler = new UMLWHandler(context, "laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        umlwHandler.addToSocialSDK();

//添加来往动态
        UMLWHandler umlwDynamicHandler = new UMLWHandler(context, "laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        umlwDynamicHandler.setToCircle(true);
        umlwDynamicHandler.addToSocialSDK();
    }
    public static QQShareContent setQQShareContent(Context context){
        QQShareContent qqShareContent = new QQShareContent();
//设置分享文字
        qqShareContent.setShareContent(context.getString(R.string.share_content));
//设置分享title
        qqShareContent.setTitle(context.getResources().getString(R.string.app_name));
//设置分享图片
        qqShareContent.setShareImage(new UMImage(context, R.mipmap.logo));
//设置点击分享内容的跳转链接
        qqShareContent.setTargetUrl(context.getString(R.string.share_url));
        return qqShareContent;
    }
    public static QZoneShareContent setQZoneShareContent(Context context){
        QZoneShareContent qzoneContent = new QZoneShareContent();
//设置分享文字
        qzoneContent.setShareContent(context.getString(R.string.share_content));
//设置点击消息的跳转URL
        qzoneContent.setTargetUrl(context.getString(R.string.share_url));
//设置分享内容的标题
        qzoneContent.setTitle(context.getResources().getString(R.string.app_name));
//设置分享图片
        qzoneContent.setShareImage(new UMImage(context, R.mipmap.logo));
        return qzoneContent;
    }

    public static WeiXinShareContent setWXShareContent(Context context){
        //设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        //设置分享文字
        weixinContent.setShareContent(context.getString(R.string.share_content));
//设置title
        weixinContent.setTitle(context.getResources().getString(R.string.app_name));
//设置分享内容跳转URL
        weixinContent.setTargetUrl(context.getString(R.string.share_url));
//设置分享图片
        weixinContent.setShareImage(new UMImage(context, R.mipmap.logo));
        return weixinContent;
    }
    public static CircleShareContent setWXCirlceShareContent(Context context){
//设置微信朋友圈分享内容
        CircleShareContent circleContent = new CircleShareContent();
        circleContent.setShareContent(context.getString(R.string.share_content));
//设置朋友圈title
        circleContent.setTitle(context.getResources().getString(R.string.app_name));
        circleContent.setShareImage(new UMImage(context, R.mipmap.logo));
        circleContent.setTargetUrl(context.getString(R.string.share_url));
        return circleContent;
    }
    public static SinaShareContent setSinaShareContent(Context context){
        SinaShareContent sinaShareContent = new SinaShareContent();
        sinaShareContent.setTitle(context.getResources().getString(R.string.app_name));
        sinaShareContent.setShareContent(context.getString(R.string.share_content)+context.getString(R.string.share_url));
        sinaShareContent.setTargetUrl(context.getString(R.string.share_url));
        return sinaShareContent;
    }
    public static RenrenShareContent setRenrenShareContent(Context context){
        RenrenShareContent renrenShareContent = new RenrenShareContent();
        renrenShareContent.setTitle(context.getResources().getString(R.string.app_name));
        renrenShareContent.setShareContent(context.getString(R.string.share_content)+context.getString(R.string.share_url));
        renrenShareContent.setTargetUrl(context.getString(R.string.share_url));
        return renrenShareContent;
    }
    public static TencentWbShareContent setTencentWBShareContent(Context context){
        TencentWbShareContent tencentShareContent = new TencentWbShareContent();
        tencentShareContent.setTitle(context.getResources().getString(R.string.app_name));
        tencentShareContent.setShareContent(context.getString(R.string.share_content)+context.getString(R.string.share_url));
        tencentShareContent.setTargetUrl(context.getString(R.string.share_url));
        return tencentShareContent;
    }
    public static SmsShareContent setSmsShareContent(Context context){
        SmsShareContent smsShareContent = new SmsShareContent();
        smsShareContent.setShareContent(context.getString(R.string.share_content)+context.getString(R.string.share_url));
        return smsShareContent;
    }
    public static UMSocialService share(Activity activity, String content) {
        shareToQQ(activity);
        shareToQQZone(activity);
        shareToWeixin(activity);
        shareToRenren(activity);
        shareToSina();
        shareToTencent();
        shareToSMS();
//        shareToEmail();
//        shareToYNote(activity);
//        shareToYX(activity);
//        shareToLW(activity);
        UMSocialService controller = UMServiceFactory.getUMSocialService("com.umeng.share");
//        controller.setShareContent(content);
//        UMImage umImage= new UMImage(activity,R.mipmap.logo);
//        umImage.setTitle(activity.getString(R.string.app_name));
//        umImage.setTargetUrl(activity.getString(R.string.share_url));
//        controller.setShareMedia(umImage);
        controller.setShareMedia(setQQShareContent(activity));
        controller.setShareMedia(setWXShareContent(activity));
        controller.setShareMedia(setWXCirlceShareContent(activity));
        controller.setShareMedia(setQZoneShareContent(activity));
        controller.setShareMedia(setSinaShareContent(activity));
        controller.setShareMedia(setRenrenShareContent(activity));
        controller.setShareMedia(setTencentWBShareContent(activity));
        controller.setShareMedia(setSmsShareContent(activity));
        //  controller.openShare(activity,false);
        return controller;
    }

    public static FeedbackAgent feedback(Context context) {
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

    public static PushAgent getPushAgent(Context context) {
        PushAgent pushAgent = PushAgent.getInstance(context);
        return pushAgent;
    }

}
