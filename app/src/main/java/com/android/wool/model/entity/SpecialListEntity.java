package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
/**
 * Created by AiMr on 2017/10/28
 */
public class SpecialListEntity implements ListItem{
    private SpecialEntity special_info;
    private List<GoodsHomeEntity> goods_list;

    public SpecialEntity getSpecial_info() {
        return special_info;
    }

    public void setSpecial_info(SpecialEntity special_info) {
        this.special_info = special_info;
    }

    public List<GoodsHomeEntity> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsHomeEntity> goods_list) {
        this.goods_list = goods_list;
    }

    @Override
    public SpecialListEntity newObject() {
        return new SpecialListEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setSpecial_info(JsonUtil.parsEntity("special_info",json,new SpecialEntity()));
        setGoods_list(JsonUtil.parsFromData("goods_list",json,new GoodsHomeEntity()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.special_info, flags);
        dest.writeTypedList(this.goods_list);
    }

    public SpecialListEntity() {
    }

    protected SpecialListEntity(Parcel in) {
        this.special_info = in.readParcelable(SpecialEntity.class.getClassLoader());
        this.goods_list = in.createTypedArrayList(GoodsHomeEntity.CREATOR);
    }

    public static final Creator<SpecialListEntity> CREATOR = new Creator<SpecialListEntity>() {
        @Override
        public SpecialListEntity createFromParcel(Parcel source) {
            return new SpecialListEntity(source);
        }

        @Override
        public SpecialListEntity[] newArray(int size) {
            return new SpecialListEntity[size];
        }
    };
}
