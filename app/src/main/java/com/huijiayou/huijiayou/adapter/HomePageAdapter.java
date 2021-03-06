package com.huijiayou.huijiayou.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huijiayou.huijiayou.R;
import com.huijiayou.huijiayou.utils.CommitUtils;

import java.io.Serializable;
import java.util.ArrayList;

import static com.huijiayou.huijiayou.R.id.tv_itemFragmentHomeProductMain_discount;

/**
 * Created by lugg on 2017/3/17.
 */

public class HomePageAdapter extends PagerAdapter {

    ArrayList<RelativeLayout> viewArrayList;
    ArrayList<Product> productArrayList;
    Context context;

    public HomePageAdapter(ArrayList<Product> productArrayList, Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
        initView();
    }

    @Override
    public int getCount() {
        return viewArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewArrayList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewArrayList.get(position));
        return viewArrayList.get(position);
    }

    public static class Product implements Serializable{
        private String id;
        private String ctime;
        private String utime;
        private String product_name;
        private String product_time;
        private String product_discount;
        private String is_trade;
        private String city_id;
        private String belong;
        private String oil_trade;
        private String image_url;
        private String is_recommend;

        public String getIs_recommend() {
            return is_recommend;
        }

        public void setIs_recommend(String is_recommend) {
            this.is_recommend = is_recommend;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getUtime() {
            return utime;
        }

        public void setUtime(String utime) {
            this.utime = utime;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_time() {
            return product_time;
        }

        public void setProduct_time(String product_time) {
            this.product_time = product_time;
        }

        public String getProduct_discount() {
            return product_discount;
        }

        public void setProduct_discount(String product_discount) {
            this.product_discount = product_discount;
        }

        public String getIs_trade() {
            return is_trade;
        }

        public void setIs_trade(String is_trade) {
            this.is_trade = is_trade;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getBelong() {
            return belong;
        }

        public void setBelong(String belong) {
            this.belong = belong;
        }

        public String getOil_trade() {
            return oil_trade;
        }

        public void setOil_trade(String oil_trade) {
            this.oil_trade = oil_trade;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }

    private void initView(){
        viewArrayList = new ArrayList<>();
        for (int i = 0; i < productArrayList.size(); i++){
            Product product = productArrayList.get(i);
            RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.item_fragment_home_product_main,null,false);
//            TextView tvName = (TextView) relativeLayout.findViewById(R.id.tv_itemFragmentHomeProductMain_name);
//            tvName.setText(product.getProduct_name());
            TextView tvDiscount = (TextView) relativeLayout.findViewById(tv_itemFragmentHomeProductMain_discount);
            double discount = Double.parseDouble(product.getProduct_discount())*10;
            if (discount < 10){
                int length = String.valueOf(discount).trim().substring(String.valueOf(discount).indexOf(".")).length();
                if (length > 2){
                    discount = CommitUtils.decimal2(discount);
                }
                tvDiscount.setText(String.valueOf(discount));
            }else if (discount == 10){
                tvDiscount.setText("10");
            }
            ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.imgView_itemFragmentHomeProductMain_sale);
            if ("1".equals(product.getIs_recommend())){
                imageView.setVisibility(View.VISIBLE);
                Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_home_pic1);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                relativeLayout.setBackgroundDrawable(drawable);
            }else{
                Drawable drawable = context.getResources().getDrawable(getBackgroundId());
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                relativeLayout.setBackgroundDrawable(drawable);
            }

            TextView tvMonth = (TextView) relativeLayout.findViewById(R.id.tv_itemFragmentHomeProductMain_month);
            if ("1".equals(product.getProduct_time())){
                TextView tvMonthTag = (TextView) relativeLayout.findViewById(R.id.tv_itemFragmentHomeProductMain_monthTag);
                tvMonthTag.setText("即时充");
                tvMonth.setText("");
            }else{
                tvMonth.setText(product.getProduct_time());
            }
            viewArrayList.add(relativeLayout);
        }
    }
    int lunHui = 1;
    private int getBackgroundId(){
        int id = 0;
        if (lunHui == 1){
            id = R.mipmap.ic_home_pic2;
        }else if (lunHui == 2){
            id = R.mipmap.ic_home_pic3;
        }else if (lunHui == 3){
            id = R.mipmap.ic_home_pic4;
        }else if (lunHui == 4){
            id = R.mipmap.ic_home_pic5;
        }else if (lunHui == 5){
            id = R.mipmap.ic_home_pic6;
        }
        if (lunHui == 5){
            lunHui = 1;
        }else {
            lunHui++;
        }
        return  id;
    }

}
