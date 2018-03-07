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
 * Created by AiMr on 2017/10/11
 */
public class PersonalInteractorImpl implements PersonalInteractor{
    @Override
    public void updateUserInfo(Context context, String name, String sex,String imgPath,final PersonalListener listener) {
        TreeMap<String,String> map = new TreeMap<>();
        String uid = MyPreference.getInstance().getUid(context);
        map.put("uid",uid);
        if(!TextUtils.isEmpty(name))
            map.put("name",name);
        if(!TextUtils.isEmpty(sex))
            map.put("sex",sex);
        if(!TextUtils.isEmpty(imgPath))
            map.put("avatar",imgPath);
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
                HttpConstant.UPDATE_MINE,
                map
        );
    }
}
