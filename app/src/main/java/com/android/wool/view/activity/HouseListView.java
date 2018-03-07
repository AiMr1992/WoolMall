package com.android.wool.view.activity;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.HouseListEntity;
import java.util.List;
/**
 * Created by AiMr on 2017/9/26
 */
public interface HouseListView extends IBaseView{
    void initView();
    void getHouseList(List<HouseListEntity> list,boolean flag);
    void remove(int position);
}
