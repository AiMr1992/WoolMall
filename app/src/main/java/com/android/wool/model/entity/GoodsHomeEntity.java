package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/9/27
 */
public class GoodsHomeEntity implements ListItem{
    private String id;
    private String title;
    private String logo;
    private String price;
    private String sales_num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSales_num() {
        return sales_num;
    }

    public void setSales_num(String sales_num) {
        this.sales_num = sales_num;
    }

    @Override
    public GoodsHomeEntity newObject() {
        return new GoodsHomeEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setTitle(JsonUtil.getString(json,"title"));
        setLogo(JsonUtil.getString(json,"logo"));
        setPrice(JsonUtil.getString(json,"price"));
        setSales_num(JsonUtil.getString(json,"sales_num"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.logo);
        dest.writeString(this.price);
        dest.writeString(this.sales_num);
    }

    public GoodsHomeEntity() {
    }

    protected GoodsHomeEntity(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.logo = in.readString();
        this.price = in.readString();
        this.sales_num = in.readString();
    }

    public static final Creator<GoodsHomeEntity> CREATOR = new Creator<GoodsHomeEntity>() {
        @Override
        public GoodsHomeEntity createFromParcel(Parcel source) {
            return new GoodsHomeEntity(source);
        }

        @Override
        public GoodsHomeEntity[] newArray(int size) {
            return new GoodsHomeEntity[size];
        }
    };
}
