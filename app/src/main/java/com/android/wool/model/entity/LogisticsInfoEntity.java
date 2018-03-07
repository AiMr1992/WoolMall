package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/10/23
 */
public class LogisticsInfoEntity implements ListItem{
    private String id;
    private String name;
    private String price;
    private String isChoose;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIsChoose() {
        return isChoose;
    }

    public void setIsChoose(String isChoose) {
        this.isChoose = isChoose;
    }

    @Override
    public LogisticsInfoEntity newObject() {
        return new LogisticsInfoEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setName(JsonUtil.getString(json,"name"));
        setPrice(JsonUtil.getString(json,"price"));
        setIsChoose("0");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.isChoose);
    }

    public LogisticsInfoEntity() {
    }

    protected LogisticsInfoEntity(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.isChoose = in.readString();
    }

    public static final Creator<LogisticsInfoEntity> CREATOR = new Creator<LogisticsInfoEntity>() {
        @Override
        public LogisticsInfoEntity createFromParcel(Parcel source) {
            return new LogisticsInfoEntity(source);
        }

        @Override
        public LogisticsInfoEntity[] newArray(int size) {
            return new LogisticsInfoEntity[size];
        }
    };
}