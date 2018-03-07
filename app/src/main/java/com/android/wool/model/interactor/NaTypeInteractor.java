package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/9/28
 */
public interface NaTypeInteractor {
    void getTypeGoodsList(NaTypeListener listener);
    interface NaTypeListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
