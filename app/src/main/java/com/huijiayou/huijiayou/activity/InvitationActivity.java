package com.huijiayou.huijiayou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;

import com.huijiayou.huijiayou.R;
import com.huijiayou.huijiayou.config.Constans;
import com.huijiayou.huijiayou.config.NetConfig;
import com.huijiayou.huijiayou.utils.PreferencesUtil;
import com.huijiayou.huijiayou.utils.ToastUtils;
import com.huijiayou.huijiayou.widget.jsbridgewebview.BridgeHandler;
import com.huijiayou.huijiayou.widget.jsbridgewebview.BridgeWebView;
import com.huijiayou.huijiayou.widget.jsbridgewebview.CallBackFunction;
import com.huijiayou.huijiayou.widget.jsbridgewebview.DefaultHandler;
import com.huijiayou.huijiayou.wxapi.ShareUtil;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 好友邀请页
 */
public class InvitationActivity extends BaseActivity implements IUiListener {

    @Bind(R.id.bridgeWebView)
    BridgeWebView bridgeWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("好友邀请");

        final String userId = PreferencesUtil.getPreferences(Constans.USER_ID,"");
        final String session_id = PreferencesUtil.getPreferences("session_id","");
        String url = NetConfig.H5_URL + "?user_id="+userId+"&"+session_id+"#/friend_invi";
//        String url = NetConfig.URL + "/wechat/#/friend_invi";
//        String userAgent = bridgeWebView.getSettings().getUserAgentString();
//        bridgeWebView.getSettings().setUserAgentString(userAgent + DeviceUtils.getHeadInfo(this));
        bridgeWebView.getSettings().setSaveFormData(false);
        bridgeWebView.getSettings().setDomStorageEnabled(true);
        bridgeWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        bridgeWebView.setDefaultHandler(new DefaultHandler());
        bridgeWebView.getSettings().setDomStorageEnabled(true);
        bridgeWebView.getSettings().setJavaScriptEnabled(true);
        bridgeWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        bridgeWebView.getSettings().setDomStorageEnabled(true);

        bridgeWebView.loadUrl(url);
        bridgeWebView.registerHandler("getUserInfos", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("user_id", userId);
                    String token = session_id.substring(session_id.indexOf("OIL_TOKEN")+10);
                    jsonObject.put("OIL_TOKEN", token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                function.onCallBack(jsonObject.toString());
            }
        });

    }

    public void shareInvitation(View view){
        String mobile = PreferencesUtil.getPreferences(Constans.USER_PHONE,"");
        mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
        String invite_code = PreferencesUtil.getPreferences(Constans.USER_INVITE_CODE,"");
        String url = NetConfig.H5_URL + "?mobile="+mobile+"&invite_code="+invite_code+"#/game/main";
        new ShareUtil().shareWebPage(this, "", "", url, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode,resultCode,data,this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

      /*  if (mDialog != null) {
            mDialog.dismiss();
        }*/
    }

    @Override
    public void onComplete(Object o) {
        ToastUtils.createNormalToast(this, "分享成功");
    }

    @Override
    public void onError(UiError uiError) {
        ToastUtils.createLongToast(this, uiError.errorMessage);
    }

    @Override
    public void onCancel() {
        ToastUtils.createLongToast(this, "取消分享");
    }
}
