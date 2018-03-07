package com.android.wool.model.interactor;
import android.content.Context;

import com.android.wool.model.network.ResponseMol;

/**
 * Created by AiMr on 2017/9/26
 */
public interface LocationListInteractor {
    void getLocation(Context context,String page,LocationListListener listener);
    interface LocationListListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
