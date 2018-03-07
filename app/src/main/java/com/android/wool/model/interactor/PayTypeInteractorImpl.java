package com.android.wool.model.interactor;
import com.android.wool.model.network.ConnHttpHelper;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.model.network.api.HttpConstant;
import com.android.wool.model.network.subscribers.EventSubscriber;
import com.android.wool.model.network.subscribers.SubscriberOnNextListener;
import java.util.TreeMap;
/**
 * Created by AiMr on 2017/10/24
 */
public class PayTypeInteractorImpl implements PayTypeInteractor{
    @Override
    public void payTypeList(final PayTypeListener listener) {
        TreeMap<String,String> map = new TreeMap<>();
        ConnHttpHelper.getInstance().getServerApiPost(
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
                HttpConstant.PAY_TYPE,
                map
        );
    }
}
