package com.huijiayou.huijiayou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.huijiayou.huijiayou.MyApplication;
import com.huijiayou.huijiayou.R;
import com.huijiayou.huijiayou.config.Constans;
import com.huijiayou.huijiayou.config.NetConfig;
import com.huijiayou.huijiayou.net.MessageEntity;
import com.huijiayou.huijiayou.net.NewHttpRequest;
import com.huijiayou.huijiayou.utils.PreferencesUtil;
import com.huijiayou.huijiayou.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;

public class SplashActivity extends BaseActivity implements NewHttpRequest.RequestCallback{

    @Bind(R.id.iv_welcome)
    ImageView iv_welcome;

    Handler handler = new Handler();
    boolean isGetLoginStatus = false;
    boolean isCountDown = false;

    int loginStatusTaskId = 1;
    boolean isFristStart = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slpha);

        isFristStart = PreferencesUtil.getPreferences(Constans.ISFRISTSTART,true);
        if (isFristStart){
            PreferencesUtil.putPreferences(Constans.ISFRISTSTART,false);
        }

        handler.postDelayed(runnable,2000);
        new NewHttpRequest(this, NetConfig.ACCOUNT,NetConfig.LOGINSTATUS,"jsonObject",loginStatusTaskId,false,this).executeTask();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            isCountDown = true;
            if (isCountDown && isGetLoginStatus){
                if (isFristStart){
                    Intent intent = new Intent(SplashActivity.this, IntroduceActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }else{
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            }
        }
    };

    public void skip(View view){
        if (isFristStart){
            Intent intent = new Intent(SplashActivity.this, IntroduceActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }else{
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
        handler.removeCallbacks(runnable);
    }

    @Override
    public void netWorkError() {

    }

    @Override
    public void requestSuccess(JSONObject jsonObject, JSONArray jsonArray, int taskId) {
        if (taskId == loginStatusTaskId){
            try {
                if(jsonObject.getInt("status") == 0){
                    MyApplication.isLogin = false;
                    PreferencesUtil.putPreferences(Constans.USER_ID,"");
                    PreferencesUtil.putPreferences(Constans.USER_TOKEN,"");
                    PreferencesUtil.putPreferences(Constans.USER_INVITE_CODE,"");
                    PreferencesUtil.putPreferences(Constans.USER_PHONE,"");
                    PreferencesUtil.putPreferences(Constans.ISLOGIN,false);
                }else{
                    MyApplication.isLogin = true;
                    PreferencesUtil.putPreferences(Constans.ISLOGIN,true);
                }
                isGetLoginStatus = true;
                if (isCountDown && isGetLoginStatus){
                    if (isFristStart){
                        Intent intent = new Intent(SplashActivity.this, IntroduceActivity.class);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    }else{
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void requestError(int code, MessageEntity msg, int taskId) {
        ToastUtils.createNormalToast(getApplicationContext(), msg.getMessage());
        isGetLoginStatus = true;
        if (isCountDown && isGetLoginStatus){
            if (isFristStart){
                Intent intent = new Intent(SplashActivity.this, IntroduceActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }else{
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }
    }
}
