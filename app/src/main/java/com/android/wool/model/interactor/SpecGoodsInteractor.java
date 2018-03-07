package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/12/8
 */
public interface SpecGoodsInteractor {
    void goodsSpec(String id,String spec_id,SpecGoodsListener listener);
    interface SpecGoodsListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
