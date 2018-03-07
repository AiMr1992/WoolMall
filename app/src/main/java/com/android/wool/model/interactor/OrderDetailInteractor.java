package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/16
 */
public interface OrderDetailInteractor {
    void getOrderDetail(Context context,String order_id,OrderDetailListener listener);
    interface OrderDetailListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
