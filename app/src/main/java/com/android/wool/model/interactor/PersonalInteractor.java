package com.android.wool.model.interactor;
import android.content.Context;

import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/11
 */
public interface PersonalInteractor {
    void updateUserInfo(Context context,String name,String sex,String imgPath,PersonalListener listener);
    interface PersonalListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
