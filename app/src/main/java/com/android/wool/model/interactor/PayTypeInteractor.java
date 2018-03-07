package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/24
 */
public interface PayTypeInteractor {
    void payTypeList(PayTypeListener listener);
    interface PayTypeListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
