package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
/**
 * Created by AiMr on 2017/10/23
 */
public class BuildOrderEntity implements ListItem{
    private List<CartEntity> goods_list;
    private List<LocationEntity> addr_info;
    private List<ActivityEntity> activity_list;
    private List<LogisticsInfoEntity> logistics_info;

    @Override
    public BuildOrderEntity newObject() {
        return new BuildOrderEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setGoods_list(JsonUtil.parsFromData("goods_list",json,new CartEntity()));
        setAddr_info(JsonUtil.parsFromData("addr_info",json,new LocationEntity()));
        setActivity_list(JsonUtil.parsFromData("activity_list",json,new ActivityEntity()));
        setLogistics_info(JsonUtil.parsFromData("logistics_info",json,new LogisticsInfoEntity()));
    }

    public List<CartEntity> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<CartEntity> goods_list) {
        this.goods_list = goods_list;
    }

    public List<LocationEntity> getAddr_info() {
        return addr_info;
    }

    public void setAddr_info(List<LocationEntity> addr_info) {
        this.addr_info = addr_info;
    }

    public List<ActivityEntity> getActivity_list() {
        return activity_list;
    }

    public void setActivity_list(List<ActivityEntity> activity_list) {
        this.activity_list = activity_list;
    }

    public List<LogisticsInfoEntity> getLogistics_info() {
        return logistics_info;
    }

    public void setLogistics_info(List<LogisticsInfoEntity> logistics_info) {
        this.logistics_info = logistics_info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.goods_list);
        dest.writeTypedList(this.addr_info);
        dest.writeTypedList(this.activity_list);
        dest.writeTypedList(this.logistics_info);
    }

    public BuildOrderEntity() {
    }

    protected BuildOrderEntity(Parcel in) {
        this.goods_list = in.createTypedArrayList(CartEntity.CREATOR);
        this.addr_info = in.createTypedArrayList(LocationEntity.CREATOR);
        this.activity_list = in.createTypedArrayList(ActivityEntity.CREATOR);
        this.logistics_info = in.createTypedArrayList(LogisticsInfoEntity.CREATOR);
    }

    public static final Creator<BuildOrderEntity> CREATOR = new Creator<BuildOrderEntity>() {
        @Override
        public BuildOrderEntity createFromParcel(Parcel source) {
            return new BuildOrderEntity(source);
        }

        @Override
        public BuildOrderEntity[] newArray(int size) {
            return new BuildOrderEntity[size];
        }
    };
}
