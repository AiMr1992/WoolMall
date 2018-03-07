package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/9/25
 */
public interface LocationUpdateInteractor {
    void update(Context context,String contact_name, String contact_phone, String address,
                String is_default,String province_id, String city_id, String country_id, String province_name,
                String city_name, String country_name, String id, LocationUpdateListener listener);
    interface LocationUpdateListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
