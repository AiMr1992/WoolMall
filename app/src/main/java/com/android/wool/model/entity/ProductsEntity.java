package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/10/17
 */
public class ProductsEntity implements ListItem{
    private String value;
    private String name;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ProductsEntity newObject() {
        return new ProductsEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setValue(JsonUtil.getString(json,"value"));
        setName(JsonUtil.getString(json,"name"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.value);
        dest.writeString(this.name);
    }

    public ProductsEntity() {
    }

    protected ProductsEntity(Parcel in) {
        this.value = in.readString();
        this.name = in.readString();
    }

    public static final Creator<ProductsEntity> CREATOR = new Creator<ProductsEntity>() {
        @Override
        public ProductsEntity createFromParcel(Parcel source) {
            return new ProductsEntity(source);
        }

        @Override
        public ProductsEntity[] newArray(int size) {
            return new ProductsEntity[size];
        }
    };
}
