package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import com.android.wool.util.LogUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2018/1/24
 * 页
 */
public class RecommendEntity implements ListItem{
    private String id;
    private String title;
    private String desc;
    private String logo;
    private List<String> goods_list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<String> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<String> goods_list) {
        this.goods_list = goods_list;
    }

    @Override
    public RecommendEntity newObject() {
        return new RecommendEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setTitle(JsonUtil.getString(json,"title"));
        setDesc(JsonUtil.getString(json,"desc"));
        setLogo(JsonUtil.getString(json,"logo"));
        parsJson(json);
    }

    private void parsJson(JSONObject jsonObject){
        if(jsonObject != null && jsonObject.has("goods_list")){
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("goods_list");
                if(jsonArray != null && jsonArray.length() > 0){
                    goods_list = new ArrayList<>();
                    for (int i = 0 ; i < jsonArray.length(); i ++){
                        goods_list.add(jsonArray.toString());
                    }
                }
            }catch (Exception e){
                LogUtil.e(e.getMessage());
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.logo);
        dest.writeStringList(this.goods_list);
    }

    public RecommendEntity() {
    }

    protected RecommendEntity(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.desc = in.readString();
        this.logo = in.readString();
        this.goods_list = in.createStringArrayList();
    }

    public static final Creator<RecommendEntity> CREATOR = new Creator<RecommendEntity>() {
        @Override
        public RecommendEntity createFromParcel(Parcel source) {
            return new RecommendEntity(source);
        }

        @Override
        public RecommendEntity[] newArray(int size) {
            return new RecommendEntity[size];
        }
    };
}
