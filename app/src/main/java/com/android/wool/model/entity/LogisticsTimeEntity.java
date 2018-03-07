package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/10/27
 */
public class LogisticsTimeEntity implements ListItem{
    private String time;
    private String ftime;
    private String context;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public LogisticsTimeEntity newObject() {
        return new LogisticsTimeEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setTime(JsonUtil.getString(json,"time"));
        setFtime(JsonUtil.getString(json,"ftime"));
        setContext(JsonUtil.getString(json,"context"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeString(this.ftime);
        dest.writeString(this.context);
    }

    public LogisticsTimeEntity() {
    }

    protected LogisticsTimeEntity(Parcel in) {
        this.time = in.readString();
        this.ftime = in.readString();
        this.context = in.readString();
    }

    public static final Creator<LogisticsTimeEntity> CREATOR = new Creator<LogisticsTimeEntity>() {
        @Override
        public LogisticsTimeEntity createFromParcel(Parcel source) {
            return new LogisticsTimeEntity(source);
        }

        @Override
        public LogisticsTimeEntity[] newArray(int size) {
            return new LogisticsTimeEntity[size];
        }
    };
}