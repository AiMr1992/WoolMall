package com.android.wool.model.network;
import com.android.wool.model.network.api.HttpConstant;
import com.android.wool.util.AppTools;
import com.android.wool.util.LogUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/9/21.
 */
public class ResponseMol {
    private JSONObject jsonObject;
    public ResponseMol(String result) {
        try {
            jsonObject = new JSONObject(result);
        }catch(JSONException e){
            LogUtil.e(e.getMessage());
        }
    }

    /**  判断returnCode是否0来判断是否调用成功接口 */
    public boolean isRequestSuccess(){
        boolean isSuccess = false;
        if(jsonObject != null && jsonObject.has(HttpConstant.API_ERROR_CODE)){
            try {
                if(jsonObject.getString(HttpConstant.API_ERROR_CODE)
                        .equals(HttpConstant.API_CODE_OK)){
                    isSuccess = true;
                }
            } catch (JSONException e) {
                LogUtil.e(e.getMessage());
            }
        }
        return isSuccess;
    }

    /** 返回ReturnCode */
    public String getReturnCode(){
        String result = "";
        if(jsonObject != null && jsonObject.has(HttpConstant.API_ERROR_CODE)){
            try {
                result = jsonObject.getString(HttpConstant.API_ERROR_CODE);
            } catch (JSONException e) {
                LogUtil.e(e.getMessage());
            }
        }
        return result;
    }

    public boolean isLoad(){
        if(jsonObject != null && jsonObject.has(HttpConstant.CURRENT_PAGE) && jsonObject.has(HttpConstant.COUNT_PAGE))
            return AppTools.isUpLoad(getString(HttpConstant.CURRENT_PAGE),getString(HttpConstant.COUNT_PAGE));
        else
            return false;
    }

    /** 获取接口返回信息 */
    public String getReturnMessage(){
        String result = "";
        if(jsonObject != null && jsonObject.has(HttpConstant.API_ERROR_RESULT)){
            try {
                result = jsonObject.getString(HttpConstant.API_ERROR_RESULT);
            } catch (JSONException e) {
                LogUtil.e(e.getMessage());
            }
        }
        return result;
    }

    public String getString(String key){
        String result = "";
        if(jsonObject != null && jsonObject.has(key)){
            try {
                result = jsonObject.getString(key);
            } catch (JSONException e) {
                LogUtil.e(e.getMessage());
            }
        }
        return result;
    }

    public String getStringForData(String key){
        String result = "";
        JSONObject jsonObj = getJsonData();
        if(jsonObj != null && jsonObj.has(key)){
            try {
                result = jsonObj.getString(key);
            } catch (JSONException e) {
                LogUtil.e(e.getMessage());
            }
        }
        return result;
    }

    public <T extends ListItem> List<T> parsFromData(String Key, T t){
        if(t == null){
            return null;
        }
        List<T> mList = null;
        if(jsonObject !=null && jsonObject.has(Key)){
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(Key);
                if(jsonArray != null && jsonArray.length() > 0){
                    mList = new ArrayList<T>();
                    T tempT = t;
                    for(int i=0;i<jsonArray.length();i++){
                        if(tempT == null){
                            tempT = (T)t.newObject();
                        }
                        tempT.parsFromJson(jsonArray.getJSONObject(i));
                        mList.add(tempT);
                        tempT = null;
                    }
                    return mList;
                }
            } catch (JSONException e) {
                LogUtil.e(e.getMessage());
            }
        }else{
            LogUtil.d(" jsonObject null or jsonObject no this key");
        }
        return null;
    }

    public <T extends ListItem> T getEntity(String key,T t){
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

    public <T extends ListItem> T getEntityFromList(String key,T t){
        if(t == null)
            return null;
        JSONObject jsonObj = getJsonData();
        if(jsonObj !=null && jsonObj.has(key)){
            try{
                JSONObject json = jsonObj.getJSONObject(key);
                t.parsFromJson(json);
                return t;
            }catch(JSONException e){
                LogUtil.e(e.getMessage());
            }
        }
        return null;
    }

    public <T extends ListItem> List<T> parsFromList(String Key,T t){
        if(t == null){
            return null;
        }
        List<T> mList = null;
        JSONObject object = getJsonData();
        if(object !=null && object.has(Key)){
            try {
                JSONArray jsonArray = object.getJSONArray(Key);
                if(jsonArray != null && jsonArray.length() > 0){
                    mList = new ArrayList<T>();
                    T tempT = t;
                    for(int i=0;i<jsonArray.length();i++){
                        if(tempT == null){
                            tempT = (T)t.newObject();
                        }
                        tempT.parsFromJson(jsonArray.getJSONObject(i));
                        mList.add(tempT);
                        tempT = null;
                    }
                    return mList;
                }
            } catch (JSONException e) {
                LogUtil.e(e.getMessage());
            }
        }else{
            LogUtil.d(" jsonobject null or jsonObject no this key");
        }
        return null;
    }

    public JSONObject getJsonData(){
        if(jsonObject != null && jsonObject.has("list")){
            try {
                return jsonObject.getJSONObject("list");
            }catch(JSONException e){
                LogUtil.e(e.getMessage());
            }
        }
        return null;
    }

    public List<String> getStringList(String Key){
        List<String> mList = null;
        if(jsonObject != null && jsonObject.has("list")){
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                if(jsonArray != null && jsonArray.length() > 0){
                    mList = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){
                        mList.add(jsonArray.getJSONObject(i).getString(Key));
                    }
                    return mList;
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }else{
            LogUtil.d(" jsonobject null or jsonObject no this key");
        }
        return null;
    }

    @Override
    public String toString() {
        return jsonObject != null ? jsonObject.toString() : "";
    }
}
