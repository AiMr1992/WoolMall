package com.android.wool.model.interactor;
import android.content.Context;
import android.text.TextUtils;
import com.android.wool.common.MyPreference;
import com.android.wool.model.network.ConnHttpHelper;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.model.network.api.HttpConstant;
import com.android.wool.model.network.subscribers.EventSubscriber;
import com.android.wool.model.network.subscribers.SubscriberOnNextListener;
import java.util.TreeMap;
/**
 * Created by AiMr on 2017/9/25
 */
public class LocationUpdateInteractorImpl implements LocationUpdateInteractor {
    @Override
    public void update(Context context,String contact_name, String contact_phone, String address,
                       String is_default, String province_id, String city_id, String country_id,
                       String province_name, String city_name, String country_name, String id,
                       final LocationUpdateListener listener) {
        String uid = MyPreference.getInstance().getUid(context);
        TreeMap<String,String> map = new TreeMap<>();
        map.put("uid",uid);
        map.put("contact_name",contact_name);
        map.put("contact_phone",contact_phone);
        map.put("address",address);
        map.put("is_default",is_default);
        map.put("province_id",province_id);
        map.put("city_id",city_id);
        map.put("country_id",country_id);
        map.put("province_name",province_name);
        map.put("city_name",city_name);
        map.put("country_name",country_name);
        if(!TextUtils.isEmpty(id))
            map.put("id",id);
        ConnHttpHelper.getInstance().getServerApiPost(
                new EventSubscriber<ResponseMol>(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(ResponseMol responseMol) {
                        if(listener != null){
                            if(responseMol != null){
                                if(responseMol.isRequestSuccess()){
                                    listener.onSuccess(responseMol);
                                }else {
                                    listener.onError(responseMol.getReturnMessage());
                                }
                            }else {
                                listener.onFailed();
                            }
                        }
                    }
                }),
                HttpConstant.LOCATION_UPDATE,
                map);
    }
}
