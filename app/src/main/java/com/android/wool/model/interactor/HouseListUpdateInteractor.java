package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/9/26
 */
public interface HouseListUpdateInteractor {
    void update(Context context,String goods_id,String type,HouseListUpdateListener listener);
    interface HouseListUpdateListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
