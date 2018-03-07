package com.android.wool.model.entity;
import android.os.Parcel;

import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/9/26
 */
public class LocationEntity implements ListItem{
    private String id;
    private String contact_name;
    private String contact_phone;
    private String city;
    private String address;
    private String is_default;
    private String province_id;
    private String city_id;
    private String country_id;
    private String province_name;
    private String city_name;
    private String country_name;

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setContact_name(JsonUtil.getString(json,"contact_name"));
        setContact_phone(JsonUtil.getString(json,"contact_phone"));
        setCity(JsonUtil.getString(json,"city"));
        setAddress(JsonUtil.getString(json,"address"));
        setIs_default(JsonUtil.getString(json,"is_default"));
        setProvince_id(JsonUtil.getString(json,"province_id"));
        setCity_id(JsonUtil.getString(json,"city_id"));
        setCountry_id(JsonUtil.getString(json,"country_id"));
        setProvince_name(JsonUtil.getString(json,"province_name"));
        setCity_name(JsonUtil.getString(json,"city_name"));
        setCountry_name(JsonUtil.getString(json,"country_name"));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    @Override
    public LocationEntity newObject() {
        return new LocationEntity();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.contact_name);
        dest.writeString(this.contact_phone);
        dest.writeString(this.city);
        dest.writeString(this.address);
        dest.writeString(this.is_default);
        dest.writeString(this.province_id);
        dest.writeString(this.city_id);
        dest.writeString(this.country_id);
        dest.writeString(this.province_name);
        dest.writeString(this.city_name);
        dest.writeString(this.country_name);
    }

    public LocationEntity() {
    }

    protected LocationEntity(Parcel in) {
        this.id = in.readString();
        this.contact_name = in.readString();
        this.contact_phone = in.readString();
        this.city = in.readString();
        this.address = in.readString();
        this.is_default = in.readString();
        this.province_id = in.readString();
        this.city_id = in.readString();
        this.country_id = in.readString();
        this.province_name = in.readString();
        this.city_name = in.readString();
        this.country_name = in.readString();
    }

    public static final Creator<LocationEntity> CREATOR = new Creator<LocationEntity>() {
        @Override
        public LocationEntity createFromParcel(Parcel source) {
            return new LocationEntity(source);
        }

        @Override
        public LocationEntity[] newArray(int size) {
            return new LocationEntity[size];
        }
    };
}
