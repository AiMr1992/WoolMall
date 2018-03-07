package com.android.wool.presenter;
import android.content.Context;
/**
 * Created by AiMr on 2017/10/19
 */
public interface BuildOrderPresenter {
    void buildOrder(Context context);
    void submitOrder(Context context, String addr_id, String logistics_id, String activity_id,
                     String comment);
    void setPrice(String price,boolean add);
}
