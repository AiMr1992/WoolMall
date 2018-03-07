package com.android.wool.common;
import android.content.Context;
import com.umeng.analytics.dplus.UMADplus;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by AiMr on 2018/1/12.
 */
public class UMEventUtils {
    public static void pushEvent(Context context, String eventName,Map<String,String> labelMap){
        Map map = null;
        if(labelMap != null) {
            map = new HashMap();
            for (Map.Entry<String, String> entry : labelMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                map.put(key,value);
            }
        }
        UMADplus.track(context,eventName,map);
    }
}