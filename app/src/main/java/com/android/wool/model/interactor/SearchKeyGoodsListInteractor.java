package com.android.wool.model.interactor;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/10/20
 */
public interface SearchKeyGoodsListInteractor {
    void getSearchGoodsList(String keywords,String sales_num_order,String price_order,String time_order,
                            String page,SearchKeyGoodsListListener listener);
    interface SearchKeyGoodsListListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }
}
