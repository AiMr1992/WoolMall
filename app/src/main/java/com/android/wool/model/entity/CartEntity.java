package com.android.wool.model.entity;
import android.os.Parcel;
import android.text.TextUtils;

import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * Created by AiMr on 2017/9/25
 */
public class CartEntity implements ListItem{
    private String cart_id;
    private String goods_id;
    private String goods_num;
    private String spec_name;
    private String is_choose;
    private String activity_name;
    private String title;
    private String price;
    private String is_delete;
    private String logo;
    private String status;
    private String market_price;
    private String is_del;

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public String getIs_choose() {
        return is_choose;
    }

    public void setIs_choose(String is_choose) {
        this.is_choose = is_choose;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    @Override
    public CartEntity newObject() {
        return new CartEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setCart_id(JsonUtil.getString(json,"cart_id"));
        setGoods_id(JsonUtil.getString(json,"goods_id"));
        setGoods_num(JsonUtil.getString(json,"goods_num"));
        setSpec_name(JsonUtil.getString(json,"spec_name"));
        setIs_choose(JsonUtil.getString(json,"is_choose"));
        setActivity_name(JsonUtil.getString(json,"activity_name"));
        setTitle(JsonUtil.getString(json,"title"));
        setPrice(JsonUtil.getString(json,"price"));
        setIs_delete(JsonUtil.getString(json,"is_delete"));
        setLogo(JsonUtil.getString(json,"logo"));
        setStatus(JsonUtil.getString(json,"status"));
        setMarket_price(JsonUtil.getString(json,"market_price"));
    }

    public BigDecimal getFavourable(){
        if(!TextUtils.isEmpty(market_price) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(goods_num)) {
            BigDecimal bigDecimal1 = new BigDecimal(market_price);
            BigDecimal bigDecimal2 = new BigDecimal(price);
            BigDecimal bigDecimal3 = new BigDecimal(goods_num);
            BigDecimal addDecimal = bigDecimal1.subtract(bigDecimal2);
            return addDecimal.multiply(bigDecimal3);
        }else {
            return new BigDecimal("0");
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cart_id);
        dest.writeString(this.goods_id);
        dest.writeString(this.goods_num);
        dest.writeString(this.spec_name);
        dest.writeString(this.is_choose);
        dest.writeString(this.activity_name);
        dest.writeString(this.title);
        dest.writeString(this.price);
        dest.writeString(this.is_delete);
        dest.writeString(this.logo);
        dest.writeString(this.status);
        dest.writeString(this.market_price);
        dest.writeString(this.is_del);
    }

    public CartEntity() {
    }

    protected CartEntity(Parcel in) {
        this.cart_id = in.readString();
        this.goods_id = in.readString();
        this.goods_num = in.readString();
        this.spec_name = in.readString();
        this.is_choose = in.readString();
        this.activity_name = in.readString();
        this.title = in.readString();
        this.price = in.readString();
        this.is_delete = in.readString();
        this.logo = in.readString();
        this.status = in.readString();
        this.market_price = in.readString();
        this.is_del = in.readString();
    }

    public static final Creator<CartEntity> CREATOR = new Creator<CartEntity>() {
        @Override
        public CartEntity createFromParcel(Parcel source) {
            return new CartEntity(source);
        }

        @Override
        public CartEntity[] newArray(int size) {
            return new CartEntity[size];
        }
    };
}
