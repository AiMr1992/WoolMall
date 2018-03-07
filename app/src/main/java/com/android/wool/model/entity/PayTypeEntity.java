package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/10/24
 */
public class PayTypeEntity implements ListItem{
    private String title;
    private String logo;
    private String type;
    private boolean isShow;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    public PayTypeEntity newObject() {
        return new PayTypeEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setTitle(JsonUtil.getString(json,"title"));
        setLogo(JsonUtil.getString(json,"logo"));
        setType(JsonUtil.getString(json,"type"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.logo);
        dest.writeString(this.type);
        dest.writeByte(this.isShow ? (byte) 1 : (byte) 0);
    }

    public PayTypeEntity() {
    }

    protected PayTypeEntity(Parcel in) {
        this.title = in.readString();
        this.logo = in.readString();
        this.type = in.readString();
        this.isShow = in.readByte() != 0;
    }

    public static final Creator<PayTypeEntity> CREATOR = new Creator<PayTypeEntity>() {
        @Override
        public PayTypeEntity createFromParcel(Parcel source) {
            return new PayTypeEntity(source);
        }

        @Override
        public PayTypeEntity[] newArray(int size) {
            return new PayTypeEntity[size];
        }
    };
}