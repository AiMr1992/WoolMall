package com.android.wool.model.entity;
import android.os.Parcel;
import android.text.TextUtils;
import com.android.wool.model.db.DBCityManager;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import com.android.wool.util.LogUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/9/26
 */
@Table(name = "city_info")
public class CityInfoEntity implements ListItem{
    @Column(name = "city_code",isId = true)
    private String id;
    @Column(name = "city_name")
    private String region_name;
    @Column(name = "p_city_code")
    private String partend_id;
    private String city;
    private String pinyin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getPartend_id() {
        return partend_id;
    }

    public void setPartend_id(String partend_id) {
        this.partend_id = partend_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public CityInfoEntity newObject() {
        return new CityInfoEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setRegion_name(JsonUtil.getString(json,"region_name"));
        setPartend_id(JsonUtil.getString(json,"partend_id"));
        setCity(JsonUtil.getString(json,"city"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.region_name);
        dest.writeString(this.partend_id);
        dest.writeString(this.city);
        dest.writeString(this.pinyin);
    }

    public CityInfoEntity() {
    }

    protected CityInfoEntity(Parcel in) {
        this.id = in.readString();
        this.region_name = in.readString();
        this.partend_id = in.readString();
        this.city = in.readString();
        this.pinyin = in.readString();
    }

    public static final Creator<CityInfoEntity> CREATOR = new Creator<CityInfoEntity>() {
        @Override
        public CityInfoEntity createFromParcel(Parcel source) {
            return new CityInfoEntity(source);
        }

        @Override
        public CityInfoEntity[] newArray(int size) {
            return new CityInfoEntity[size];
        }
    };

    /**  得到子城市 */
    public List<CityInfoEntity> getChild(){
        if(!TextUtils.isEmpty(city)){
            try {
                List<CityInfoEntity> mlist = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(city);
                for(int i = 0; i < jsonArray.length(); i ++){
                    CityInfoEntity entity = new CityInfoEntity();
                    entity.parsFromJson(jsonArray.getJSONObject(i));
                    mlist.add(entity);
                }
                return mlist;
            }catch(JSONException e){
                LogUtil.e(e.getMessage());
            }
        }
        return null;
    }

    /** 插入数据 */
    public void insertSelfAndChild(DBCityManager dbCityManager){
        dbCityManager.insertData(this);
        if(!TextUtils.isEmpty(city)){
            try {
                JSONArray jsonArray = new JSONArray(city);
                for(int i = 0; i < jsonArray.length(); i ++){
                    CityInfoEntity entity = new CityInfoEntity();
                    entity.parsFromJson(jsonArray.getJSONObject(i));
                    dbCityManager.insertData(entity);
                    entity.insertSelfAndChild(dbCityManager);
                }
            }catch(JSONException e){
                LogUtil.e(e.getMessage());
            }
        }
    }
}