package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/27
 */
public interface LogisticsInteractor {
    void getLogisticsInfo(String order_no,LogisticsListener listener);
    interface LogisticsListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
