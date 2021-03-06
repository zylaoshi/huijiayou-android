package com.huijiayou.huijiayou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;

import com.huijiayou.huijiayou.R;
import com.huijiayou.huijiayou.config.Constans;
import com.huijiayou.huijiayou.config.NetConfig;
import com.huijiayou.huijiayou.net.DeviceUtils;
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

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelfareActivity extends BaseActivity implements IUiListener {

    @Bind(R.id.bwb_activityWelfare_view)
    BridgeWebView bridgeWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("邀请好友");

        String userAgent = bridgeWebView.getSettings().getUserAgentString();
        bridgeWebView.getSettings().setUserAgentString(userAgent + DeviceUtils.getHeadInfo(this));
        bridgeWebView.getSettings().setSaveFormData(false);
        bridgeWebView.getSettings().setDomStorageEnabled(true);
        bridgeWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        bridgeWebView.setDefaultHandler(new DefaultHandler());
        bridgeWebView.getSettings().setDomStorageEnabled(true);
        bridgeWebView.getSettings().setJavaScriptEnabled(true);
        bridgeWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        bridgeWebView.getSettings().setDomStorageEnabled(true);

        bridgeWebView.registerHandler("invitation", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                String mobile = PreferencesUtil.getPreferences(Constans.USER_PHONE,"");
                mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
                String invite_code = PreferencesUtil.getPreferences(Constans.USER_INVITE_CODE,"");
                String url = NetConfig.H5_URL + "?mobile="+mobile+"&invite_code="+invite_code+"#/game/main";
                new ShareUtil().shareWebPage(WelfareActivity.this, "", "", url, WelfareActivity.this);
            }
        });
        bridgeWebView.loadUrl(NetConfig.getwelfare);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode,resultCode,data,this);
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
