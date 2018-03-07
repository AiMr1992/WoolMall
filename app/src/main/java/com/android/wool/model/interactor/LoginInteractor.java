package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/9/22
 */
public interface LoginInteractor {
    void login(String phone,String pwd,LoginListener listener);
    interface LoginListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
