package com.android.wool.presenter;
import android.content.Context;
/**
 * Created by AiMr on 2017/10/12
 */
public interface OrderListPresenter {
    void getOrderList(Context context, String type,String pageSize);
    void editOrder(Context context, String order_id,String type,int position);
    void resetOrder(Context context, String order_id);
}
