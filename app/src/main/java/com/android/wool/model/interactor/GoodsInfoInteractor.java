package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/13
 */
public interface GoodsInfoInteractor {
    void getGoodsInfo(Context context,String id, GoodsInfoListener listener);
    interface GoodsInfoListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
