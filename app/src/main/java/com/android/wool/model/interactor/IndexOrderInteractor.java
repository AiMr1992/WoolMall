package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2018/1/26
 */
public interface IndexOrderInteractor {
    void getIndexOrder(String type,String page,IndexOrderListener listener);
    interface IndexOrderListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
