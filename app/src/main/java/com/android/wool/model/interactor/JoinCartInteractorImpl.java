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
 * Created by AiMr on 2017/10/18
 */
public class JoinCartInteractorImpl implements JoinCartInteractor{
    @Override
    public void joinCart(Context context, String goodsId, String goodsNum, String specArray,final JoinCartListener listener) {
        String uid = MyPreference.getInstance().getUid(context);
        TreeMap<String,String> map = new TreeMap<>();
        map.put("uid",uid);
        map.put("goods_id",goodsId);
        map.put("goods_num",goodsNum);
        map.put("spec_arry",specArray);
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
                HttpConstant.JOIN_CART,
                map
        );
    }
}