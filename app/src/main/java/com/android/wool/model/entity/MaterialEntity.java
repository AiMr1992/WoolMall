package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2018/1/19
 */
public class MaterialEntity implements ListItem{
    private String id;
    private String title;
    private String content;
    private String description;
    private String keywords;
    private String logo;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public MaterialEntity newObject() {
        return new MaterialEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setTitle(JsonUtil.getString(json,"title"));
        setContent(JsonUtil.getString(json,"content"));
        setDescription(JsonUtil.getString(json,"des"));
        setKeywords(JsonUtil.getString(json,"keywords"));
        setLogo(JsonUtil.getString(json,"logo"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.description);
        dest.writeString(this.keywords);
        dest.writeString(this.logo);
    }

    public MaterialEntity() {
    }

    protected MaterialEntity(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.description = in.readString();
        this.keywords = in.readString();
        this.logo = in.readString();
    }

    public static final Creator<MaterialEntity> CREATOR = new Creator<MaterialEntity>() {
        @Override
        public MaterialEntity createFromParcel(Parcel source) {
            return new MaterialEntity(source);
        }

        @Override
        public MaterialEntity[] newArray(int size) {
            return new MaterialEntity[size];
        }
    };
}