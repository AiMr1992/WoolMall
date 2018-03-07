package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/10/13
 */
public class OrderGoodsEntity implements ListItem{
    private String goods_id;
    private String goods_title;
    private String goods_num;
    private String goods_price;
    private String spec_name;
    private String goods_logo;
    private String price_original;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public String getGoods_logo() {
        return goods_logo;
    }

    public void setGoods_logo(String goods_logo) {
        this.goods_logo = goods_logo;
    }

    public String getPrice_original() {
        return price_original;
    }

    public void setPrice_original(String price_original) {
        this.price_original = price_original;
    }

    @Override
    public OrderGoodsEntity newObject() {
        return new OrderGoodsEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setGoods_id(JsonUtil.getString(json,"goods_id"));
        setGoods_title(JsonUtil.getString(json,"goods_title"));
        setGoods_num(JsonUtil.getString(json,"goods_num"));
        setGoods_price(JsonUtil.getString(json,"goods_price"));
        setSpec_name(JsonUtil.getString(json,"spec_name"));
        setGoods_logo(JsonUtil.getString(json,"goods_logo"));
        setPrice_original(JsonUtil.getString(json,"price_original"));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.goods_id);
        dest.writeString(this.goods_title);
        dest.writeString(this.goods_num);
        dest.writeString(this.goods_price);
        dest.writeString(this.spec_name);
        dest.writeString(this.goods_logo);
        dest.writeString(this.price_original);
    }

    public OrderGoodsEntity() {
    }

    protected OrderGoodsEntity(Parcel in) {
        this.goods_id = in.readString();
        this.goods_title = in.readString();
        this.goods_num = in.readString();
        this.goods_price = in.readString();
        this.spec_name = in.readString();
        this.goods_logo = in.readString();
        this.price_original = in.readString();
    }

    public static final Creator<OrderGoodsEntity> CREATOR = new Creator<OrderGoodsEntity>() {
        @Override
        public OrderGoodsEntity createFromParcel(Parcel source) {
            return new OrderGoodsEntity(source);
        }

        @Override
        public OrderGoodsEntity[] newArray(int size) {
            return new OrderGoodsEntity[size];
        }
    };
}
