package com.android.wool.presenter;
import android.content.Context;
import com.android.wool.model.entity.GoodsCartsEntity;
/**
 * Created by AiMr on 2017/9/28
 */
public interface NaTypePresenter {
    void getTypeGoodsList(Context context);
    void refreshRight(GoodsCartsEntity entity);
}
