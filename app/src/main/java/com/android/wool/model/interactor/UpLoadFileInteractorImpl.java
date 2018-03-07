package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.model.network.ConnHttpHelper;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.model.network.subscribers.EventSubscriber;
import com.android.wool.model.network.subscribers.SubscriberOnNextListener;
import com.android.wool.util.qiniu.QiNiuPhotoUpLoad;
import com.android.wool.util.qiniu.QiNiuUpLoadManager;
import java.io.File;
/**
 * Created by AiMr on 2017/10/11
 */
public class UpLoadFileInteractorImpl implements UpLoadFileInteractor{
    private QiNiuUpLoadManager upLoad;
    public UpLoadFileInteractorImpl() {
        upLoad = new QiNiuUpLoadManager();
    }

    /**
     * 本地服务器上传
     * @param file
     * @param listener
     */
    @Override
    public void uploadFile(File file,final UpLoadListener listener) {
        ConnHttpHelper.getInstance().uploadFile(
                new EventSubscriber<ResponseMol>(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(ResponseMol responseMol) {
                        if(listener != null){
                            if(responseMol != null){
                                if(responseMol.isRequestSuccess()){
                                    listener.onSuccess(responseMol);
                                }else {
                                    listener.onError(responseMol.getReturnMessage());
                                }
                            }else {
                                listener.onFailed();
                            }
                        }
                    }
                }),
                file
        );
    }

    /**
     * 七牛上传
     * @param file
     * @param listener
     */
    @Override
    public void upLoadQiNiuFile(File file,String token,final UpLoadQiNiuListener listener, int type, Context context) {
        upLoad.getQiNiuPhotoInstance().upLoad(
                new QiNiuPhotoUpLoad.UpLoadListener() {
                    @Override
                    public void onSuccess(File file,String url) {
                        if(listener != null){
                            listener.onSuccess(file,url);
                        }
                    }

                    @Override
                    public void onError(String message) {
                        if(listener != null){
                            listener.onError(message);
                        }
                    }
                },
                token,
                file,
                type,
                context
        );
    }
}