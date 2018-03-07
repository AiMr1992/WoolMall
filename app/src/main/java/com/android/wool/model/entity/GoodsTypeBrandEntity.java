package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/9/28
 */
public class GoodsTypeBrandEntity implements ListItem{
    private String id;
    private String logo;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public GoodsTypeBrandEntity newObject() {
        return new GoodsTypeBrandEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setLogo(JsonUtil.getString(json,"logo"));
        setTitle(JsonUtil.getString(json,"title"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.logo);
        dest.writeString(this.title);
    }

    public GoodsTypeBrandEntity() {
    }

    protected GoodsTypeBrandEntity(Parcel in) {
        this.id = in.readString();
        this.logo = in.readString();
        this.title = in.readString();
    }

    public static final Creator<GoodsTypeBrandEntity> CREATOR = new Creator<GoodsTypeBrandEntity>() {
        @Override
        public GoodsTypeBrandEntity createFromParcel(Parcel source) {
            return new GoodsTypeBrandEntity(source);
        }

        @Override
        public GoodsTypeBrandEntity[] newArray(int size) {
            return new GoodsTypeBrandEntity[size];
        }
    };

    @Override
    public String toString() {
        return "商品ID:"+this.id;
    }
}
