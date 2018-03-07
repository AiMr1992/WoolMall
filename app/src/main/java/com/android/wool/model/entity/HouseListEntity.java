package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/9/26
 */
public class HouseListEntity implements ListItem{
    private String id;
    private String title;
    private String price;
    private String market_price;
    private String logo;
    private String status;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
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



    @Override
    public HouseListEntity newObject() {
        return new HouseListEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setTitle(JsonUtil.getString(json,"title"));
        setPrice(JsonUtil.getString(json,"price"));
        setMarket_price(JsonUtil.getString(json,"market_price"));
        setLogo(JsonUtil.getString(json,"logo"));
        setStatus(JsonUtil.getString(json,"status"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.price);
        dest.writeString(this.market_price);
        dest.writeString(this.logo);
        dest.writeString(this.status);
    }

    public HouseListEntity() {
    }

    protected HouseListEntity(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.price = in.readString();
        this.market_price = in.readString();
        this.logo = in.readString();
        this.status = in.readString();
    }

    public static final Creator<HouseListEntity> CREATOR = new Creator<HouseListEntity>() {
        @Override
        public HouseListEntity createFromParcel(Parcel source) {
            return new HouseListEntity(source);
        }

        @Override
        public HouseListEntity[] newArray(int size) {
            return new HouseListEntity[size];
        }
    };
}
