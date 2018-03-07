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
 * Created by AiMr on 2017/10/13
 */
public class GoodsInfoEntity implements ListItem {
    private String id;
    private String title;
    private String pics;
    private String price;
    private String market_price;
    private String sales_num;
    private String content;
    private String attr_ids;
    private String stock;
    private List<SpecEntity> spce_array;
    private String is_collection;
    private List<IntroEntity> intro;
    private List<ProductsEntity> products;

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

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getSales_num() {
        return sales_num;
    }

    public void setSales_num(String sales_num) {
        this.sales_num = sales_num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttr_ids() {
        return attr_ids;
    }

    public void setAttr_ids(String attr_ids) {
        this.attr_ids = attr_ids;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public List<SpecEntity> getSpce_array() {
        return spce_array;
    }

    public void setSpce_array(List<SpecEntity> spce_array) {
        this.spce_array = spce_array;
    }

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
    }

    public List<IntroEntity> getIntro() {
        return intro;
    }

    public void setIntro(List<IntroEntity> intro) {
        this.intro = intro;
    }

    public List<ProductsEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsEntity> products) {
        this.products = products;
    }

    @Override
    public void parsFromJson(JSONObject json) throws JSONException {
        setId(JsonUtil.getString(json, "id"));
        setTitle(JsonUtil.getString(json, "title"));
        setPics(JsonUtil.getString(json, "pics"));
        setPrice(JsonUtil.getString(json, "price"));
        setMarket_price(JsonUtil.getString(json, "market_price"));
        setSales_num(JsonUtil.getString(json, "sales_num"));
        setContent(JsonUtil.getString(json, "content"));
        setAttr_ids(JsonUtil.getString(json,"attr_ids"));
        setStock(JsonUtil.getString(json,"stock"));
        setSpce_array(JsonUtil.parsFromData("spce_array",json,new SpecEntity()));
        setIs_collection(JsonUtil.getString(json,"is_collection"));
        setIntro(JsonUtil.parsFromData("intro", json, new IntroEntity()));
        setProducts(JsonUtil.parsFromData("products",json,new ProductsEntity()));
    }

    @Override
    public GoodsInfoEntity newObject() {
        return new GoodsInfoEntity();
    }

    public List<String> getChild() {
        if (!TextUtils.isEmpty(pics)) {
            try {
                List<String> mList = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(pics);
                for (int i = 0; i < jsonArray.length(); i++) {
                    mList.add(jsonArray.getString(i));
                }
                return mList;
            } catch (JSONException e) {
                LogUtil.e(e.getMessage());
            }
        }
        return null;
    }

    public int getSize(){
        if(spce_array != null && spce_array.size() > 0){
            return spce_array.size();
        }
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.pics);
        dest.writeString(this.price);
        dest.writeString(this.market_price);
        dest.writeString(this.sales_num);
        dest.writeString(this.content);
        dest.writeString(this.attr_ids);
        dest.writeString(this.stock);
        dest.writeTypedList(this.spce_array);
        dest.writeString(this.is_collection);
        dest.writeTypedList(this.intro);
        dest.writeTypedList(this.products);
    }

    public GoodsInfoEntity() {
    }

    protected GoodsInfoEntity(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.pics = in.readString();
        this.price = in.readString();
        this.market_price = in.readString();
        this.sales_num = in.readString();
        this.content = in.readString();
        this.attr_ids = in.readString();
        this.stock = in.readString();
        this.spce_array = in.createTypedArrayList(SpecEntity.CREATOR);
        this.is_collection = in.readString();
        this.intro = in.createTypedArrayList(IntroEntity.CREATOR);
        this.products = in.createTypedArrayList(ProductsEntity.CREATOR);
    }

    public static final Creator<GoodsInfoEntity> CREATOR = new Creator<GoodsInfoEntity>() {
        @Override
        public GoodsInfoEntity createFromParcel(Parcel source) {
            return new GoodsInfoEntity(source);
        }

        @Override
        public GoodsInfoEntity[] newArray(int size) {
            return new GoodsInfoEntity[size];
        }
    };
}