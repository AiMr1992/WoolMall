package com.android.wool.model.interactor;
import android.text.TextUtils;
import com.android.wool.model.network.ConnHttpHelper;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.model.network.api.HttpConstant;
import com.android.wool.model.network.subscribers.EventSubscriber;
import com.android.wool.model.network.subscribers.SubscriberOnNextListener;
import java.util.TreeMap;
/**
 * Created by AiMr on 2017/10/21
 */
public class TypeGoodsListInteractorImpl implements TypeGoodsListInteractor{
    @Override
    public void getTypeList(String car_id, String sales_num_order, String price_order,String time_order,String page,
                            final TypeGoodsListListener listener) {
        TreeMap<String,String> map = new TreeMap<>();
        map.put("cat_id",car_id);
        if(!TextUtils.isEmpty(sales_num_order))
            map.put("sales_num_order",sales_num_order);
        if(!TextUtils.isEmpty(price_order))
            map.put("price_order",price_order);
        if(!TextUtils.isEmpty(time_order))
            map.put("time_order",time_order);
        if(!TextUtils.isEmpty(page))
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
                HttpConstant.TYPE_GOODS_LIST,
                map
        );
    }
}