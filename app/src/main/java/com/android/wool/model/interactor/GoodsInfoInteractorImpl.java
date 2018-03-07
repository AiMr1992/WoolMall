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
 * Created by AiMr on 2017/10/13
 */
public class GoodsInfoInteractorImpl implements GoodsInfoInteractor{
    @Override
    public void getGoodsInfo(Context context, String id, final GoodsInfoListener listener) {
        String uid = MyPreference.getInstance().getUid(context);
        TreeMap<String,String> map = new TreeMap<>();
        if(!TextUtils.isEmpty(uid))
            map.put("uid",uid);
        map.put("id",id);
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
                HttpConstant.GOODS_INFO,
                map
        );
    }
}