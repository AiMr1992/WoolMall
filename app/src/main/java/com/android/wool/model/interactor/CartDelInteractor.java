package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/18
 */
public interface CartDelInteractor {
    void delCart(Context context,String cart_id,CartDelListener listener);
    interface CartDelListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
