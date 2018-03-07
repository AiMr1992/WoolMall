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
 * Created by AiMr on 2017/10/23
 */
public class OrderSubmitInteractorImpl implements OrderSubmitInteractor{
    @Override
    public void submitOrder(Context context, String addr_id, String logistics_id, String activity_id,
                            String comment,final OrderSubmitListener listener) {
        String uid = MyPreference.getInstance().getUid(context);
        TreeMap<String,String> map = new TreeMap<>();
        map.put("uid",uid);
        map.put("addr_id",addr_id);
        map.put("logistics_id",logistics_id);
        if(!TextUtils.isEmpty(activity_id))
            map.put("activity_id",activity_id);
        if(!TextUtils.isEmpty(comment))
            map.put("comment",comment);
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
                HttpConstant.ORDER_SUBMIT,
                map
        );
    }
}