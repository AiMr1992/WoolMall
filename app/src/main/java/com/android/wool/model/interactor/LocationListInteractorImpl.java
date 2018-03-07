package com.android.wool.model.interactor;
import android.content.Context;
import com.android.wool.common.MyPreference;
import com.android.wool.model.network.ConnHttpHelper;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.model.network.api.HttpConstant;
import com.android.wool.model.network.subscribers.EventSubscriber;
import com.android.wool.model.network.subscribers.SubscriberOnNextListener;
import java.util.TreeMap;
/**
 * Created by AiMr on 2017/9/26
 */
public class LocationListInteractorImpl implements LocationListInteractor{
    @Override
    public void getLocation(Context context,String page,final LocationListListener listener) {
        String id = MyPreference.getInstance().getUid(context);
        TreeMap<String,String> map = new TreeMap<>();
        map.put("uid",id);
        map.put("page",page);
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
                HttpConstant.LOCATION,
                map
        );
    }
}
