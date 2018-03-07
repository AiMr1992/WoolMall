package com.android.wool.presenter;
import android.content.Context;
import com.android.wool.model.entity.LocationEntity;
/**
 * Created by AiMr on 2017/9/26
 */
public interface LocationListPresenter {
    void getLocationList(Context context,String page);
    void delLocation(String id,Context context,int position);
    void updateDefault(Context context, LocationEntity entity,int position);
    void finish();
}
