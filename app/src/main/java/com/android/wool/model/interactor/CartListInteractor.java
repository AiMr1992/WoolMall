package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/14
 */
public interface CartListInteractor {
    void getCartList(Context context,String page,CartListListener listener);
    interface CartListListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
