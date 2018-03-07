package com.android.wool.view.activity;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.SpecialListEntity;
/**
 * Created by AiMr on 2017/10/28
 */
public interface SpecialListView extends IBaseView{
    void initView();
    void getEntity(SpecialListEntity entity);
}
