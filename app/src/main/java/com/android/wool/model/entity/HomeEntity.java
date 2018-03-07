package com.android.wool.model.entity;
import android.os.Parcel;
import com.android.wool.model.network.ListItem;
import com.android.wool.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
/**
 * Created by AiMr on 2017/9/27
 */
public class HomeEntity implements ListItem{
    private List<BannerEntity> ad_list;
    private List<BannerEntity> ad_img;
    private List<RecommendEntity> recommend;

    @Override
    public HomeEntity newObject() {
        return new HomeEntity();
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setAd_list(JsonUtil.parsFromData("ad_list",json,new BannerEntity()));
        setAd_img(JsonUtil.parsFromData("ad_img",json,new BannerEntity()));
        setRecommend(JsonUtil.parsFromData("recommend",json,new RecommendEntity()));
    }

    public List<BannerEntity> getAd_list() {
        return ad_list;
    }

    public void setAd_list(List<BannerEntity> ad_list) {
        this.ad_list = ad_list;
    }

    public List<BannerEntity> getAd_img() {
        return ad_img;
    }

    public void setAd_img(List<BannerEntity> ad_img) {
        this.ad_img = ad_img;
    }

    public List<RecommendEntity> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<RecommendEntity> recommend) {
        this.recommend = recommend;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.ad_list);
        dest.writeTypedList(this.ad_img);
        dest.writeTypedList(this.recommend);
    }

    public HomeEntity() {
    }

    protected HomeEntity(Parcel in) {
        this.ad_list = in.createTypedArrayList(BannerEntity.CREATOR);
        this.ad_img = in.createTypedArrayList(BannerEntity.CREATOR);
        this.recommend = in.createTypedArrayList(RecommendEntity.CREATOR);
    }

    public static final Creator<HomeEntity> CREATOR = new Creator<HomeEntity>() {
        @Override
        public HomeEntity createFromParcel(Parcel source) {
            return new HomeEntity(source);
        }

        @Override
        public HomeEntity[] newArray(int size) {
            return new HomeEntity[size];
        }
    };
}
