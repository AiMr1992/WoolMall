package com.android.wool.presenter;
import android.content.Context;
/**
 * Created by AiMr on 2017/10/13
 */

public interface GoodsInfoPresenter {
    void getGoodsInfo(Context context, String id);
    void update(Context context,String goods_id,String type);
    void joinCart(Context context,String goodsId, String goodsNum, String specArray);
    void goodsSpec(String id,String specId);
}
