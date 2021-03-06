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
 * Created by AiMr on 2017/10/19
 */
public class CartChangNumberInteractorImpl implements CartChangNumberInteractor{
    @Override
    public void changeCart(Context context, String cart_id, String Goods_num,final CartChangNumberListener listener) {
        TreeMap<String,String> map = new TreeMap<>();
        String uid = MyPreference.getInstance().getUid(context);
        map.put("uid",uid);
        map.put("cart_id",cart_id);
        map.put("goods_num",Goods_num);
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
                HttpConstant.CART_UPDATE_NUMBER,
                map
        );
    }
}
