package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/9/27
 */
public class SpecialEntity implements ListItem{
    private String id;
    private String title;
    private String logo;
    private String desc;
    private String type;
    private String sales_num;
    private HomeCourseEntity details;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSales_num() {
        return sales_num;
    }

    public void setSales_num(String sales_num) {
        this.sales_num = sales_num;
    }

    public HomeCourseEntity getDetails() {
        return details;
    }

    public void setDetails(HomeCourseEntity details) {
        this.details = details;
    }

    @Override
    public SpecialEntity newObject() {
        return new SpecialEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setTitle(JsonUtil.getString(json,"title"));
        setLogo(JsonUtil.getString(json,"logo"));
        setDesc(JsonUtil.getString(json,"desc"));
        setType(JsonUtil.getString(json,"type"));
        setSales_num(JsonUtil.getString(json,"sales_num"));
        setDetails(JsonUtil.parsEntity("details",json,new HomeCourseEntity()));
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
        dest.writeString(this.desc);
        dest.writeString(this.type);
        dest.writeString(this.sales_num);
        dest.writeParcelable(this.details, flags);
    }

    public SpecialEntity() {
    }

    protected SpecialEntity(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.logo = in.readString();
        this.desc = in.readString();
        this.type = in.readString();
        this.sales_num = in.readString();
        this.details = in.readParcelable(HomeCourseEntity.class.getClassLoader());
    }

    public static final Creator<SpecialEntity> CREATOR = new Creator<SpecialEntity>() {
        @Override
        public SpecialEntity createFromParcel(Parcel source) {
            return new SpecialEntity(source);
        }

        @Override
        public SpecialEntity[] newArray(int size) {
            return new SpecialEntity[size];
        }
    };
}