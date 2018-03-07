package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/10/16
 */
public class ActivityEntity implements ListItem{
    private String id;
    private String title;
    private String discount;
    private String exceed;
    private String isChoose;

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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getExceed() {
        return exceed;
    }

    public void setExceed(String exceed) {
        this.exceed = exceed;
    }

    public String getIsChoose() {
        return isChoose;
    }

    public void setIsChoose(String isChoose) {
        this.isChoose = isChoose;
    }

    @Override
    public ActivityEntity newObject() {
        return new ActivityEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setTitle(JsonUtil.getString(json,"title"));
        setExceed(JsonUtil.getString(json,"exceed"));
        setDiscount(JsonUtil.getString(json,"discount"));
        setIsChoose("0");
    }

    public ActivityEntity newNoneEntity(){
        ActivityEntity entity = newObject();
        entity.setTitle("无");
        entity.setDiscount("0");
        entity.setIsChoose("0");
        return entity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.discount);
        dest.writeString(this.exceed);
        dest.writeString(this.isChoose);
    }

    public ActivityEntity() {
    }

    protected ActivityEntity(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.discount = in.readString();
        this.exceed = in.readString();
        this.isChoose = in.readString();
    }

    public static final Creator<ActivityEntity> CREATOR = new Creator<ActivityEntity>() {
        @Override
        public ActivityEntity createFromParcel(Parcel source) {
            return new ActivityEntity(source);
        }

        @Override
        public ActivityEntity[] newArray(int size) {
            return new ActivityEntity[size];
        }
    };
}
