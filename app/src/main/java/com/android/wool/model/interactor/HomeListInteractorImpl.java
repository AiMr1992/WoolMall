package com.android.wool.model.interactor;
import android.content.Context;
import android.text.TextUtils;
import com.android.wool.common.MyPreference;
import com.android.wool.model.network.ConnHttpHelper;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.model.network.api.HttpConstant;
import com.android.wool.model.network.subscribers.EventSubscriber;
import com.android.wool.model.network.subscribers.SubscriberOnNextListener;
import java.util.TreeMap;
/**
 * Created by AiMr on 2017/9/27
 */

public class HomeListInteractorImpl implements HomeListInteractor{
    @Override
    public void getList(Context context,final HomeListListener listener) {
        TreeMap<String,String> map = new TreeMap<>();
        String uid = MyPreference.getInstance().getUid(context);
        if(!TextUtils.isEmpty(uid))
            map.put("uid",uid);
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
                HttpConstant.HOME_LIST,
                map
        );
    }
}
