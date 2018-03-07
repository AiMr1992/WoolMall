package com.android.wool.view.activity;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.LocationEntity;
import java.util.List;
/**
 * Created by AiMr on 2017/9/26
 */
public interface LocationListView extends IBaseView{
    void getLocationList(List<LocationEntity> list,boolean flag);
    void deleteLocation(int position);
    void updateLocationDefault(int position);
    void finishPager();
}