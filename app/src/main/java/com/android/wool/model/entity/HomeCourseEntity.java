package com.android.wool.model.entity;
import android.os.Parcel;
import android.text.TextUtils;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/10/27
 */
public class HomeCourseEntity implements ListItem{
    private String id;
    private String title;
    private String desc;
    private String logo;
    private String lecturer;
    private String price;
    private String is_buy;
    private String url;
    private String subscribe;
    private String is_subscribe;
    private String uid;
    private String start_time;
    private String type;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(String is_buy) {
        this.is_buy = is_buy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSubscribe(String subscribe) {
        if(TextUtils.isEmpty(subscribe) || TextUtils.equals("null",subscribe))
            this.subscribe = "0";
        else
            this.subscribe = subscribe;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public HomeCourseEntity newObject() {
        return new HomeCourseEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setTitle(JsonUtil.getString(json,"title"));
        setDesc(JsonUtil.getString(json,"desc"));
        setPrice(JsonUtil.getString(json,"price"));
        setLogo(JsonUtil.getString(json,"logo"));
        setIs_buy(JsonUtil.getString(json,"is_buy"));
        setUrl(JsonUtil.getString(json,"url"));
        setLecturer(JsonUtil.getString(json,"lecturer"));
        setSubscribe(JsonUtil.getString(json,"subscribe"));
        setIs_subscribe(JsonUtil.getString(json,"is_subscribe"));
        setUid(JsonUtil.getString(json,"uid"));
        setStart_time(JsonUtil.getString(json,"start_time"));
        setType(JsonUtil.getString(json,"type"));
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
        dest.writeString(this.desc);
        dest.writeString(this.logo);
        dest.writeString(this.lecturer);
        dest.writeString(this.price);
        dest.writeString(this.is_buy);
        dest.writeString(this.url);
        dest.writeString(this.subscribe);
        dest.writeString(this.is_subscribe);
        dest.writeString(this.uid);
        dest.writeString(this.start_time);
        dest.writeString(this.type);
        dest.writeString(this.status);
    }

    public HomeCourseEntity() {
    }

    protected HomeCourseEntity(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.desc = in.readString();
        this.logo = in.readString();
        this.lecturer = in.readString();
        this.price = in.readString();
        this.is_buy = in.readString();
        this.url = in.readString();
        this.subscribe = in.readString();
        this.is_subscribe = in.readString();
        this.uid = in.readString();
        this.start_time = in.readString();
        this.type = in.readString();
        this.status = in.readString();
    }

    public static final Creator<HomeCourseEntity> CREATOR = new Creator<HomeCourseEntity>() {
        @Override
        public HomeCourseEntity createFromParcel(Parcel source) {
            return new HomeCourseEntity(source);
        }

        @Override
        public HomeCourseEntity[] newArray(int size) {
            return new HomeCourseEntity[size];
        }
    };
}