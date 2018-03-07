package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/9/26
 */
public interface HouseListInteractor {
    void house(Context context,String page,HouseListener listener);
    interface HouseListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
