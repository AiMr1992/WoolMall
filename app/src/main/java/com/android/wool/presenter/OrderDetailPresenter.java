package com.android.wool.presenter;
import android.content.Context;
/**
 * Created by AiMr on 2017/10/16
 */
public interface OrderDetailPresenter {
    void getOrderDetail(Context context, String orderId);
    void editOrder(Context context, String order_id,String type,int position);
    void getLogistics(Context context,String order_no);
    void resetOrder(Context context, String order_id);
}
