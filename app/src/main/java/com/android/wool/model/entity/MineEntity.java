package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/9/26
 */
public class MineEntity implements ListItem{
    private String name;
    private String avatar;
    private String sex;
    private String cart_num;
    private String order_num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCart_num() {
        return cart_num;
    }

    public void setCart_num(String cart_num) {
        this.cart_num = cart_num;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    @Override
    public MineEntity newObject() {
        return new MineEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setName(JsonUtil.getString(json,"name"));
        setAvatar(JsonUtil.getString(json,"avatar"));
        setSex(JsonUtil.getString(json,"sex"));
        setCart_num(JsonUtil.getString(json,"cart_num"));
        setOrder_num(JsonUtil.getString(json,"order_num"));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeString(this.sex);
        dest.writeString(this.cart_num);
        dest.writeString(this.order_num);
    }

    public MineEntity() {
    }

    protected MineEntity(Parcel in) {
        this.name = in.readString();
        this.avatar = in.readString();
        this.sex = in.readString();
        this.cart_num = in.readString();
        this.order_num = in.readString();
    }

    public static final Creator<MineEntity> CREATOR = new Creator<MineEntity>() {
        @Override
        public MineEntity createFromParcel(Parcel source) {
            return new MineEntity(source);
        }

        @Override
        public MineEntity[] newArray(int size) {
            return new MineEntity[size];
        }
    };
}
