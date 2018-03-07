package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
/**
 * Created by AiMr on 2017/10/17
 */
public class SpecEntity implements ListItem{
    private String title;
    private List<ValueEntity> value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ValueEntity> getValue() {
        return value;
    }

    public void setValue(List<ValueEntity> value) {
        this.value = value;
    }

    @Override
    public SpecEntity newObject() {
        return new SpecEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setTitle(JsonUtil.getString(json,"title"));
        setValue(JsonUtil.parsFromData("value",json,new ValueEntity()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeTypedList(this.value);
    }

    public SpecEntity() {
    }

    protected SpecEntity(Parcel in) {
        this.title = in.readString();
        this.value = in.createTypedArrayList(ValueEntity.CREATOR);
    }

    public static final Creator<SpecEntity> CREATOR = new Creator<SpecEntity>() {
        @Override
        public SpecEntity createFromParcel(Parcel source) {
            return new SpecEntity(source);
        }

        @Override
        public SpecEntity[] newArray(int size) {
            return new SpecEntity[size];
        }
    };
}
