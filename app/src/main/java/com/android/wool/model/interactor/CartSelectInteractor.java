package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/19
 */
public interface CartSelectInteractor {
    void cartSelect(Context context,String cart_id,CartSelectListener listener);
    interface CartSelectListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
