package com.huijiayou.huijiayou.config;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * Created by lugg on 2017/2/24.
 */

public class Constans {


    public final static boolean DEBUG = true;
    public static String WXBaseUrl = "https://api.weixin.qq.com/";
    public static String WX_APP_ID = "wxc77febc13d61d07b";
    public static IWXAPI WXapi;
  //  public static BaseResp resp;
    public static String AppSecret = "4c7582669eb3db8ec0bf8a2f22163397";


  // public static final String APP_ID = "wxd930ea5d5a258f4f";

    public static class ShowMsgActivity {
        public static final String STitle = "showmsg_title";
        public static final String SMessage = "showmsg_message";
        public static final String BAThumbData = "showmsg_thumb_data";
    }
    //请求的类型
    public final static String JSONOBJECT = "jsonObject";
    public final static String JSONOARRAY= "jsonArray";



    public final static String URL_wyh = "http://wyh.oil.user.passport.com";//王远航
    public final static String URL_zxg = "http://oil_product.dev.wanglibao.com";//张孝国


    public final static String ACCOUNT = "/service.php?c=account";
    public final static String OILCARD = "/index.php?c=oilcard";
    public final static String PRODUCT = "/index.php?c=product";




    //method
    public final static String MESSAGEAUTH = "messageAuth"; //获取手机验证码
    public final static String SIGNIN = "signin"; //登录
    public final static String SIGNOUT = "signout"; //撤销登录
    public final static String LOGINSTATUS = "loginStatus"; //获取登录状态
    public final static String WEIXIN_AUTH_POST = "weixin_auth_post"; //微信登录


    public final static String productList = "productList"; //产品列表
    public final static String getOilcardInfo = "getOilcardInfo"; //油卡列表
    public final static String getCity = "getCity"; //可充油城市的列表
    public final static String bindCard = "bindCard"; //绑卡





    public static final String CODE = "code";
    public static final String DATA = "data";
    public static final String ERROR_MESSAGE = "message";

    public static final String ID_ZHONGSHIHUA = "1";
    public static final String ID_ZHONGSHIYOU = "2";
}