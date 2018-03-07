package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/10/13
 */
public class IntroEntity implements ListItem{
    private String title;
    private String logo;

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

    @Override
    public IntroEntity newObject() {
        return new IntroEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setTitle(JsonUtil.getString(json,"title"));
        setLogo(JsonUtil.getString(json,"logo"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.logo);
    }

    public IntroEntity() {
    }

    protected IntroEntity(Parcel in) {
        this.title = in.readString();
        this.logo = in.readString();
    }

    public static final Creator<IntroEntity> CREATOR = new Creator<IntroEntity>() {
        @Override
        public IntroEntity createFromParcel(Parcel source) {
            return new IntroEntity(source);
        }

        @Override
        public IntroEntity[] newArray(int size) {
            return new IntroEntity[size];
        }
    };
}
