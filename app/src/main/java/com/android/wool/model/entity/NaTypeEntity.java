package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
/**
 * Created by AiMr on 2017/9/28
 */
public class NaTypeEntity implements ListItem{
    private List<GoodsCartsEntity> goods_cats;
    private List<GoodsTypeBrandEntity> hot_brand;

    public List<GoodsCartsEntity> getGoods_cats() {
        return goods_cats;
    }

    public void setGoods_cats(List<GoodsCartsEntity> goods_cats) {
        this.goods_cats = goods_cats;
    }

    public List<GoodsTypeBrandEntity> getHot_brand() {
        return hot_brand;
    }

    public void setHot_brand(List<GoodsTypeBrandEntity> hot_brand) {
        this.hot_brand = hot_brand;
    }

    @Override
    public NaTypeEntity newObject() {
        return new NaTypeEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setGoods_cats(JsonUtil.parsFromData("goods_cats",json,new GoodsCartsEntity()));
        setHot_brand(JsonUtil.parsFromData("hot_brand",json,new GoodsTypeBrandEntity()));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.goods_cats);
        dest.writeTypedList(this.hot_brand);
    }

    public NaTypeEntity() {
    }

    protected NaTypeEntity(Parcel in) {
        this.goods_cats = in.createTypedArrayList(GoodsCartsEntity.CREATOR);
        this.hot_brand = in.createTypedArrayList(GoodsTypeBrandEntity.CREATOR);
    }

    public static final Creator<NaTypeEntity> CREATOR = new Creator<NaTypeEntity>() {
        @Override
        public NaTypeEntity createFromParcel(Parcel source) {
            return new NaTypeEntity(source);
        }

        @Override
        public NaTypeEntity[] newArray(int size) {
            return new NaTypeEntity[size];
        }
    };
}
