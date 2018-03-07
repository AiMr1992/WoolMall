package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2018/1/15
 */
public interface QiNiuTokenInteractor {
    void qiNiuToken(QiNiuTokenListener listener);
    interface QiNiuTokenListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
