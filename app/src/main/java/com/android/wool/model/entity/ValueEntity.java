package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AiMr on 2017/12/7
 */
public class ValueEntity implements ListItem{
    private String id;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public ValueEntity newObject() {
        return new ValueEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setValue(JsonUtil.getString(json,"value"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.value);
    }

    public ValueEntity() {
    }

    protected ValueEntity(Parcel in) {
        this.id = in.readString();
        this.value = in.readString();
    }

    public static final Creator<ValueEntity> CREATOR = new Creator<ValueEntity>() {
        @Override
        public ValueEntity createFromParcel(Parcel source) {
            return new ValueEntity(source);
        }

        @Override
        public ValueEntity[] newArray(int size) {
            return new ValueEntity[size];
        }
    };
}
