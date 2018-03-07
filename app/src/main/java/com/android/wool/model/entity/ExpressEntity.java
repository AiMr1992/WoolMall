package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
/**
 * Created by AiMr on 2017/10/16
 */
public class ExpressEntity implements ListItem{
    private ExpressInfoEntity info;
    private List<ExpressListEntity> lsit;

    public ExpressInfoEntity getInfo() {
        return info;
    }

    public void setInfo(ExpressInfoEntity info) {
        this.info = info;
    }

    public List<ExpressListEntity> getLsit() {
        return lsit;
    }

    public void setLsit(List<ExpressListEntity> lsit) {
        this.lsit = lsit;
    }

    @Override
    public ExpressEntity newObject() {
        return new ExpressEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setInfo(JsonUtil.parsEntity("info",json,new ExpressInfoEntity()));
        setLsit(JsonUtil.parsFromData("list",json,new ExpressListEntity()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.info, flags);
        dest.writeTypedList(this.lsit);
    }

    public ExpressEntity() {
    }

    protected ExpressEntity(Parcel in) {
        this.info = in.readParcelable(ExpressInfoEntity.class.getClassLoader());
        this.lsit = in.createTypedArrayList(ExpressListEntity.CREATOR);
    }

    public static final Creator<ExpressEntity> CREATOR = new Creator<ExpressEntity>() {
        @Override
        public ExpressEntity createFromParcel(Parcel source) {
            return new ExpressEntity(source);
        }

        @Override
        public ExpressEntity[] newArray(int size) {
            return new ExpressEntity[size];
        }
    };
}
