package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/10/16
 */
public class ExpressInfoEntity implements ListItem{
    private String id;
    private String order_id;
    private String express_company;
    private String express_no;
    private String add_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getExpress_company() {
        return express_company;
    }

    public void setExpress_company(String express_company) {
        this.express_company = express_company;
    }

    public String getExpress_no() {
        return express_no;
    }

    public void setExpress_no(String express_no) {
        this.express_no = express_no;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    @Override
    public ExpressInfoEntity newObject() {
        return new ExpressInfoEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setOrder_id(JsonUtil.getString(json,"order_id"));
        setExpress_company(JsonUtil.getString(json,"express_company"));
        setExpress_no(JsonUtil.getString(json,"express_no"));
        setAdd_time(JsonUtil.getString(json,"add_time"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.order_id);
        dest.writeString(this.express_company);
        dest.writeString(this.express_no);
        dest.writeString(this.add_time);
    }

    public ExpressInfoEntity() {
    }

    protected ExpressInfoEntity(Parcel in) {
        this.id = in.readString();
        this.order_id = in.readString();
        this.express_company = in.readString();
        this.express_no = in.readString();
        this.add_time = in.readString();
    }

    public static final Creator<ExpressInfoEntity> CREATOR = new Creator<ExpressInfoEntity>() {
        @Override
        public ExpressInfoEntity createFromParcel(Parcel source) {
            return new ExpressInfoEntity(source);
        }

        @Override
        public ExpressInfoEntity[] newArray(int size) {
            return new ExpressInfoEntity[size];
        }
    };
}