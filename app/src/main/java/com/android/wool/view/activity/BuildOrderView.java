package com.android.wool.view.activity;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.BuildOrderEntity;
/**
 * Created by AiMr on 2017/10/19
 */
public interface BuildOrderView extends IBaseView{
    void initView();
    void getBuildEntity(BuildOrderEntity entity);
    void startPayActivity(String total,String order_no,String orderId);
    void setPrice(String price,boolean add);
}
