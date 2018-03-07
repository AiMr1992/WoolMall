package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/9/27
 */
public class BannerEntity implements ListItem{
    private String id;
    private String title;
    private String type;
    private String logo;
    private String content;
    private String desc;
    private HomeCourseEntity details;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HomeCourseEntity getDetails() {
        return details;
    }

    public void setDetails(HomeCourseEntity details) {
        this.details = details;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public BannerEntity newObject() {
        return new BannerEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setTitle(JsonUtil.getString(json,"title"));
        setType(JsonUtil.getString(json,"type"));
        setLogo(JsonUtil.getString(json,"logo"));
        setContent(JsonUtil.getString(json,"content"));
        setDesc(JsonUtil.getString(json,"desc"));
        setDetails(JsonUtil.parsEntity("details",json,new HomeCourseEntity()));
        setId(JsonUtil.getString(json,"id"));
    }

    public BannerEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeString(this.logo);
        dest.writeString(this.content);
        dest.writeString(this.desc);
        dest.writeParcelable(this.details, flags);
    }

    protected BannerEntity(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.type = in.readString();
        this.logo = in.readString();
        this.content = in.readString();
        this.desc = in.readString();
        this.details = in.readParcelable(HomeCourseEntity.class.getClassLoader());
    }

    public static final Creator<BannerEntity> CREATOR = new Creator<BannerEntity>() {
        @Override
        public BannerEntity createFromParcel(Parcel source) {
            return new BannerEntity(source);
        }

        @Override
        public BannerEntity[] newArray(int size) {
            return new BannerEntity[size];
        }
    };
}
