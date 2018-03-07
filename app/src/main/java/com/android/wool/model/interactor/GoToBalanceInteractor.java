package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/23
 */
public interface GoToBalanceInteractor {
    void getConfirm(Context context,GoToBalanceListener listener);
    interface GoToBalanceListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
