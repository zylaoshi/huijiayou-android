package com.huijiayou.huijiayou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huijiayou.huijiayou.R;
import com.huijiayou.huijiayou.bean.Message;
import com.huijiayou.huijiayou.config.Constans;
import com.huijiayou.huijiayou.config.NetConfig;
import com.huijiayou.huijiayou.fragment.HomeFragment;
import com.huijiayou.huijiayou.fragment.OrderFragment;
import com.huijiayou.huijiayou.net.MessageEntity;
import com.huijiayou.huijiayou.net.NewHttpRequest;
import com.huijiayou.huijiayou.utils.PreferencesUtil;
import com.huijiayou.huijiayou.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageDetailActivity extends BaseActivity implements NewHttpRequest.RequestCallback{

    @Bind(R.id.tv_ActivityMessageDetail_time)
    TextView tv_ActivityMessageDetail_time;

    @Bind(R.id.tv_ActivityMessageDetail_title)
    TextView tv_ActivityMessageDetail_title;

    @Bind(R.id.tv_ActivityMessageDetail_content)
    TextView tv_ActivityMessageDetail_content;

    @Bind(R.id.view_ActivityMessageDetail_line)
    View view_ActivityMessageDetail_line;

    @Bind(R.id.tv_ActivityMessageDetail_button)
    TextView tv_ActivityMessageDetail_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        initTitle();

        Intent intent = getIntent();
        final Message message = (Message) intent.getSerializableExtra("message");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(Long.parseLong(message.getCreated_at()+"000"));
        tv_ActivityMessageDetail_time.setText(simpleDateFormat1.format(date));
        tvTitle.setText(message.getTitle());
        tv_ActivityMessageDetail_content.setText(message.getContent());

        if ("0".equals(message.getJump_type())){ //纯文本
            view_ActivityMessageDetail_line.setVisibility(View.GONE);
            tv_ActivityMessageDetail_button.setVisibility(View.GONE);
        }else if ("1".equals(message.getJump_type())){ //去加油
            tv_ActivityMessageDetail_button.setText("去加油");
            tv_ActivityMessageDetail_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentHomeFragment = new Intent(MessageDetailActivity.this, MainActivity.class);
                    intentHomeFragment.putExtra("type", HomeFragment.TAG);
                    startActivity(intentHomeFragment);
                    MessageDetailActivity.this.finish();
                }
            });

        }else if ("2".equals(message.getJump_type())){ // 查看订单列表
            tv_ActivityMessageDetail_button.setText("查看订单");
            tv_ActivityMessageDetail_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentMainActivity = new Intent(MessageDetailActivity.this, MainActivity.class);
                    intentMainActivity.putExtra("type", OrderFragment.TAG);
                    startActivity(intentMainActivity);
                    MessageDetailActivity.this.finish();
                }
            });

        }else if ("3".equals(message.getJump_type())){//查看订单详情  TODO
            tv_ActivityMessageDetail_button.setText("查看详情");
            tv_ActivityMessageDetail_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentWebViewActivity = new Intent(MessageDetailActivity.this, WebViewActivity.class);
                    intentWebViewActivity.putExtra("title", "活动");
                    intentWebViewActivity.putExtra("url", message.getUrl());
                    startActivity(intentWebViewActivity);
                    MessageDetailActivity.this.finish();
                }
            });

        }else if ("4".equals(message.getJump_type())){ //邀请好友
            tv_ActivityMessageDetail_button.setText("邀请好友");
            tv_ActivityMessageDetail_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MessageDetailActivity.this, InvitationActivity.class));
                }
            });

        }

//        if ("oil_register".equals(message.getMtype())){
//
//        }else if ("oil_buy_forthwith".equals(message.getMtype()) ||
//                "oil_buy_noforthwith".equals(message.getMtype()) ||
//                "oil_recharge_forthwith".equals(message.getMtype()) ||
//                "oil_recharge_noforthwith".equals(message.getMtype()) ||
//                "oil_packets".equals(message.getMtype()) ||
//                "oil_oildrop_friend".equals(message.getMtype())){
//
//            tv_ActivityMessageDetail_button.setText(message.getJump());
//            if ("1".equals(message.getJump_type())){ //去加油
//                Intent intentHomeFragment = new Intent(this, MainActivity.class);
//                intentHomeFragment.putExtra("type", HomeFragment.TAG);
//                startActivity(intentHomeFragment);
//                finish();
//            }else if ("2".equals(message.getJump_type())){ // 查看订单详情 TODO
//
//            }else if ("3".equals(message.getJump_type())){//查看活动详情 h5  TODO
//
//            }else if ("4".equals(message.getJump_type())){ //邀请好友
//                startActivity(new Intent(this, InvitationActivity.class));
//            }
//
//        }else{
//            view_ActivityMessageDetail_line.setVisibility(View.GONE);
//            tv_ActivityMessageDetail_button.setVisibility(View.GONE);
//        }

        if ("0".equals(message.getRead_status())){
            tv_ActivityMessageDetail_content.setTextColor(getResources().getColor(R.color.textColor_51586A));
            HashMap<String,Object> hashMap = new HashMap<>();
            String userId = PreferencesUtil.getPreferences(Constans.USER_ID,"");
            hashMap.put(Constans.USER_ID,userId);
            hashMap.put("msg_id",message.getId());
            new NewHttpRequest(this, NetConfig.MESSAGE, NetConfig.message_mark, "jsonObject", 1, hashMap, false, this).executeTask();
        }else{
            tv_ActivityMessageDetail_content.setTextColor(getResources().getColor(R.color.gray));
        }
    }

    @Override
    public void netWorkError() {

    }

    @Override
    public void requestSuccess(JSONObject jsonObject, JSONArray jsonArray, int taskId) {

    }

    @Override
    public void requestError(int code, MessageEntity msg, int taskId) {
        ToastUtils.createNormalToast(msg.getMessage());
    }
}
