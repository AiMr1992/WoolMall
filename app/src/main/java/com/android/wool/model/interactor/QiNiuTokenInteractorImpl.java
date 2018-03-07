package com.android.wool.model.interactor;
import com.android.wool.model.network.ConnHttpHelper;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.model.network.api.HttpConstant;
import com.android.wool.model.network.subscribers.EventSubscriber;
import com.android.wool.model.network.subscribers.SubscriberOnNextListener;
import com.android.wool.util.LogUtil;
import java.util.TreeMap;
/**
 * Created by AiMr on 2018/1/15
 */
public class QiNiuTokenInteractorImpl implements QiNiuTokenInteractor{
    @Override
    public void qiNiuToken(final QiNiuTokenListener listener) {
        TreeMap<String,String> map = new TreeMap<>();
        ConnHttpHelper.getInstance().getServerApiPost(
                new EventSubscriber<ResponseMol>(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(ResponseMol responseMol) {
                        if(listener != null){
                            if(responseMol != null){
                                if(responseMol.isRequestSuccess()){
                                    LogUtil.d("ToKen:"+responseMol.getString("list"));
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
                HttpConstant.QN_TOKEN,
                map
        );
    }
}