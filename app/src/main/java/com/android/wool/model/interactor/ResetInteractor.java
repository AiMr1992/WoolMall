package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/9/22
 */
public interface ResetInteractor {
    void commit(String Phone,String Pwd,String Pwd2,ResetListener listener);
    interface ResetListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
