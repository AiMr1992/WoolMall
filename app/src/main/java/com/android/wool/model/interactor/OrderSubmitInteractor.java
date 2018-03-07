package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/23
 */
public interface OrderSubmitInteractor {
    void submitOrder(Context context,String addr_id,String logistics_id,String activity_id,
                     String comment,OrderSubmitListener listener);
    interface OrderSubmitListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
