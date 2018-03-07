package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/9
 */
public interface LocationDelInteractor {
    void delLocation(String id, Context context,LocationDelListener listener);
    interface LocationDelListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
