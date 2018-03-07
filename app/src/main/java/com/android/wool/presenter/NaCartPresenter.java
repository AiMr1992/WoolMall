package com.android.wool.presenter;
import android.content.Context;
/**
 * Created by AiMr on 2017/10/14
 */
public interface NaCartPresenter {
    void getCartList(Context context, String page);
    void delCart(Context context,String cart_id,int position);
    void selectCart(Context context,String cart_id,int position,boolean select);
    void changeCart(Context context,String cart_id,String Goods_num,int position,boolean isAdd);
}
