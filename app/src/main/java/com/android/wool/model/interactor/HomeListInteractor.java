package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/9/27
 */
public interface HomeListInteractor {
    void getList(Context context,HomeListListener listener);
    interface HomeListListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
