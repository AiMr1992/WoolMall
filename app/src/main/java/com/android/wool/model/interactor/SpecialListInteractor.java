package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/28
 */
public interface SpecialListInteractor {
    void getSpecialList(String id,String page,SpecialListListener listener);
    interface SpecialListListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
