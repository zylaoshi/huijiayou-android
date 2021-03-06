package com.huijiayou.huijiayou.config;

/**
 * Created by lugg on 2017/3/26.
 */

public class NetConfig {

//    public final static String URL = "http://wyh.oil.user.passport.com";//王远航
//    public final static String URL_zxg = "http://oilproduct.dev.wanglibao.com";//张孝国
      public final static String URL = "http://test.1huangjin.cn";
//    public final static String URL = "http://www.ihaomu.com";

//    public final static String ACCOUNT = URL + "/service.php?c=account";
//    public final static String OILCARD = URL + "/index.php?c=oilcard";
//    public final static String PRODUCT = URL + "/index.php?c=product";
//    public final static String ORDER = URL + "/index.php?c=order";
//    public final static String PAY = URL + "/index.php?c=pay";
//    public final static String MESSAGE = URL + "/message/message.php?c=msg";

    public final static String ACCOUNT = URL + "/passport/service.php?c=account";
    public final static String OILCARD = URL + "/pro/index.php?c=oilcard";
    public final static String PRODUCT = URL + "/pro/index.php?c=product";
    public final static String ORDER = URL + "/pro/index.php?c=order";
    public final static String PAY = URL + "/pro/index.php?c=pay";
    public final static String MESSAGE = URL + "/message/message.php?c=msg";


    public final static String message_lst = "lst";
    public final static String message_markAll = "markAll";
    public final static String message_checkNewMsg = "checkNewMsg";
    public final static String message_mark = "mark";

    //method
    public final static String MESSAGEAUTH = "messageAuth"; //获取手机验证码
    public final static String SIGNIN = "signin"; //登录
    public final static String SIGNOUT = "signout"; //撤销登录
    public final static String LOGINSTATUS = "loginStatus"; //获取登录状态
    public final static String WEIXIN_AUTH_POST = "weixin_auth_post"; //微信登录
    //weixin_auth_post

    public final static String productList = "productList"; //产品列表
    public final static String getOilCardList = "getOilCardList"; //油卡列表
    public final static String getCity = "getCity"; //可充油城市的列表
    public final static String bindCard = "bindCard"; //绑卡
    public final static String getOilCardInfo = "getOilCardInfo"; //油卡信息查询接口
    public final static String UserPacketsInfo = "UserPacketsInfo"; //获取用户所有红包
    public final static String UserEnableOil = "UserEnableOil"; //返回该用户的可用油滴
    public final static String checkIn = "checkIn"; //签到
    public final static String UserOildropInfo = "UserOildropInfo"; //返回油滴流水
    public final static String order = "order"; //下单
    public final static String getOrderList = "getOrderList";
    public final static String getOrderInfo=  "getOrderInfo";
    public final static String checkOrder ="checkOrder";//订单支付前调用的接口
    public final  static  String GETUSERSAVEMONEY = "getUserSaveMoney";//总共节省的钱数
    public final static String back ="back";//支付结果请求接口
    public final  static  String shareCodePicture = "shareCodePicture"; //分享生成二维码图片
    public final  static  String userPacketsList = "userPacketsList"; //获取当前产品可用的所有红包
    public final  static  String feedBackForUser = "feedBackForUser"; //用户反馈接
    public final  static  String appVersionSee = "appVersionSee"; //查询最新版本信息


    public static final String H5_URL = URL + "/wechat/";
//    public static final String H5_URL = "http://192.168.10.212:8888/";

    public static final String getwelfare = H5_URL + "#/friend_request_details";
    public static final String register_agreement = H5_URL + "#/register_agreement";
    public static final String user_agreement = H5_URL + "#/user_agreement";
    public static final String help = H5_URL + "#/help";

}
