package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/9/22
 */
public interface SendMsgInteractor {
    void sendMsg(String phone,String type,RegisterListener listener);
    interface RegisterListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
