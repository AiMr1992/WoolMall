package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ResponseMol;
import java.io.File;
/**
 * Created by AiMr on 2017/10/11
 */
public interface UpLoadFileInteractor {
    void uploadFile(File file,UpLoadListener listener);
    interface UpLoadListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
    void upLoadQiNiuFile(File file,String token,UpLoadQiNiuListener listener, int type, Context context);
    interface UpLoadQiNiuListener{
        void onSuccess(File file,String url);
        void onProgress();
        void onError(String message);
    }
}
