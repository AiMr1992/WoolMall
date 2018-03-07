package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
/**
 * Created by AiMr on 2017/10/27
 */
public class LogisticsEntity implements ListItem{
    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String name;
    private String status;
    private String state;
    private List<LogisticsTimeEntity> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<LogisticsTimeEntity> getData() {
        return data;
    }

    public void setData(List<LogisticsTimeEntity> data) {
        this.data = data;
    }

    @Override
    public LogisticsEntity newObject() {
        return new LogisticsEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setMessage(JsonUtil.getString(json,"message"));
        setNu(JsonUtil.getString(json,"nu"));
        setIscheck(JsonUtil.getString(json,"ischeck"));
        setCondition(JsonUtil.getString(json,"condition"));
        setName(JsonUtil.getString(json,"com"));
        setStatus(JsonUtil.getString(json,"status"));
        setStatus(JsonUtil.getString(json,"state"));
        setData(JsonUtil.parsFromData("data",json,new LogisticsTimeEntity()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeString(this.nu);
        dest.writeString(this.ischeck);
        dest.writeString(this.condition);
        dest.writeString(this.name);
        dest.writeString(this.status);
        dest.writeString(this.state);
        dest.writeTypedList(this.data);
    }

    public LogisticsEntity() {
    }

    protected LogisticsEntity(Parcel in) {
        this.message = in.readString();
        this.nu = in.readString();
        this.ischeck = in.readString();
        this.condition = in.readString();
        this.name = in.readString();
        this.status = in.readString();
        this.state = in.readString();
        this.data = in.createTypedArrayList(LogisticsTimeEntity.CREATOR);
    }

    public static final Creator<LogisticsEntity> CREATOR = new Creator<LogisticsEntity>() {
        @Override
        public LogisticsEntity createFromParcel(Parcel source) {
            return new LogisticsEntity(source);
        }

        @Override
        public LogisticsEntity[] newArray(int size) {
            return new LogisticsEntity[size];
        }
    };
}