package com.android.wool.view.activity;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.LogisticsTimeEntity;

import java.util.List;

/**
 * Created by AiMr on 2017/10/27
 */
public interface LogisticsListView extends IBaseView{
    void initView();
    void getLogistics(List<LogisticsTimeEntity> list);
}
