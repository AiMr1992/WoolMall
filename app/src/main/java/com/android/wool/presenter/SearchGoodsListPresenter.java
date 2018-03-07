package com.android.wool.presenter;
import android.content.Context;
/**
 * Created by AiMr on 2017/10/20
 */
public interface SearchGoodsListPresenter {
    void getSearchGoodsList(Context context,String keywords, String sales_num_order,
                            String price_order, String time_order,String page);
    void getTypeListId(Context context,String car_id, String sales_num_order, String price_order,String time_order,String page);
    void getIndexOrder(String type,String page);
}
