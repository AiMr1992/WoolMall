package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2018/1/19
 */
public interface MaterialListInteractor {
    void getMaterialList(String page,MaterialListListener listener);
    interface MaterialListListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
