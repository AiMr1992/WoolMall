package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/11/7
 */
public interface OrderResetInteractor {
    void resetOrder(Context context,String order_id,OrderResetListener listener);
    interface OrderResetListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
