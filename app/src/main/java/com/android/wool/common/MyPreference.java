package com.android.wool.common;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
/**
 * Created by AiMr on 2017/9/21.
 */
public class MyPreference {
    private static MyPreference myPreference;
    //sharedPreference文件名称
    private final String PREFERENCE_NAME = "im_preference";
    public static final String UID = "id";
    public static final String PHONE = "phone";
    public static final String FIRSER_INSERT_CITY = "insert_city";
    public static final String SEARCH_HISTORY = "history";
    public static final String USER_TYPE = "roles";//用户角色

    /** 构造方法 */
    private MyPreference(){}
    /** 获取类的实例 */
    synchronized  public static MyPreference getInstance(){
        if(myPreference == null){
            myPreference = new MyPreference();
        }
        return myPreference;
    }

    /** 将简单类型数据保存在sharedPrefrence中 */
    public boolean savePreferenceData(Context context, String key, String value){
        boolean result = false;
        SharedPreferences.Editor editor = getSharedPreferenceEditor(context);
        if(editor != null && (!TextUtils.isEmpty(key))){
            editor.putString(key, value);
            result = editor.commit();
        }
        return result;
    }

    /** 获取SharedPrefrencesEditor */
    public SharedPreferences.Editor getSharedPreferenceEditor(Context context){
        SharedPreferences preferences = context.getSharedPreferences
                (PREFERENCE_NAME, Activity.MODE_PRIVATE);
        if(preferences != null){
            return preferences.edit();
        }
        return null;
    }

    /** 获取跟key对应的值 */
    public String getPreferenceData(Context context, String key){
        String result = "";
        SharedPreferences preferences = context.getSharedPreferences
                (PREFERENCE_NAME, Activity.MODE_PRIVATE);
        if(preferences != null){
            result = preferences.getString(key,"");
        }
        return result;
    }

    public String getUid(Context context){
        return getPreferenceData(context,UID);
    }
}
