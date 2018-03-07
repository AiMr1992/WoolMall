package com.android.wool.model.entity;
import android.os.Parcel;
import android.text.TextUtils;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import com.android.wool.util.LogUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/9/28
 */
public class GoodsCartsEntity implements ListItem{
    private String id;
    private String title;
    private String logo;
    private String next;
    private String pid;
    private boolean isShow;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    public GoodsCartsEntity newObject() {
        return new GoodsCartsEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json,"id"));
        setPid(JsonUtil.getString(json,"pid"));
        setTitle(JsonUtil.getString(json,"title"));
        setLogo(JsonUtil.getString(json,"logo"));
        setNext(JsonUtil.getString(json,"next"));
    }

    public GoodsCartsEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.logo);
        dest.writeString(this.next);
        dest.writeString(this.pid);
        dest.writeByte(this.isShow ? (byte) 1 : (byte) 0);
    }

    protected GoodsCartsEntity(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.logo = in.readString();
        this.next = in.readString();
        this.pid = in.readString();
        this.isShow = in.readByte() != 0;
    }

    public static final Creator<GoodsCartsEntity> CREATOR = new Creator<GoodsCartsEntity>() {
        @Override
        public GoodsCartsEntity createFromParcel(Parcel source) {
            return new GoodsCartsEntity(source);
        }

        @Override
        public GoodsCartsEntity[] newArray(int size) {
            return new GoodsCartsEntity[size];
        }
    };

    public List<GoodsCartsEntity> getChild(){
        if(!TextUtils.isEmpty(next)){
            try {
                List<GoodsCartsEntity> mList = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(next);
                for(int i = 0; i < jsonArray.length(); i ++){
                    GoodsCartsEntity entity = new GoodsCartsEntity();
                    entity.parsFromJson(jsonArray.getJSONObject(i));
                    mList.add(entity);
                }
                return mList;
            }catch(JSONException e){
                LogUtil.e(e.getMessage());
            }
        }
        return null;
    }
}
