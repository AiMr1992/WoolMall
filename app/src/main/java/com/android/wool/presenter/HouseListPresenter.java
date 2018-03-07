package com.android.wool.presenter;
import android.content.Context;
import com.android.wool.model.entity.HouseListEntity;
/**
 * Created by AiMr on 2017/9/26
 */
public interface HouseListPresenter {
    void getHouseList(Context context,String page);
    void update(Context context, HouseListEntity entity, String type, int position);
}
