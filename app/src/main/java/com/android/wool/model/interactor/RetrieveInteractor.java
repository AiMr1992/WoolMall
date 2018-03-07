package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/9/22
 */
public interface RetrieveInteractor {
    void commit(String Phone,String Code,RetrieveListener listener);
    interface RetrieveListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
