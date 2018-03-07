package com.android.wool.util;
import com.android.wool.model.network.ListItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by PC on 2015/12/7.
 */
public class JsonUtil {
    /**
     * 获取字符类型数据
     * @param key
     * @return
     */
    public static String getString(JSONObject json,String key){
        String result = "";
        if (json != null && json.has(key)) {
            try {
                result = json.getString(key);
            } catch (JSONException e) {
                LogUtil.e(e.getMessage());
            }
        }
        return result;
    }

    /**
     * 解析数组，将数组转换成对应的对象，对象需实现ListItem接口
     * 直接从data中去列表
     * @param Key
     * @param t
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends ListItem> List<T> parsFromData(String Key, JSONObject jsonObject, T t){
        if(t == null){
            return null;
        }
        List<T> mlist = null;
        if(jsonObject !=null && jsonObject.has(Key)){
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(Key);
                if(jsonArray != null && jsonArray.length() > 0){
                    mlist = new ArrayList<T>();
                    T tempT = t;
                    for(int i=0;i<jsonArray.length();i++){
                        if(tempT == null){
                            tempT = (T)t.newObject();
                        }
                        tempT.parsFromJson(jsonArray.getJSONObject(i));
                        mlist.add(tempT);
                        tempT = null;
                    }
                    return mlist;
                }
            } catch (JSONException e) {
                LogUtil.e(e.getMessage());
            }
        }else{
            LogUtil.d(" jsonobject null or jsonObject no this key");
        }
        return null;
    }

    public static <T extends ListItem> T parsEntity(String key, JSONObject jsonObject, T t){
        if(t == null)
            return null;
        if(jsonObject !=null && jsonObject.has(key)){
            try{
                JSONObject json = jsonObject.getJSONObject(key);
                t.parsFromJson(json);
                return t;
            }catch(JSONException e){
                LogUtil.e(e.getMessage());
            }
        }
        return null;
    }
}
