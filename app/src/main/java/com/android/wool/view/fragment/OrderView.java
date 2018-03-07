package com.android.wool.view.fragment;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.OrderListEntity;
import java.util.List;
/**
 * Created by AiMr on 2017/10/12
 */
public interface OrderView extends IBaseView{
    String ORDER_CANCEL = "1";
    String ORDER_DELETE = "2";
    String ORDER_TAKE = "3";
    void showLoading();
    void disMissProgress();
    void getOrderList(List<OrderListEntity> list,boolean flag);
    void orderCancelSuccess(int position);
    void orderDelSuccess(int position);
    void orderTackSuccess(int position);
}
