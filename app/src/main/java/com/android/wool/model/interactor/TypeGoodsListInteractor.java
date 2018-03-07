package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/21
 */
public interface TypeGoodsListInteractor {
    void getTypeList(String car_id,String sales_num_order,String price_order,String time_order,String page,TypeGoodsListListener listener);
    interface TypeGoodsListListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
