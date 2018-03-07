package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/9/25
 */
public class UserEntity implements ListItem{
    private String id;
    private String name;
    private String phone;
    private String avatar;
    private String roles;
    private String domain_name;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getDomain_name() {
        return domain_name;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }

    @Override
    public UserEntity newObject() {
        return new UserEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setName(JsonUtil.getString(json,"name"));
        setAvatar(JsonUtil.getString(json,"avatar"));
        setPhone(JsonUtil.getString(json,"phone"));
        setRoles(JsonUtil.getString(json,"roles"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.avatar);
        dest.writeString(this.roles);
        dest.writeString(this.domain_name);
    }

    public UserEntity() {
    }

    protected UserEntity(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.avatar = in.readString();
        this.roles = in.readString();
        this.domain_name = in.readString();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };
}
