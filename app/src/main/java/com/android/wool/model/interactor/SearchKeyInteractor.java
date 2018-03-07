package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/14
 */
public interface SearchKeyInteractor {
    void getHotSearch(HotSearchListener listener);
    interface HotSearchListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
