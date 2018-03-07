package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
/**
 * Created by AiMr on 2017/10/16
 */
public class OrderDetailEntity implements ListItem{
    private OrderListEntity order_info;
    private List<OrderGoodsEntity> goods_list;
    private LocationEntity addr_info;
    private ExpressEntity express;
    private ActivityEntity activity;

    public OrderListEntity getOrder_info() {
        return order_info;
    }

    public void setOrder_info(OrderListEntity order_info) {
        this.order_info = order_info;
    }

    public List<OrderGoodsEntity> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<OrderGoodsEntity> goods_list) {
        this.goods_list = goods_list;
    }

    public LocationEntity getAddr_info() {
        return addr_info;
    }

    public void setAddr_info(LocationEntity addr_info) {
        this.addr_info = addr_info;
    }

    public ExpressEntity getExpress() {
        return express;
    }

    public void setExpress(ExpressEntity express) {
        this.express = express;
    }

    public ActivityEntity getActivity() {
        return activity;
    }

    public void setActivity(ActivityEntity activity) {
        this.activity = activity;
    }

    @Override
    public OrderDetailEntity newObject() {
        return new OrderDetailEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setOrder_info(JsonUtil.parsEntity("order_info",json,new OrderListEntity()));
        setGoods_list(JsonUtil.parsFromData("goods_list",json,new OrderGoodsEntity()));
        setAddr_info(JsonUtil.parsEntity("addr_info",json,new LocationEntity()));
        setExpress(JsonUtil.parsEntity("express",json,new ExpressEntity()));
        setActivity(JsonUtil.parsEntity("activity",json,new ActivityEntity()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.order_info, flags);
        dest.writeTypedList(this.goods_list);
        dest.writeParcelable(this.addr_info, flags);
        dest.writeParcelable(this.express, flags);
        dest.writeParcelable(this.activity, flags);
    }

    public OrderDetailEntity() {
    }

    protected OrderDetailEntity(Parcel in) {
        this.order_info = in.readParcelable(OrderListEntity.class.getClassLoader());
        this.goods_list = in.createTypedArrayList(OrderGoodsEntity.CREATOR);
        this.addr_info = in.readParcelable(LocationEntity.class.getClassLoader());
        this.express = in.readParcelable(ExpressEntity.class.getClassLoader());
        this.activity = in.readParcelable(ActivityEntity.class.getClassLoader());
    }

    public static final Creator<OrderDetailEntity> CREATOR = new Creator<OrderDetailEntity>() {
        @Override
        public OrderDetailEntity createFromParcel(Parcel source) {
            return new OrderDetailEntity(source);
        }

        @Override
        public OrderDetailEntity[] newArray(int size) {
            return new OrderDetailEntity[size];
        }
    };
}