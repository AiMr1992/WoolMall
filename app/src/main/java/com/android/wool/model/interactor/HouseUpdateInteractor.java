package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/17
 */
public interface HouseUpdateInteractor {
    void update(Context context,String goods_id,String type,HouseUpdateListener listener);
    interface HouseUpdateListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
