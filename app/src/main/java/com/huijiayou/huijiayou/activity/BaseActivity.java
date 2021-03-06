package com.huijiayou.huijiayou.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huijiayou.huijiayou.MyApplication;
import com.huijiayou.huijiayou.R;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends AppCompatActivity {

    MyApplication myApplication;

    public TextView tvTitle;

    public TextView tvBack;

    public TextView tvRight;

    public RelativeLayout rl_common_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getApplication();
        myApplication.addActivity(this);
    }

    protected void initTitle(){
        tvTitle = (TextView) findViewById(R.id.tv_commonTitle_title);
        tvBack = (TextView) findViewById(R.id.tv_commonTitle_back);
        tvRight = (TextView) findViewById(R.id.tv_commonTitle_right);
        rl_common_title = (RelativeLayout) findViewById(R.id.common_title_fragment);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    //设置字体不随系统设置的大小而改变
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
