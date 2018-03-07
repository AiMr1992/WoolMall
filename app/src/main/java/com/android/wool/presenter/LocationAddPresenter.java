package com.android.wool.presenter;
import android.content.Context;
/**
 * Created by AiMr on 2017/10/9
 */

public interface LocationAddPresenter {
    int LOCATION_ADD = 0;
    int LOCATION_UPDATE = 1;
    void switchPager(int position);
    void addLocation(Context context, String contact_name, String contact_phone, String address,
                     String is_default, String province_id, String city_id, String country_id, String province_name,
                     String city_name, String country_name);
    void updateLocation(Context context, String contact_name, String contact_phone, String address,
                     String is_default, String province_id, String city_id, String country_id, String province_name,
                     String city_name, String country_name, String id,int position);
    void delLocation(Context context,String id,int position);
}
