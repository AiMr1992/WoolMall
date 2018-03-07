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
 * Created by AiMr on 2017/10/16
 */
public class OrderDetailInteractorImpl implements OrderDetailInteractor{
    @Override
    public void getOrderDetail(Context context, String order_id,final OrderDetailListener listener) {
        String uid = MyPreference.getInstance().getUid(context);
        TreeMap<String,String> map = new TreeMap<>();
        map.put("order_id",order_id);
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
                HttpConstant.ORDER_DETAIL,
                map
        );
    }
}
