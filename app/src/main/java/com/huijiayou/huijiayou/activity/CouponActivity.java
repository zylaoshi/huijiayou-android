package com.huijiayou.huijiayou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huijiayou.huijiayou.R;
import com.huijiayou.huijiayou.adapter.CouponAdapter;
import com.huijiayou.huijiayou.adapter.OverCouponAdapter;
import com.huijiayou.huijiayou.adapter.UseCouponAdapter;
import com.huijiayou.huijiayou.config.Constans;
import com.huijiayou.huijiayou.config.NetConfig;
import com.huijiayou.huijiayou.fragment.HomeFragment;
import com.huijiayou.huijiayou.net.MessageEntity;
import com.huijiayou.huijiayou.net.NewHttpRequest;
import com.huijiayou.huijiayou.utils.PreferencesUtil;
import com.huijiayou.huijiayou.widget.DrawablePaddingTextView;
import com.huijiayou.huijiayou.widget.SVRecyclerView;
import com.huijiayou.huijiayou.widget.SpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CouponActivity extends BaseActivity implements NewHttpRequest.RequestCallback{

    @Bind(R.id.btn_activityCoupon_noUseCoupon)
    Button btn_activityCoupon_noUseCoupon;

    @Bind(R.id.tv_activityCoupon_noUseTag)
    TextView tv_activityCoupon_noUseTag;

    @Bind(R.id.recyclerView_activityCoupon_noUse)
    SVRecyclerView recyclerView_activityCoupon_noUse;

    @Bind(R.id.tv_activityCoupon_useTag)
    TextView tv_activityCoupon_useTag;

    @Bind(R.id.recyclerView_activityCoupon_use)
    SVRecyclerView recyclerView_activityCoupon_use;

    @Bind(R.id.tv_activityCoupon_over)
    TextView tv_activityCoupon_over;

    @Bind(R.id.svRecyclerView_activityCoupon_over)
    SVRecyclerView svRecyclerView_activityCoupon_over;

    @Bind(R.id.rl_activityCoupon_use)
    RelativeLayout rl_activityCoupon_use;

    @Bind(R.id.tv_activityCoupon_use_size)
    TextView tv_activityCoupon_use_size;

    @Bind(R.id.tv_activityCoupon_noData)
    DrawablePaddingTextView tv_activityCoupon_noData;

    int UserPacketsInfoTaskId = 1;
    int userPacketsListTaskId = 5;

    public static final int NORMAL_TYPE = 1;
    public static final int SELECTED_TYPE = 2;
    int type = NORMAL_TYPE;
    CouponAdapter noUseCouponAdapter;
    UseCouponAdapter useCouponAdapter;
    OverCouponAdapter overCouponAdapter;
    ArrayList<CouponAdapter.Coupon> couponNoUseArrayList;
    ArrayList<UseCouponAdapter.Coupon> couponAlreadyUseArrayList;
    ArrayList<OverCouponAdapter.Coupon> couponOverArrayList;

    String product_id,oilCard,discountTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("优惠券");

        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type",NORMAL_TYPE);

        if (NORMAL_TYPE == type){
            codeNormal();
            UserPacketsInfo();
        }else if (SELECTED_TYPE == type){
            product_id = intent.getStringExtra("product_id");
            oilCard = intent.getStringExtra("oilCard");
            discountTotal = intent.getStringExtra("discountTotal");
            codeSelected();
            userPacketsList();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_activityCoupon_use.setLayoutManager(linearLayoutManager);
        recyclerView_activityCoupon_use.addItemDecoration(new SpaceItemDecoration(20));
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_activityCoupon_noUse.setLayoutManager(linearLayoutManager);
        recyclerView_activityCoupon_noUse.addItemDecoration(new SpaceItemDecoration(20));
        linearLayoutManager = new LinearLayoutManager(this);
        svRecyclerView_activityCoupon_over.setLayoutManager(linearLayoutManager);
        svRecyclerView_activityCoupon_over.addItemDecoration(new SpaceItemDecoration(20));
    }

    private void UserPacketsInfo(){
        HashMap<String,Object> hashMap = new HashMap<>();
        String userId = PreferencesUtil.getPreferences(Constans.USER_ID,"");
        hashMap.put(Constans.USER_ID,userId);

        new NewHttpRequest(this, NetConfig.ACCOUNT, NetConfig.UserPacketsInfo, "jsonObject", UserPacketsInfoTaskId,
                hashMap, true, this).executeTask();
    }

    private void userPacketsList(){
        HashMap<String,Object> hashMap = new HashMap<>();
        String userId = PreferencesUtil.getPreferences(Constans.USER_ID,"");
        hashMap.put(Constans.USER_ID,userId);
        hashMap.put("products_id",product_id);
        hashMap.put("card_num",oilCard);
        hashMap.put("amount",discountTotal);

        new NewHttpRequest(this, NetConfig.ACCOUNT, NetConfig.userPacketsList, "jsonObject", userPacketsListTaskId,
                hashMap, true, this).executeTask();
    }

    private void codeNormal(){
        btn_activityCoupon_noUseCoupon.setVisibility(View.GONE);
    }

    private void codeSelected(){
        tv_activityCoupon_noUseTag.setVisibility(View.GONE);
        rl_activityCoupon_use.setVisibility(View.GONE);
        tv_activityCoupon_useTag.setVisibility(View.GONE);
        recyclerView_activityCoupon_use.setVisibility(View.GONE);
        tv_activityCoupon_over.setVisibility(View.GONE);
        svRecyclerView_activityCoupon_over.setVisibility(View.GONE);
    }

    public void useCoupon(View view){
        Intent inten = new Intent(this,MainActivity.class);
        inten.putExtra("type", HomeFragment.TAG);
        startActivity(inten);
        finish();
    }

    public void noUseCoupon(View view){
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void netWorkError() {

    }

    //"noUse"未使用,"alreadyUse 已使用,"over"已过期
    @Override
    public void requestSuccess(JSONObject jsonObject, JSONArray jsonArray, int taskId) {
        try {
            if (taskId == UserPacketsInfoTaskId){
                JSONObject jsonObject1 = jsonObject.getJSONObject("list");
                couponNoUseArrayList = new Gson().fromJson(jsonObject1.get("noUse").toString(), new TypeToken<ArrayList<CouponAdapter.Coupon>>() {}.getType());
                noUseCouponAdapter = new CouponAdapter(couponNoUseArrayList,this,null);
                recyclerView_activityCoupon_noUse.setAdapter(noUseCouponAdapter);

                couponAlreadyUseArrayList = new Gson().fromJson(jsonObject1.get("alreadyUse").toString(), new TypeToken<ArrayList<UseCouponAdapter.Coupon>>() {}.getType());
                useCouponAdapter = new UseCouponAdapter(couponAlreadyUseArrayList,this,null);
                recyclerView_activityCoupon_use.setAdapter(useCouponAdapter);

                couponOverArrayList = new Gson().fromJson(jsonObject1.get("over").toString(), new TypeToken<ArrayList<OverCouponAdapter.Coupon>>() {}.getType());
                overCouponAdapter = new OverCouponAdapter(couponOverArrayList,this,null);
                svRecyclerView_activityCoupon_over.setAdapter(overCouponAdapter);

                if (couponAlreadyUseArrayList == null || couponAlreadyUseArrayList.size() == 0){
                    recyclerView_activityCoupon_use.setVisibility(View.GONE);
                    tv_activityCoupon_useTag.setVisibility(View.GONE);
                }else{
                    recyclerView_activityCoupon_use.setVisibility(View.VISIBLE);
                    tv_activityCoupon_useTag.setVisibility(View.VISIBLE);
                }
                if (couponOverArrayList == null || couponOverArrayList.size() == 0){
                    svRecyclerView_activityCoupon_over.setVisibility(View.GONE);
                    tv_activityCoupon_over.setVisibility(View.GONE);
                }else{
                    svRecyclerView_activityCoupon_over.setVisibility(View.VISIBLE);
                    tv_activityCoupon_over.setVisibility(View.VISIBLE);
                }
                if ( (couponAlreadyUseArrayList == null || couponAlreadyUseArrayList.size() == 0) &&
                        (couponOverArrayList == null || couponOverArrayList.size() == 0) &&
                        (couponNoUseArrayList == null || couponNoUseArrayList.size() == 0)){
                    tv_activityCoupon_noData.setVisibility(View.VISIBLE);
                }
            }else if (taskId == userPacketsListTaskId){
                Object object = jsonObject.get("data");
                JSONArray jsonArray1 = new JSONArray();
                if (object != null){
                    jsonArray1 = (JSONArray) object;
                }
                couponNoUseArrayList = new Gson().fromJson(jsonArray1.toString(), new TypeToken<ArrayList<CouponAdapter.Coupon>>() {}.getType());
                noUseCouponAdapter = new CouponAdapter(couponNoUseArrayList, this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) v.getTag();
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("coupon",couponNoUseArrayList.get(position));
                        intent.putExtra("coupon",bundle);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
                recyclerView_activityCoupon_noUse.setAdapter(noUseCouponAdapter);
                recyclerView_activityCoupon_use.setVisibility(View.GONE);
                tv_activityCoupon_useTag.setVisibility(View.GONE);
                svRecyclerView_activityCoupon_over.setVisibility(View.GONE);
                tv_activityCoupon_over.setVisibility(View.GONE);
            }

            if (couponNoUseArrayList != null && couponNoUseArrayList.size() > 0){
                tv_activityCoupon_use_size.setText(couponNoUseArrayList.size()+"");
                tv_activityCoupon_noUseTag.setVisibility(View.VISIBLE);
                recyclerView_activityCoupon_noUse.setVisibility(View.VISIBLE);
            }else{
                rl_activityCoupon_use.setVisibility(View.GONE);
                tv_activityCoupon_noUseTag.setVisibility(View.GONE);
                recyclerView_activityCoupon_noUse.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestError(int code, MessageEntity msg, int taskId) {
        if (taskId == UserPacketsInfoTaskId){

        }
    }
}
