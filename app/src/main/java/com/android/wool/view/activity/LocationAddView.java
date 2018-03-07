package com.android.wool.view.activity;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.LocationEntity;
/**
 * Created by AiMr on 2017/10/9
 */
public interface LocationAddView extends IBaseView{
    void addLocationTitle();
    void updateLocationTitle();
    void addLocation();
    void updateLocation();
    void addLocationSuccess(LocationEntity entity);
    void updateLocationSuccess(LocationEntity entity,int position);
    void delLocationSuccess(int position);
}
