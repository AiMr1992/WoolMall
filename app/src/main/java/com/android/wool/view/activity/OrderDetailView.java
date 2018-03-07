package com.android.wool.view.activity;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.LogisticsTimeEntity;
import com.android.wool.model.entity.OrderDetailEntity;
/**
 * Created by AiMr on 2017/10/16
 */
public interface OrderDetailView extends IBaseView{
    //订单状态
    String ORDER_NONE_PLAY = "1";//未付款
    String ORDER_TACK_N = "2";//待发货
    String ORDER_TACK_Y = "3";//已发货
    String ORDER_FINISH = "4";//已完成
    String ORDER_TACK = "5";//取消

    //处理方式
    String ORDER_CANCEL = "1";
    String ORDER_DELETE = "2";
    String ORDER_TAKE = "3";

    //页面类型
    String ORDER_PAGER_ALL = "0";
    String ORDER_NONE_PAGER_PLAY = "1";
    String ORDER_PAGER_TAKE = "2";
    String ORDER_PAGER_FINISH = "3";

    void getOrderDetailEntity(OrderDetailEntity entity);
    void initView();
    void getLogistics(LogisticsTimeEntity entity);

    void editSuccess(int position,String type);
}
