package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/12
 */
public interface OrderListInteractor {
    void getOrderList(Context context,String type,String pageSize,OrderListListener listener);
    interface OrderListListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
