package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2018/1/26
 */
public interface IndexRecommendInteractor {
    void getIndexRecommendList(String page,IndexRecommendListener listener);
    interface IndexRecommendListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
