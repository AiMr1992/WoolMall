package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
/**
 * Created by AiMr on 2017/10/13
 */
public class OrderListEntity implements ListItem{
    private String id;
    private String order_no;
    private String status;
    private String total_order;
    private String cost_carry;
    private String add_time;
    private String comment;
    private String exp_time;
    private List<OrderGoodsEntity> goods_list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_order() {
        return total_order;
    }

    public void setTotal_order(String total_order) {
        this.total_order = total_order;
    }

    public String getCost_carry() {
        return cost_carry;
    }

    public void setCost_carry(String cost_carry) {
        this.cost_carry = cost_carry;
    }

    public List<OrderGoodsEntity> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<OrderGoodsEntity> goods_list) {
        this.goods_list = goods_list;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getExp_time() {
        return exp_time;
    }

    public void setExp_time(String exp_time) {
        this.exp_time = exp_time;
    }

    @Override
    public OrderListEntity newObject() {
        return new OrderListEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setOrder_no(JsonUtil.getString(json,"order_no"));
        setStatus(JsonUtil.getString(json,"status"));
        setTotal_order(JsonUtil.getString(json,"total_order"));
        setCost_carry(JsonUtil.getString(json,"cost_carry"));
        setAdd_time(JsonUtil.getString(json,"add_time"));
        setComment(JsonUtil.getString(json,"comment"));
        setExp_time(JsonUtil.getString(json,"send_time"));
        setGoods_list(JsonUtil.parsFromData("goods_list",json,new OrderGoodsEntity()));
    }

    public OrderListEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.order_no);
        dest.writeString(this.status);
        dest.writeString(this.total_order);
        dest.writeString(this.cost_carry);
        dest.writeString(this.add_time);
        dest.writeString(this.comment);
        dest.writeString(this.exp_time);
        dest.writeTypedList(this.goods_list);
    }

    protected OrderListEntity(Parcel in) {
        this.id = in.readString();
        this.order_no = in.readString();
        this.status = in.readString();
        this.total_order = in.readString();
        this.cost_carry = in.readString();
        this.add_time = in.readString();
        this.comment = in.readString();
        this.exp_time = in.readString();
        this.goods_list = in.createTypedArrayList(OrderGoodsEntity.CREATOR);
    }

    public static final Creator<OrderListEntity> CREATOR = new Creator<OrderListEntity>() {
        @Override
        public OrderListEntity createFromParcel(Parcel source) {
            return new OrderListEntity(source);
        }

        @Override
        public OrderListEntity[] newArray(int size) {
            return new OrderListEntity[size];
        }
    };
}
