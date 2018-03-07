package com.android.wool.view.activity;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.PayTypeEntity;
import java.util.List;
/**
 * Created by AiMr on 2017/10/24
 */
public interface PayTypeView extends IBaseView{
    void initView();
    void payTypeList(List<PayTypeEntity> list);
}
