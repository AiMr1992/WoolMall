package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/20
 */
public interface OrderEditInteractor {
    void editOrder(Context context,String order_id,String type,OrderEditListener listener);
    interface OrderEditListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
