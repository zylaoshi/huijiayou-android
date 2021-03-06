package com.huijiayou.huijiayou;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.huijiayou.huijiayou.config.Constans;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.umeng.analytics.MobclickAgent;

import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lugg on 2017/2/22.
 */

public class MyApplication extends Application {

    private static Context context;
    public static SharedPreferences preferences;
    public static IWXAPI msgApi;
    public List<Activity> activityList = new LinkedList<Activity>();
    public static boolean isLogin = false;
    public static boolean isNewMessage = false;
    public static Tencent mTencent;
    //微信分享
//    private static final String APP_ID = "wxc77febc13d61d07b";
//    private IWXAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        JPushInterface.setDebugMode(true);//极光推送
        JPushInterface.init(this);//极光推送

//        UMShareAPI.get(this);
//        PlatformConfig.setWeixin("wxc77febc13d61d07b", "4c7582669eb3db8ec0bf8a2f22163397");//ok
//        PlatformConfig.setQQZone("1105947235", "0VBI1ejZjatqOTOo"); //ok
        msgApi = WXAPIFactory.createWXAPI(context, Constans.WX_APP_ID,true);
         //将该app注册到微信
        msgApi.registerApp(Constans.WX_APP_ID);
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        ImageLoader.getInstance().init(configuration);

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        mTencent =  Tencent.createInstance("1106037339", this); //初始化qq分享
    }

    public static Context getContext(){
        return context;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancelAll();
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
